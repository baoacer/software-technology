package com.example.jwt.application.usecase.inventory;

public interface CheckOutOfStockUseCase {
    boolean execute(short productId);
}
