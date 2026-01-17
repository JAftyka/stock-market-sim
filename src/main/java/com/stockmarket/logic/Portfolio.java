package com.stockmarket.logic;
import com.stockmarket.domain.*;

import java.util.HashMap;

public class Portfolio {
    private HashMap<String,Asset> symbolAssetMap;

    private double cash;

    public Portfolio(double initialCash) {
        this.symbolAssetMap = new HashMap<>();
        if (initialCash<0) {
            throw new IllegalArgumentException("Initial cash cannot be negative");
        }
        this.cash = initialCash;
    }

    public double getCash() {
        return this.cash;
    }

    public int getAssetCount() {
        return this.symbolAssetMap.size();
    }

    public Asset getAssetBySymbol(String symbol){
        return symbolAssetMap.get(symbol);
    }

    public boolean purchaseAsset(Asset asset, int quantity, double price) {
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
        for (Asset asset:symbolAssetMap.values()) {
            sum+=asset.calculateValueOfAllLots();
        }
        return sum;
    }
}