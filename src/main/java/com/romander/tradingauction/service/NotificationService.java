package com.romander.tradingauction.service;

import com.romander.tradingauction.model.ExchangeProposal;

public interface NotificationService {
    void sendNewProposalNotification(ExchangeProposal proposal);
    void sendProposalAcceptedNotification(ExchangeProposal proposal);
    void sendProposalRejectedNotification(ExchangeProposal proposal);
    void sendNewCounterProposalNotification(ExchangeProposal proposal);
}
