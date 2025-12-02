package com.stockmarket.logic;

import com.stockmarket.domain.*;

public class Portfolio<T extends Asset> {

    private static final int MAX_HOLDINGS = 10;
    
    private static class AssetHolding<T extends Asset> {

        private T asset;
        private int quantity;

        public AssetHolding(T asset, int quantity) {
            if (asset == null) {
                throw new IllegalArgumentException("Asset cannot be null");
            }
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }
            this.asset = asset;
            this.quantity = quantity;
        }
        
        public T getAsset() {
            return this.asset;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void addQuantity(int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity to add must be positive");
            }
            this.quantity += quantity;
        }
    }

    private double cash;
    private AssetHolding[] holdings;
    private int holdingsCount;

    public Portfolio(double initialCash) {
        if (initialCash<0) {
            throw new IllegalArgumentException("Initial cash cannot be negative");
        }
        this.cash = initialCash;
        this.holdings = new AssetHolding[MAX_HOLDINGS];
        this.holdingsCount = 0;
    }

    public int getMaxHoldings() {
        return MAX_HOLDINGS;
    }
    
    public double getCash() {
        return this.cash;
    }

    public AssetHolding<T> getHolding(int index) {
        if (index < 0 || index >= holdingsCount) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return holdings[index];
    }

    public int getHoldingsCount() {
        return this.holdingsCount;
    }

    public void purchaseAsset(T asset, int quantity) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (asset.calculatePurchaseCost(quantity)>this.getCash()){
            System.out.println("nuh-uh, u too poor bro");
            throw new IllegalStateException("Insufficient cash to purchase asset");
        }
        else{
            for (int i = 0; i < holdingsCount; i++) {
                if (holdings[i].getAsset().equals(asset)) {
                    holdings[i].addQuantity(quantity);
                    return;
                }
            }
        }
        if (holdingsCount >= MAX_HOLDINGS) {
            throw new IllegalStateException("AssetHolding is full, cannot add more holdings");
        }
        holdings[holdingsCount] = new AssetHolding(asset, quantity);
        holdingsCount++;
        this.cash -= asset.calculatePurchaseCost(quantity);
    }

    public double calculateTotalRealValue() {
        double sum = 0;
        for (int i = 0; i < holdingsCount; i++) {
            sum+=holdings[i].getAsset().calculateRealValue(holdings[i].getQuantity());
        }
        return sum;
    }
}
