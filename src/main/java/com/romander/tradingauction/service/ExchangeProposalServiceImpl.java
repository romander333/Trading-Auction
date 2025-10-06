package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.proposal.CounterProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalResponseDto;
import com.romander.tradingauction.dto.proposal.ProposalAccessResponseDto;
import com.romander.tradingauction.exception.AccessDeniedException;
import com.romander.tradingauction.exception.EntityNotFoundException;
import com.romander.tradingauction.exception.ProposalCannotBeCanceledException;
import com.romander.tradingauction.exception.ProposalOtherUsersProductException;
import com.romander.tradingauction.mapper.ProposalMapper;
import com.romander.tradingauction.model.ExchangeProposal;
import com.romander.tradingauction.model.Product;
import com.romander.tradingauction.model.User;
import com.romander.tradingauction.repository.ExchangeProposalRepository;
import com.romander.tradingauction.repository.ProductRepository;
import com.romander.tradingauction.repository.UserRepository;
import com.romander.tradingauction.security.AuthenticationService;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangeProposalServiceImpl implements ExchangeProposalService {
    private final ExchangeProposalRepository proposalRepository;
    private final ProposalMapper proposalMapper;
    private final AuthenticationService authenticationService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ExchangeProposalResponseDto createProposal(ExchangeProposalRequestDto requestDto) {
        User user = authenticationService.getCurrentUser();
        ExchangeProposal proposal = proposalMapper.toModel(requestDto);

        User toUser = userRepository.findById(requestDto.getToUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found toUser id " + requestDto.getToUserId()));

        proposal.setToUser(toUser);
        proposal.setFromUser(user);
        proposal.setStatus(ExchangeProposal.Status.PENDING);
        proposal.setCreatedAt(LocalDateTime.now());

        setToProductsAndFromProducts(requestDto, proposal);

        if (!checkOwnerProducts(proposal)) {
            throw new ProposalOtherUsersProductException("Can't create proposal without owner products");
        }

        proposalRepository.save(proposal);

        return proposalMapper.toDto(proposal);
    }

    @Override
    public ExchangeProposalResponseDto getProposal(Long proposalId) {
        ExchangeProposal proposal = getProposalById(proposalId);
        return proposalMapper.toDto(proposal);
    }

    @Override
    @Transactional
    public ExchangeProposalResponseDto cancelProposal(Long proposalId) {
        User user = authenticationService.getCurrentUser();
        ExchangeProposal proposal = getProposalById(proposalId);
        if (!proposal.getFromUser().equals(user)) {
            throw new AccessDeniedException("You can't cancel other people proposals");
        }
        if (proposal.getStatus() != ExchangeProposal.Status.PENDING) {
            throw new ProposalCannotBeCanceledException(
                    "Can't cancel proposal because it already accepted or rejected by user: "
                    + proposal.getToUser().getUsername());
        }
        proposal.setStatus(ExchangeProposal.Status.CANCELED);
        proposalRepository.save(proposal);
        return proposalMapper.toDto(proposal);
    }

    @Override
    public ProposalAccessResponseDto acceptProposal(Long proposalId) {
        ExchangeProposal proposal = getProposalById(proposalId);
        proposal.setStatus(ExchangeProposal.Status.ACCEPTED);

        ProposalAccessResponseDto responseDto = proposalMapper.toAccessDto(proposal);
        proposalRepository.save(proposal);
        return responseDto;
    }

    @Override
    public ExchangeProposalResponseDto rejectProposal(Long proposalId) {
        ExchangeProposal proposal = getProposalById(proposalId);
        proposal.setStatus(ExchangeProposal.Status.REJECTED);
        proposalRepository.save(proposal);
        return proposalMapper.toDto(proposal);
    }

    @Override
    @Transactional
    public ExchangeProposalResponseDto counterProposal(Long proposalId, CounterProposalRequestDto requestDto) {
        ExchangeProposal proposal = getProposalById(proposalId);
        proposal.setStatus(ExchangeProposal.Status.REJECTED);
        proposalRepository.save(proposal);

        ExchangeProposal counterProposal = new ExchangeProposal();
        counterProposal.setFromUser(authenticationService.getCurrentUser());
        counterProposal.setToUser(proposal.getFromUser());
        counterProposal.setToProducts(new HashSet<>(proposal.getFromProducts()));
        counterProposal.setStatus(ExchangeProposal.Status.PENDING);
        counterProposal.setCreatedAt(LocalDateTime.now());
        counterProposal.setFromProducts(getProductsById(requestDto.getCounterProductId()));

        proposalRepository.save(counterProposal);
        return proposalMapper.toDto(counterProposal);
    }

    @Override
    public Page<ExchangeProposalResponseDto> getIncomingProposals(Pageable pageable) {
        User user = authenticationService.getCurrentUser();
        Page<ExchangeProposal> proposals = proposalRepository.findAllByToUser(user, pageable);
        return proposals.map(proposalMapper::toDto);
    }

    @Override
    public Page<ExchangeProposalResponseDto> getOutgoingProposals(Pageable pageable) {
        User user = authenticationService.getCurrentUser();
        Page<ExchangeProposal> proposals = proposalRepository.findAllByFromUser(user, pageable);
        return proposals.map(proposalMapper::toDto);
    }
    private ExchangeProposal getProposalById(Long proposalId) {
        return proposalRepository.findById(proposalId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find proposal with id: "
                        + proposalId));
    }

    private Set<Product> getProductsById(Set<Long> productIds) {
        Set<Product> products = new HashSet<>();
        for (Long productId : productIds) {
            products.add(productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find product with id: "
                            + productId)));
        }
        return products;
    }

    private void setToProductsAndFromProducts(ExchangeProposalRequestDto requestDto, ExchangeProposal proposal) {
        proposal.getFromProducts().addAll(productRepository.findAllById(requestDto.getProductFromId()));
        proposal.getToProducts().addAll(productRepository.findAllById(requestDto.getProductToId()));
    }

    private boolean checkOwnerProducts(ExchangeProposal proposal) {
        boolean allFromValid = proposal.getFromProducts().stream()
                .allMatch(p -> p.getUser().equals(proposal.getFromUser()));
        boolean allToValid = proposal.getToProducts().stream()
                .allMatch(p -> p.getUser().equals(proposal.getToUser()));
        return allFromValid && allToValid;
    }
}
