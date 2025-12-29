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

    /**
     * Market value of a commodity does NOT include storage cost.
     * Storage cost is an expense applied during sale (per lot).
     */
    @Override
    public double calculateRealValue(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return quantity * getMarketPrice();
    }

    /**
     * Purchase cost for commodities includes:
     * - unit purchase price (from the lot)
     * - initial storage fee per unit
     *
     * NOTE: This method is used when creating a PurchaseLot.
     */
    @Override
    public double calculatePurchaseCost(int quantity) {
        throw new UnsupportedOperationException(
                "Commodity purchase cost must be calculated per-lot using unit purchase price");
    }

    /**
     * Commodity-specific per-lot valuation.
     * Used for reporting and FIFO sale calculations.
     */
    @Override
    public double calculateLotValue(PurchaseLot lot) {
        return lot.getQuantity() * getMarketPrice();
    }

    /**
     * Computes storage cost for a specific lot.
     * This is used during FIFO sale to reduce profit.
     */
    public double calculateStorageCostForLot(PurchaseLot lot, LocalDate saleDate) {
        long daysHeld = ChronoUnit.DAYS.between(lot.getPurchaseDate(), saleDate);
        if (daysHeld < 0) {
            throw new IllegalArgumentException("Sale date cannot be before purchase date");
        }
        return daysHeld * storageCostPerUnitPerDay * lot.getQuantity();
    }
}
