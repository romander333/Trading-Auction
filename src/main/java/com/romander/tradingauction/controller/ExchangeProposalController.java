package com.romander.tradingauction.controller;

import com.romander.tradingauction.dto.proposal.CounterProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalResponseDto;
import com.romander.tradingauction.dto.proposal.ProposalAccessResponseDto;
import com.romander.tradingauction.service.ExchangeProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeProposalController {
    private final ExchangeProposalService proposalService;

    @PostMapping()
    public ExchangeProposalResponseDto createProposal(@RequestBody @Valid ExchangeProposalRequestDto requestDto) {
        return proposalService.createProposal(requestDto);
    }

    @PostMapping("/{id}/cancel")
    public ExchangeProposalResponseDto cancelProposal(@PathVariable Long id) {
        return proposalService.cancelProposal(id);
    }

    @PostMapping("/{id}/accept")
    public ProposalAccessResponseDto acceptProposal(@PathVariable Long id) {
        return proposalService.acceptProposal(id);
    }

    @PostMapping("/{id}/reject")
    public ExchangeProposalResponseDto rejectProposal(@PathVariable Long id) {
        return proposalService.rejectProposal(id);
    }

    @PostMapping("/{id}/countee")
    public ExchangeProposalResponseDto counterProposal(
            @PathVariable Long id,
            @RequestBody @Valid CounterProposalRequestDto requestDto
    ) {
        return proposalService.counterProposal(id, requestDto);
    }

    @GetMapping("/{id}")
    public ExchangeProposalResponseDto getProposal(@PathVariable Long id) {
        return proposalService.getProposal(id);
    }

    @GetMapping("/incoming")
    public Page<ExchangeProposalResponseDto> getIncomingProposal(Pageable pageable) {
        return proposalService.getIncomingProposals(pageable);
    }

    @GetMapping("/outgoing")
    public Page<ExchangeProposalResponseDto> getOutgoingProposal(Pageable pageable) {
        return proposalService.getOutgoingProposals(pageable);
    }
}
