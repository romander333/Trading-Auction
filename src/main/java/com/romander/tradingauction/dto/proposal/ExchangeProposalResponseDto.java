package com.romander.tradingauction.dto.proposal;

import com.romander.tradingauction.model.ExchangeProposal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class ExchangeProposalResponseDto {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private Set<Long> productFromId;
    private Set<Long> productToId;
    private ExchangeProposal.Status status;
    private LocalDateTime createdAt;
}
