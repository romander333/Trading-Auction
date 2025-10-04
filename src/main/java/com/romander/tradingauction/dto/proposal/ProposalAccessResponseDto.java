package com.romander.tradingauction.dto.proposal;

import com.romander.tradingauction.dto.user.AddressDto;
import com.romander.tradingauction.model.ExchangeProposal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProposalAccessResponseDto {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private Set<Long> productFromId;
    private Set<Long> productToId;
    private ExchangeProposal.Status status;
    private LocalDateTime createdAt;
    private AddressDto shippingAddress;
}
