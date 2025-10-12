package com.romander.tradingauction.mapper;

import com.romander.tradingauction.config.MapperConfig;
import com.romander.tradingauction.dto.payment.PaymentResponseDto;
import com.romander.tradingauction.model.Payment;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {

    PaymentResponseDto toDto(Payment payment);
}
