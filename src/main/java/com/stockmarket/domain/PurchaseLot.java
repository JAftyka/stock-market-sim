package com.stockmarket.domain;

import java.time.LocalDate;

public class PurchaseLot {

    private final LocalDate purchaseDate;
    private double unitPrice;
    private int quantity;

    public PurchaseLot(LocalDate purchaseDate, int quantity, double unitPrice) {
        if (purchaseDate == null) {
            throw new IllegalArgumentException("Purchase date cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }

        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public boolean isEmpty() {
        return this.quantity == 0;
    }
}