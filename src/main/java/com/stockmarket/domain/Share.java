package com.stockmarket.domain;

public class Share extends Asset {

    private double handlingFee;

    public Share(String symbol, String name, double marketPrice) {
        super(symbol, name, marketPrice, AssetType.SHARE);
        this.handlingFee = 15.0;
    }

    public double getHandlingFee() {
        return this.handlingFee;
    }

    public void setHandlingFee(double handlingFee) {
        if (handlingFee <= 0) {
            throw new IllegalArgumentException("Handling fee must be positive");
        }
        this.handlingFee = handlingFee;
    }

    @Override
    //
    public double calculateRealValue(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return (quantity * getMarketPrice()) - handlingFee;
    }

    @Override
    public double calculatePurchaseCost(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return (quantity * getMarketPrice()) + handlingFee;
    }

    @Override
    public double calculateLotValue(PurchaseLot lot) {
        return lot.getQuantity() * getMarketPrice();
    }
}
