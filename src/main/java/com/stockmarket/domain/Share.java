package com.stockmarket.domain;

public class Share extends Asset {
    private double handlingFee;

    public Share(String symbol, String name, double marketPrice) {
        super(symbol, name, marketPrice);
        this.handlingFee = 15.0;
    }

    public double getHandlingFee() {
        return this.handlingFee;
    }

    public void setHandlingFee(double handlingFee) {
        if(handlingFee < 0){
            throw new IllegalArgumentException("Share handling fee cannot be negative");
        }
        if(handlingFee == 0){
            throw new IllegalArgumentException("Share handling fee cannot be zero");
        }
        this.handlingFee = handlingFee;
    }
    @Override
    public double calculateRealValue(int quantity) {
        return quantity * this.getMarketPrice() - this.handlingFee;
    }

    @Override
    public double calculatePurchaseCost(int quantity) {
        return quantity * this.getMarketPrice() + this.handlingFee;
    }
}