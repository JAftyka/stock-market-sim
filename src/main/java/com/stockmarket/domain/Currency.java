package com.stockmarket.domain;

public class Currency extends Asset {

    private int spread; // w pipsach (1 pip = 0.0001)

    public Currency(String symbol, String name, double marketPrice, int spread) {
        super(symbol, name, marketPrice, AssetType.CURRENCY);

        if (spread <= 0) {
            throw new IllegalArgumentException("Spread must be positive");
        }

        this.spread = spread;
    }

    public int getSpread() {
        return this.spread;
    }

    public void setSpread(int spread) {
        if (spread <= 0) {
            throw new IllegalArgumentException("Spread must be positive");
        }
        this.spread = spread;
    }

    /**
     * Spread w jednostkach ceny (np. 0.0003)
     */
    private double spreadValue() {
        return spread * 0.0001;
    }

    /**
     * Cena kupna (ask) = cena rynkowa + spread
     */
    public double askPrice() {
        return getMarketPrice() + spreadValue();
    }

    /**
     * Cena sprzedaży (bid) = cena rynkowa - spread
     */
    public double bidPrice() {
        return getMarketPrice() - spreadValue();
    }

    @Override
    public double calculateValueOfAllLots() {
        double sum = 0.0;

        for (PurchaseLot lot : getLotQueue()) {
            sum += lot.getQuantity() * bidPrice(); // wycena po cenie sprzedaży
        }

        return sum;
    }

    /**
     * Koszt zakupu waluty — po cenie ask
     */
    public double calculatePurchaseCost(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return askPrice() * quantity;
    }

    @Override
    public double calculateLotValue(PurchaseLot lot) {
        if (lot == null) {
            throw new IllegalArgumentException("Lot cannot be null");
        }
        return lot.getQuantity() * bidPrice();
    }
}
