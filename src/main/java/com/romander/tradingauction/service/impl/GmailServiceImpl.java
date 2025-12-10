package com.romander.tradingauction.service.impl;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.romander.tradingauction.exception.EmailServiceException;
import com.romander.tradingauction.service.GmailService;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailServiceImpl implements GmailService {
    private static final String USER_ID = "me";
    private final Gmail gmail;
    @Override
    public boolean sendEmail(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = createMimeMessage(to, USER_ID, subject, body);

            Message message = createMessage(mimeMessage);

            gmail.users().messages().send(USER_ID, message).execute();

            return true;
        } catch (Exception e) {
            throw new EmailServiceException("Failed to send email", e);
        }
    }

    private MimeMessage createMimeMessage(String to, String from, String subject, String messageBody) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(messageBody);

        return email;
    }

    private Message createMessage(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        email.writeTo(buffer);

        String encodedEmail = Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.toByteArray());

        Message message = new Message();
        message.setRaw(encodedEmail);

        return message;
    }
}
