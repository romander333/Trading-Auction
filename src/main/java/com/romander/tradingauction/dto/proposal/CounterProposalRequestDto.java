package com.romander.tradingauction.dto.proposal;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.Data;

@Data
public class CounterProposalRequestDto {
    @NotEmpty
    private Set<Long> counterProductId;
}
