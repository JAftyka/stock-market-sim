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
    public double calculatePurchaseCost(int quantity, double unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }

        // koszt zakupu = cena * ilość + prowizja maklerska
        return quantity * unitPrice + handlingFee;
    }

    @Override
    public double calculateRealSaleValue(int quantity, double sellPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (sellPrice <= 0) {
            throw new IllegalArgumentException("Sell price must be positive");
        }

        // wpływ gotówki = cena sprzedaży * ilość - prowizja
        return quantity * sellPrice - handlingFee;
    }

    @Override
    public double calculateProfitFromLot(int quantity, double lotUnitPrice, double sellPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // zysk = (cena sprzedaży - cena zakupu) * ilość - prowizja
        return (sellPrice - lotUnitPrice) * quantity - handlingFee;
    }

    @Override
    public double calculateLotValue(PurchaseLot lot) {
        if (lot == null) {
            throw new IllegalArgumentException("Lot cannot be null");
        }
        return lot.getQuantity() * getMarketPrice();
    }

    @Override
    public double calculateValueOfAllLots() {
        double sum = 0.0;

        for (PurchaseLot lot : getLotDeque()) {
            sum += lot.getQuantity() * getMarketPrice();
        }

        return sum;
    }
}