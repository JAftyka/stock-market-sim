package com.stockmarket.logic;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.PurchaseLot;

import java.time.LocalDate;
import java.util.HashMap;

public class Portfolio {

    private final HashMap<String, Asset> symbolAssetMap;
    private double cash;

    public Portfolio(double initialCash) {
        if (initialCash < 0) {
            throw new IllegalArgumentException("Initial cash cannot be negative");
        }
        this.cash = initialCash;
        this.symbolAssetMap = new HashMap<>();
    }

    public double getCash() {
        return this.cash;
    }

    public int getAssetCount() {
        return this.symbolAssetMap.size();
    }

    public Asset getAssetBySymbol(String symbol) {
        return this.symbolAssetMap.get(symbol);
    }

    /**
     * Kupno aktywa — dodaje partię zakupową (FIFO)
     */
    public boolean purchaseAsset(Asset asset, int quantity, double unitPrice) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }

        double totalCost = quantity * unitPrice;

        if (totalCost > this.cash) {
            throw new IllegalStateException("Insufficient cash to purchase asset");
        }

        // jeśli aktywo nie istnieje w portfelu → dodaj
        if (!symbolAssetMap.containsKey(asset.getSymbol())) {
            symbolAssetMap.put(asset.getSymbol(), asset);
        }

        // dodaj partię zakupową FIFO
        asset.addLot(LocalDate.now(), quantity, unitPrice);

        // odejmij gotówkę
        this.cash -= totalCost;

        return true;
    }

    /**
     * Zwraca całkowitą ilość aktywa (sumę wszystkich partii)
     */
    public int getAssetQuantity(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset cannot be null");
        }

        Asset stored = symbolAssetMap.get(asset.getSymbol());
        if (stored == null) {
            return 0;
        }

        int total = 0;
        for (PurchaseLot lot : stored.getLotQueue()) {
            total += lot.getQuantity();
        }
        return total;
    }

    /**
     * Redukuje ilość aktywa metodą FIFO — używane przy sprzedaży
     */
    public void reduceAssetQuantity(String symbol, int quantityToSell) {
        if (quantityToSell <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Asset asset = symbolAssetMap.get(symbol);
        if (asset == null) {
            throw new IllegalStateException("Asset not found in portfolio");
        }

        int remaining = quantityToSell;

        for (PurchaseLot lot : asset.getLotQueue()) {
            if (remaining == 0) break;

            int lotQty = lot.getQuantity();

            if (lotQty <= remaining) {
                // zużywamy całą partię
                remaining -= lotQty;
                lot.setQuantity(0);
            } else {
                // zużywamy część partii
                lot.setQuantity(lotQty - remaining);
                remaining = 0;
            }
        }

        if (remaining > 0) {
            throw new IllegalStateException("Not enough quantity to sell");
        }

        // usuwamy puste partie
        asset.getLotQueue().removeEmptyLots();
    }

    /**
     * Wycena portfela
     */
    public double audit() {
        double sum = 0.0;
        for (Asset asset : symbolAssetMap.values()) {
            sum += asset.calculateValueOfAllLots();
        }
        return sum;
    }
}