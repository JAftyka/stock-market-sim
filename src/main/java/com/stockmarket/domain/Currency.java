package com.stockmarket.domain;

public class Currency extends Asset {

    private int spread; // w pipsach (1 pip = 0.0001)

    public Currency(String symbol, String name, double marketPrice, int spread) {
        super(symbol, name, marketPrice, AssetType.CURRENCY);

        if (spread < 0) {
            throw new IllegalArgumentException("Spread cannot be negative");
        }

        this.spread = spread;
    }

    public int getSpread() {
        return this.spread;
    }

    public void setSpread(int spread) {
        if (spread < 0) {
            throw new IllegalArgumentException("Spread cannot be negative");
        }
        this.spread = spread;
    }

    private double spreadValue() {
        return spread * 0.0001;
    }

    /** Cena kupna (ask) = cena rynkowa + spread */
    public double askPrice() {
        return getMarketPrice() + spreadValue();
    }

    /** Cena sprzedaży (bid) = cena rynkowa - spread */
    public double bidPrice() {
        return getMarketPrice() - spreadValue();
    }

    @Override
    public double calculatePurchaseCost(int quantity, double unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }

        // waluty kupuje się po cenie ASK
        return quantity * askPrice();
    }

    @Override
    public double calculateRealSaleValue(int quantity, double sellPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (sellPrice <= 0) {
            throw new IllegalArgumentException("Sell price must be positive");
        }

        // waluty sprzedaje się po cenie BID
        return quantity * bidPrice();
    }

    @Override
    public double calculateProfitFromLot(int quantity, double lotUnitPrice, double sellPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // zysk = (cena sprzedaży BID - cena zakupu ASK) * ilość
        double buy = lotUnitPrice;   // cena zakupu z partii
        double sell = bidPrice();    // cena sprzedaży waluty

        return (sell - buy) * quantity;
    }

    @Override
    public double calculateLotValue(PurchaseLot lot) {
        if (lot == null) {
            throw new IllegalArgumentException("Lot cannot be null");
        }
        return lot.getQuantity() * bidPrice();
    }

    @Override
    public double calculateValueOfAllLots() {
        double sum = 0.0;

        for (PurchaseLot lot : getLotDeque()) {
            sum += lot.getQuantity() * bidPrice();
        }

        return sum;
    }
}
