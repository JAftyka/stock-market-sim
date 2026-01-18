package com.stockmarket.domain;

import java.time.LocalDate;

public class PurchaseLot {
    private LocalDate purchaseDate;
    private int quantity;
    private double purchasePrice;

    public PurchaseLot(LocalDate purchaseDate, int quantity, double purchasePrice){
        if(purchaseDate==null) throw new IllegalArgumentException("Purchase date cannot be null");
        if(quantity<=0) throw new IllegalArgumentException("Quantity must be positive");
        if(purchasePrice<=0) throw new IllegalArgumentException("Purchase price must be positive");
        this.purchaseDate=purchaseDate;
        this.quantity=quantity;
        this.purchasePrice=purchasePrice;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPurchasePrice() {
        return this.purchasePrice;
    }

    public void setPurchaseDate(LocalDate newDate) {
        if(newDate==null) throw new IllegalArgumentException("Purchase date cannot be null");
        this.purchaseDate=newDate;
    }

    public void setQuantity(int newQuantity) {
        if(newQuantity<=0) throw new IllegalArgumentException("Quantity must be positive");
        this.quantity=newQuantity;
    }

    public void setPurchasePrice(double newPrice) {
        if(newPrice<=0) throw new IllegalArgumentException("Purchase price must be positive");
        this.purchasePrice=newPrice;
    }
}
