package com.romander.tradingauction.service.impl;

import com.romander.tradingauction.model.Address;
import com.romander.tradingauction.model.ExchangeProposal;
import com.romander.tradingauction.model.Product;
import com.romander.tradingauction.model.User;
import com.romander.tradingauction.service.GmailService;
import com.romander.tradingauction.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final GmailService gmailService;
    @Override
    public void sendNewProposalNotification(ExchangeProposal proposal) {
        User fromUser = proposal.getFromUser();
        User toUser = proposal.getToUser();

        String subject = "🔔 Нова пропозиція обміну на Trading Auction!";

        String offeredProducts = formatProducts(proposal.getFromProducts());
        String requestedProducts = formatProducts(proposal.getToProducts());

        String body = String.format(
                "Привіт, %s!\n\n" +
                        "Користувач %s (%s) хоче обмінятися з вами.\n\n" +
                        "📥 ВАМ ПРОПОНУЮТЬ:\n%s\n\n" +
                        "📤 ВЗАМІН НА ВАШІ:\n%s\n\n" +
                        "Зайдіть у додаток: http://localhost:3000, щоб переглянути деталі та прийняти рішення.",
                getFullName(toUser),
                getFullName(fromUser),
                fromUser.getEmail(),
                offeredProducts,
                requestedProducts
        );

        sendEmailAsync(toUser.getEmail(), subject, body);
    }

    @Override
    public void sendProposalAcceptedNotification(ExchangeProposal proposal) {
        User initiator = proposal.getFromUser(); // Той, хто пропонував першим
        User receiver = proposal.getToUser();    // Той, хто погодився

        String subjectForInitiator = "✅ Вашу пропозицію прийнято! Дані для відправки";
        String bodyForInitiator = String.format(
                "Вітаємо, %s!\n\n" +
                        "Користувач %s прийняв вашу пропозицію!\n" +
                        "Тепер ви маєте надіслати свої товари.\n\n" +
                        "📦 ДАНІ ДЛЯ ВІДПРАВКИ ОТРИМУВАЧУ:\n" +
                        "Ім'я: %s\n" +
                        "Телефон: %s\n" +
                        "Адреса: %s\n\n" +
                        "Будь ласка, зв'яжіться з користувачем для уточнення деталей.",
                        "Зайдіть у додаток: http://localhost:3000, щоб переглянути деталі та прийняти рішення.",
                getFullName(initiator),
                getFullName(receiver),
                getFullName(receiver),
                receiver.getPhone(),
                formatAddress(receiver.getAddress())
        );
        sendEmailAsync(initiator.getEmail(), subjectForInitiator, bodyForInitiator);

        String subjectForReceiver = "✅ Ви прийняли пропозицію! Дані для відправки";
        String bodyForReceiver = String.format(
                "Привіт, %s!\n\n" +
                        "Ви погодилися на обмін з користувачем %s.\n" +
                        "Не забудьте відправити свої товари.\n\n" +
                        "📦 ДАНІ ДЛЯ ВІДПРАВКИ ПАРТНЕРУ:\n" +
                        "Ім'я: %s\n" +
                        "Телефон: %s\n" +
                        "Адреса: %s\n\n" +
                        "Вдалого обміну!",
                getFullName(receiver),
                getFullName(initiator),
                getFullName(initiator),
                initiator.getPhone(),
                formatAddress(initiator.getAddress())
        );
        sendEmailAsync(receiver.getEmail(), subjectForReceiver, bodyForReceiver);
    }

    @Override
    public void sendProposalRejectedNotification(ExchangeProposal proposal) {
        User initiator = proposal.getFromUser();
        User receiver = proposal.getToUser();

        String subject = "❌ Оновлення щодо вашої пропозиції";
        String body = String.format(
                "Привіт, %s.\n\n" +
                        "На жаль, користувач %s відхилив вашу пропозицію обміну.\n" +
                        "Не засмучуйтесь, спробуйте запропонувати інші товари або знайдіть іншого партнера!",
                        "Зайдіть у додаток: http://localhost:3000, щоб переглянути деталі та прийняти рішення.",
                getFullName(initiator),
                getFullName(receiver)
        );

        sendEmailAsync(initiator.getEmail(), subject, body);
    }

    @Override
    public void sendNewCounterProposalNotification(ExchangeProposal newProposal) {

        User initiator = newProposal.getFromUser();
        User receiver = newProposal.getToUser();

        String subject = "🔄 Отримано зустрічну пропозицію!";

        String offeredProducts = formatProducts(newProposal.getFromProducts());
        String requestedProducts = formatProducts(newProposal.getToProducts());

        String body = String.format(
                "Привіт, %s!\n\n" +
                        "Користувачу %s не підійшли попередні умови, але він надіслав вам ЗУСТРІЧНУ пропозицію!\n\n" +
                        "📥 ВАМ ПРОПОНУЮТЬ НОВІ УМОВИ:\n%s\n\n" +
                        "📤 ВЗАМІН НА ВАШІ:\n%s\n\n" +
                        "Зайдіть у додаток: http://localhost:3000, щоб розглянути нові умови.",
                getFullName(receiver),
                getFullName(initiator),
                offeredProducts,
                requestedProducts
        );

        sendEmailAsync(receiver.getEmail(), subject, body);
    }

    private void sendEmailAsync(String to, String subject, String body) {
        new Thread(() -> {
            try {
                gmailService.sendEmail(to, subject, body);
                log.info("Email sent to {}", to);
            } catch (Exception e) {
                log.error("Failed to send email to {}", to, e);
            }
        }).start();
    }

    private String getFullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

    private String formatAddress(Address address) {
        if (address == null) return "Адреса не вказана";
        return String.format("%s, %s, %s, %s",
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry());
    }

    private String formatProducts(java.util.Set<Product> products) {
        if (products == null || products.isEmpty()) return "Товари не вказані";
        // Припускаємо, що у Product є метод getName() або getTitle()
        return products.stream()
                .map(Product::getTitle)
                .collect(Collectors.joining(", "));
    }
}

