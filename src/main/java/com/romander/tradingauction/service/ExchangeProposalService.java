package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.proposal.CounterProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalResponseDto;
import com.romander.tradingauction.dto.proposal.ProposalAccessResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExchangeProposalService {
    ExchangeProposalResponseDto createProposal(ExchangeProposalRequestDto requestDto);

    ExchangeProposalResponseDto getProposal(Long proposalId);

    ExchangeProposalResponseDto cancelProposal(Long proposalId);

    ProposalAccessResponseDto acceptProposal(Long proposalId);

    ExchangeProposalResponseDto rejectProposal(Long proposalId);

    ExchangeProposalResponseDto counterProposal(Long proposalId, CounterProposalRequestDto requestDto);

    Page<ExchangeProposalResponseDto> getIncomingProposals(Pageable pageable);

    Page<ExchangeProposalResponseDto> getOutgoingProposals(Pageable pageable);
}
