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

    public double getStorageCostPerUnitPerDay() {
        return this.storageCostPerUnitPerDay;
    }

    public void setStorageCostPerUnitPerDay(double storageCostPerUnitPerDay) {
        if (storageCostPerUnitPerDay <= 0) {
            throw new IllegalArgumentException("Storage cost must be positive");
        }
        this.storageCostPerUnitPerDay = storageCostPerUnitPerDay;
    }

    public double getInitialStorageFeePerUnit() {
        return initialStorageFeePerUnit;
    }

    public void setInitialStorageFeePerUnit(double initialStorageFeePerUnit) {
        if (initialStorageFeePerUnit <= 0) {
            throw new IllegalArgumentException("Initial storage fee must be positive");
        }
        this.initialStorageFeePerUnit = initialStorageFeePerUnit;
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
        double sum = 0;
        PurchaseLotQueue queue = this.getLotQueue();
        Iterator<this.getLotQueue()> iterator = ot.iterator();
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (...) iterator.remove();
        }
        for (PurchaseLot lot:this.getLotQueue().//use an iterator to access the queue elements){
            sum += lot.getQuantity() * lot.getPurchasePrice();
        }
        return sum;
    }

    public double calculateStorageCostForLot(PurchaseLot lot, LocalDate saleDate) {
        long daysHeld = ChronoUnit.DAYS.between(lot.getPurchaseDate(), saleDate);
        if (daysHeld < 0) {
            throw new IllegalArgumentException("Sale date cannot be before purchase date");
        }
        return daysHeld * this.storageCostPerUnitPerDay * lot.getQuantity() + lot.getQuantity() * this.initialStorageFeePerUnit;
    }
}
