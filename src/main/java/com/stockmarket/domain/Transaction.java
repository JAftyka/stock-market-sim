package com.stockmarket.domain;

public class Transaction {
    private Asset asset;
    private double limit;
    private TransactionType type;

    public Transaction(Asset asset, double limit){
        if(asset==null) throw new IllegalArgumentException("Asset cannot be null");
        if(limit<=0) throw new IllegalArgumentException("Price limit must be positive");
        this.asset=asset;
        this.limit=limit;
    }

    public double getLimit(){
        return this.limit;
    }

    public double getAsset(){
        return this.asset;
    }

    public TransactionType getType(){
        return this.type;
    }
}
