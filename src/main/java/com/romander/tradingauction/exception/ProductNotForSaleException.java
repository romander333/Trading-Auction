package com.romander.tradingauction.exception;

public class ProductNotForSaleException extends RuntimeException {
    public ProductNotForSaleException(String message) {
        super(message);
    }
}
