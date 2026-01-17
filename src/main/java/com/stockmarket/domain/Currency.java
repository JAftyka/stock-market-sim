package com.stockmarket.domain;

public class Currency extends Asset {
    private int spread;

    public Currency(String symbol, String name, int spread) {
        super(symbol, name, AssetType.CURRENCY);
        if (spread <= 0) {
            throw new IllegalArgumentException("Spread must be positive");
        }
        if (spread*0.0001 > this.askPrice()) {
            throw new IllegalArgumentException("Spread cannot be greater than ask price");
        }
        this.spread = spread; // wyrazone w pip (1 pip = 0.0001)
    }

    public double getSpread() {
        return this.spread;
    }

    public void setSpread(int spread) {
        if (spread <= 0) {
            throw new IllegalArgumentException("Spread must be positive");
        }
        if (spread*0.0001 > this.askPrice()) {
            throw new IllegalArgumentException("Spread cannot be greater than ask price");
        }
        this.spread = spread;
    }

    @Override
    public double calculateRealValue(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        return this.bidPrice() * quantity;
    }

    @Override
    public double calculatePurchaseCost(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        return this.askPrice() * quantity; //spread wchodzi dopiero przy sprzedazy
    }

    @Override
    public double calculateLotValue(PurchaseLot lot) {
        return lot.getQuantity() * getMarketPrice();
    }
}
