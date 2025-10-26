package com.romander.tradingauction.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
@Slf4j
public class GmailConfiguration {

    @Value("${gmail.name}")
    private String applicationName;

    // Шлях до файлу з обліковими даними в папці 'resources'
    @Value("classpath:credentials.json")
    private Resource credentialsFile;

    // Папка для збереження токенів
    @Value("${gmail.tokens-directory:tokens}") // 'tokens' - значення за замовчуванням
    private String tokensDirectoryPath;

    // Області видимості (scopes)
    @Value("${gmail.scopes}")
    private List<String> scopes;

    @Bean
    @Qualifier("gsonFactoryGmail")
    public GsonFactory gsonFactoryGmail() {
        return GsonFactory.getDefaultInstance();
    }

    @Bean
    @Qualifier("googleNetHttpTransportGmail")
    public NetHttpTransport googleNetHttpTransportGmail() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    /**
     * Створює бін Credential, виконуючи потік автентифікації OAuth2.
     */
    @Bean
    @Qualifier("gmailCredentials")
    public Credential gmailCredentials(@Qualifier("googleNetHttpTransportGmail") NetHttpTransport httpTransport,
                                       @Qualifier("gsonFactoryGmail") GsonFactory jsonFactory) throws Exception {

        log.info("Starting Gmail OAuth2 authentication flow...");

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                jsonFactory, new InputStreamReader(credentialsFile.getInputStream())
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensDirectoryPath)))
                .setAccessType("offline")
                .build();

        // Використовуємо порт 8888 для зворотного виклику OAuth
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        // authorize("user") запустить браузер при першому запуску
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        log.info("Gmail OAuth2 authentication successful.");
        return credential;
    }

    /**
     * Конфігурує бін Gmail service.
     */
    @Bean
    public Gmail gmail(@Qualifier("gmailCredentials") Credential credential,
                       @Qualifier("googleNetHttpTransportGmail") NetHttpTransport httpTransport,
                       @Qualifier("gsonFactoryGmail") GsonFactory jsonFactory) {
        try {
            Gmail gmail = new Gmail.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(applicationName)
                    .build();

            log.info("Gmail service successfully configured with application name: {}", applicationName);
            return gmail;
        } catch (Exception e) {
            log.error("Failed to configure Gmail service", e);
            throw new GmailConfigurationException("Failed to configure Gmail service", e);
        }
    }

    // Ваш клас винятків залишається без змін
    public static class GmailConfigurationException extends RuntimeException {
        public GmailConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}