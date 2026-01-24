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

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Reduction amount must be positive");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("Cannot reduce more than current quantity");
        }
        this.quantity -= amount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}