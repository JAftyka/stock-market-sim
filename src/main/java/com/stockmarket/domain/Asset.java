package com.stockmarket.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Asset {

    private final String symbol;
    private final String name;
    private double marketPrice;
    private final AssetType type;

    protected final List<PurchaseLot> lots;

    protected Asset(String symbol, String name, double marketPrice, AssetType type) {
        this.symbol = symbol;
        this.name = name;
        this.marketPrice = marketPrice;
        this.type = type;
        this.lots = new ArrayList<>();
    }

    public abstract double calculateRealValue(int quantity);
    public abstract double calculatePurchaseCost(int quantity);
    public abstract double calculateLotValue(PurchaseLot lot);

    public List<PurchaseLot> getLots() {
        return lots;
    }

    public void addLot(PurchaseLot lot) {
        lots.add(lot);
    }

    public AssetType getType() {
        return type;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;           //true jesli to ten sam obiekt
        if (obj == null || getClass() != obj.getClass()) return false; //false jesli null lub inny typ
        Asset other = (Asset) obj;
        return Objects.equals(this.symbol, other.symbol); //sprawdzam czy symbole sa takie same
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
