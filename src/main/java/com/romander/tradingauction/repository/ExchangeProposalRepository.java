package com.romander.tradingauction.repository;

import com.romander.tradingauction.model.ExchangeProposal;
import com.romander.tradingauction.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeProposalRepository extends JpaRepository<ExchangeProposal, Long> {

    @EntityGraph(attributePaths = {"fromProducts", "toProducts", "fromUser", "toUser"})
    Page<ExchangeProposal> findAllByFromUser(User fromUser, Pageable pageable);

    @EntityGraph(attributePaths = {"fromProducts", "toProducts", "fromUser", "toUser"})
    Page<ExchangeProposal> findAllByToUser(User toUser, Pageable pageable);

    @EntityGraph(attributePaths = {"fromProducts", "toProducts", "fromUser", "toUser"})
    Optional<ExchangeProposal> findById(Long id);
}
