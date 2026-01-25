package com.stockmarket.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Commodity extends Asset {

    private double storageCostPerUnitPerDay;
    private double initialStorageFeePerUnit;

    public Commodity(String symbol, String name, double marketPrice) {
        super(symbol, name, marketPrice, AssetType.COMMODITY);
        this.storageCostPerUnitPerDay = 5.0;
        this.initialStorageFeePerUnit = 20.0;
    }

    public Commodity(String symbol, String name, double marketPrice,
                     double storageCostPerUnitPerDay, double initialStorageFeePerUnit) {

        super(symbol, name, marketPrice, AssetType.COMMODITY);

        if (storageCostPerUnitPerDay <= 0) {
            throw new IllegalArgumentException("Storage cost must be positive");
        }
        if (initialStorageFeePerUnit <= 0) {
            throw new IllegalArgumentException("Initial storage fee must be positive");
        }

        this.storageCostPerUnitPerDay = storageCostPerUnitPerDay;
        this.initialStorageFeePerUnit = initialStorageFeePerUnit;
    }

    public String getName() {
        return super.getName();
    }

    public String getSymbol() {
        return super.getSymbol();
    }

    public double getInitialStorageFeePerUnit() {
        return this.initialStorageFeePerUnit;
    }

    public double getStorageCostPerUnitPerDay() {
        return this.storageCostPerUnitPerDay;
    }

    public double getMarketPrice() {
        return super.getMarketPrice();
    }

    public void setInitialStorageFeePerUnit(double initialStorageFeePerUnit) {
        if (initialStorageFeePerUnit <= 0) {
            throw new IllegalArgumentException("Initial storage fee must be positive");
        }
        this.initialStorageFeePerUnit = initialStorageFeePerUnit;
    }

    public void setStorageCostPerUnitPerDay(double storageCostPerUnitPerDay) {
        if (storageCostPerUnitPerDay <= 0) {
            throw new IllegalArgumentException("Storage cost must be positive");
        }
        this.storageCostPerUnitPerDay = storageCostPerUnitPerDay;
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

        for (PurchaseLot lot : getLotQueue()) {
            sum += lot.getQuantity() * getMarketPrice();
        }

        return sum;
    }

    public double calculateStorageCostForLot(PurchaseLot lot, LocalDate saleDate) {
        long daysHeld = ChronoUnit.DAYS.between(lot.getPurchaseDate(), saleDate);
        if (daysHeld < 0) {
            throw new IllegalArgumentException("Sale date cannot be before purchase date");
        }

        return daysHeld * storageCostPerUnitPerDay * lot.getQuantity()
                + lot.getQuantity() * initialStorageFeePerUnit;
    }
}
