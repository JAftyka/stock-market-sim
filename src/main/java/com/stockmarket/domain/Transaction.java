package com.stockmarket.domain;

public class Transaction {

    private final String symbol;
    private final double limit;
    private int quantity;
    private final TransactionType type;
    private final long timestamp;

    public Transaction(Asset asset, double limit, int quantity, TransactionType type) {
        if (asset == null) throw new IllegalArgumentException("Asset cannot be null");
        if (limit <= 0) throw new IllegalArgumentException("Price limit must be positive");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        this.symbol = asset.getSymbol();
        this.limit = limit;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = System.nanoTime(); // do rozstrzygania remisów
    }

    public String getSymbol() {
        return symbol;
    }

    public double getLimit() {
        return limit;
    }

    public int getQuantity() {
        return quantity;
    }

    public TransactionType getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setQuantity(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = newQuantity;
    }
}
