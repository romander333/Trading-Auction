package com.romander.tradingauction.repository;

import com.romander.tradingauction.model.ExchangeProposal;
import com.romander.tradingauction.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeProposalRepository extends JpaRepository<ExchangeProposal, Long> {
    Page<ExchangeProposal> findAllByFromUser(User fromUser, Pageable pageable);

    Page<ExchangeProposal> findAllByToUser(User toUser, Pageable pageable);
}
