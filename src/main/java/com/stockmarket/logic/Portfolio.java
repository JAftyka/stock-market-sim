package com.stockmarket.logic;

import com.stockmarket.domain.*;

public class Portfolio {

    private static final int MAX_HOLDINGS = 10;
    
    private class AssetHolding {

        private Asset asset;
        private int quantity;

        public AssetHolding(Asset asset, int quantity) {
            if (asset == null) {
                throw new IllegalArgumentException("Asset cannot be null");
            }
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }
            this.asset = asset;
            this.quantity = quantity;
        }
        
        public Asset getAsset() {
            return this.asset;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public boolean addQuantity(int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity to add must be positive");
            }
            this.quantity += quantity;
            return true;
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

    public int getHoldingsCount() {
        return this.holdingsCount;
    }

    public boolean purchaseAsset(Asset asset, int quantity) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (asset.calculatePurchaseCost(quantity)>this.getCash()){
            throw new IllegalStateException("Insufficient cash to purchase asset");
        }
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].getAsset().equals(asset)) {
                holdings[i].addQuantity(quantity);
                this.cash -= asset.calculatePurchaseCost(quantity);
                return true;
            }
        }
        if (holdingsCount >= MAX_HOLDINGS) {
            throw new IllegalStateException("AssetHolding is full, cannot add more holdings");
        }
        holdings[holdingsCount] = new AssetHolding(asset, quantity);
        holdingsCount++;
        this.cash -= asset.calculatePurchaseCost(quantity);
        return true;
    }

    public int getHoldingQuantity(int index){
        if(index<0||index>holdingsCount) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return this.holdings[index].getQuantity();
    }

    public double audit() {
        double sum = 0;
        for (int i = 0; i < holdingsCount; i++) {
            sum+=holdings[i].getAsset().calculateRealValue(holdings[i].getQuantity());
        }
        return sum;
    }
}
