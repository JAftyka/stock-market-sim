package com.stockmarket.domain;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Wartość rynkowa akcji po uwzględnieniu prowizji.
     * Akcje nie mają spreadu jak waluty, ale mają koszt obsługi.
     */
    @Override
    public double calculateRealValue(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return (quantity * getMarketPrice()) - handlingFee;
    }

    /**
     * Koszt zakupu akcji z uwzględnieniem prowizji.
     */
    @Override
    public double calculatePurchaseCost(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return (quantity * getMarketPrice()) + handlingFee;
    }

    /**
     * Akcje nie mają dodatkowych reguł podatkowych poza FIFO,
     * więc metoda pozostaje prosta.
     */
    @Override
    public double calculateLotValue(PurchaseLot lot) {
        return lot.getQuantity() * getMarketPrice();
    }
}
