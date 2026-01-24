package com.stockmarket.domain;

public class Transaction {
    private Asset asset;
    private double limit;
    private int quantity;
    private TransactionType type;

    public Transaction(Asset asset, double limit, int quantity, TransactionType type){
        if(asset==null) throw new IllegalArgumentException("Asset cannot be null");
        if(limit<=0) throw new IllegalArgumentException("Price limit must be positive");
        if(quantity<=0) throw new IllegalArgumentException("Quantity must be positive");
        this.asset=asset;
        this.limit=limit;
        this.quantity=quantity;
        this.type=type;
    }

    public Asset getAsset(){
        return this.asset;
    }

    public double getLimit(){
        return this.limit;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public TransactionType getType(){
        return this.type;
    }

    public boolean setQuantity(int newQuantity){
        if(newQuantity<0) throw new IllegalArgumentException("Quantity cannot be negative");
        this.quantity=newQuantity;
        return true;
    }
}
