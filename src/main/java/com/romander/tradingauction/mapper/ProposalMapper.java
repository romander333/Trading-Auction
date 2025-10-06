package com.romander.tradingauction.mapper;

import com.romander.tradingauction.config.MapperConfig;
import com.romander.tradingauction.dto.proposal.ExchangeProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalResponseDto;
import com.romander.tradingauction.dto.proposal.ProposalAccessResponseDto;
import com.romander.tradingauction.model.ExchangeProposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class, imports = {Collectors.class}, uses = {AddressMapper.class})
public interface ProposalMapper {

    ExchangeProposal toModel(ExchangeProposalRequestDto requestDto);

    @Mapping(target = "fromUserId", source = "fromUser.id")
    @Mapping(target = "toUserId", source = "toUser.id")
    @Mapping(target = "productFromId", expression = "java(exchangeProposal.getFromProducts().stream().map(p -> p.getId()).collect(Collectors.toSet()))")
    @Mapping(target = "productToId", expression = "java(exchangeProposal.getToProducts().stream().map(p -> p.getId()).collect(Collectors.toSet()))")
    ExchangeProposalResponseDto toDto(ExchangeProposal exchangeProposal);

    @Mapping(target = "shippingAddress", source = "fromUser.address")
    @Mapping(target = "fromUserId", source = "fromUser.id")
    @Mapping(target = "toUserId", source = "toUser.id")
    @Mapping(target = "productFromId", expression = "java(exchangeProposal.getFromProducts().stream().map(p -> p.getId()).collect(Collectors.toSet()))")
    @Mapping(target = "productToId", expression = "java(exchangeProposal.getToProducts().stream().map(p -> p.getId()).collect(Collectors.toSet()))")
    ProposalAccessResponseDto toAccessDto(ExchangeProposal exchangeProposal);
}

