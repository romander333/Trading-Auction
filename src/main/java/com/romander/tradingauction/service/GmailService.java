package com.romander.tradingauction.service;

public interface GmailService {
    boolean sendEmail(String to, String subject, String body);
}
