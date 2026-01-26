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

    public double getInitialStorageFeePerUnit() {
        return this.initialStorageFeePerUnit;
    }

    public double getStorageCostPerUnitPerDay() {
        return this.storageCostPerUnitPerDay;
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

        for (PurchaseLot lot : getLotDeque()) {
            sum += lot.getQuantity() * getMarketPrice();
        }

        return sum;
    }

    @Override
    public double calculatePurchaseCost(int quantity, double unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }

        // koszt zakupu = cena * ilość + opłata początkowa magazynowania
        return quantity * unitPrice + quantity * initialStorageFeePerUnit;
    }

    @Override
    public double calculateRealSaleValue(int quantity, double sellPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (sellPrice <= 0) {
            throw new IllegalArgumentException("Sell price must be positive");
        }

        // sprzedaż towaru nie odejmuje storage cost
        return quantity * sellPrice;
    }

    @Override
    public double calculateProfitFromLot(int quantity, double lotUnitPrice, double sellPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // UWAGA: Portfolio powinno przekazać datę sprzedaży
        LocalDate saleDate = LocalDate.now();

        // koszt magazynowania od zakupu do sprzedaży
        double storageCost = calculateStorageCost(quantity, lotUnitPrice, saleDate);

        // zysk = (cena sprzedaży - cena zakupu) * ilość - koszt magazynowania
        return (sellPrice - lotUnitPrice) * quantity - storageCost;
    }

    private double calculateStorageCost(int quantity, double lotUnitPrice, LocalDate saleDate) {
        // musimy znaleźć partię, żeby pobrać datę zakupu
        // ale Portfolio przekazuje tylko lotUnitPrice, więc:
        // → zakładamy, że data sprzedaży = dziś
        // → a data zakupu jest w PurchaseLot (Portfolio ją zna)

        // WERSJA PROSTA: storage liczone od zakupu do dziś
        // (jeśli chcesz wersję z datą sprzedaży → Portfolio musi ją przekazać)

        // Znajdujemy partię o tej cenie zakupu
        PurchaseLot targetLot = null;
        for (PurchaseLot lot : getLotDeque()) {
            if (lot.getUnitPrice() == lotUnitPrice) {
                targetLot = lot;
                break;
            }
        }

        if (targetLot == null) {
            return 0.0; // nie powinno się zdarzyć
        }

        long daysHeld = ChronoUnit.DAYS.between(targetLot.getPurchaseDate(), saleDate);
        if (daysHeld < 0) {
            daysHeld = 0;
        }

        return daysHeld * storageCostPerUnitPerDay * quantity
                + quantity * initialStorageFeePerUnit;
    }
}
