package com.romander.tradingauction.mapper;

import com.romander.tradingauction.config.MapperConfig;
import com.romander.tradingauction.dto.proposal.ExchangeProposalRequestDto;
import com.romander.tradingauction.dto.proposal.ExchangeProposalResponseDto;
import com.romander.tradingauction.dto.proposal.ProposalAccessResponseDto;
import com.romander.tradingauction.model.ExchangeProposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ExchangeProposalMapper {
    ExchangeProposal toModel(ExchangeProposalRequestDto requestDto);

    ExchangeProposalResponseDto toDto(ExchangeProposal exchangeProposal);

    @Mapping(target = "shippingAddress", source = "fromUser.address")
    ProposalAccessResponseDto toAccessDto(ExchangeProposal exchangeProposal);
}
