package com.romander.tradingauction.dto.proposal;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExchangeProposalRequestDto {
    @NotNull
    private Long toUserId;
    @NotEmpty
    private Set<Long> productFromId;
    @NotEmpty
    private Set<Long> productToId;
}
