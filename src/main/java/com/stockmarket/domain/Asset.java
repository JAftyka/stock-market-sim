package com.stockmarket.domain;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Asset {

    private final String symbol;
    private final String name;
    private final AssetType type;
    private double marketPrice;
    private PurchaseLotQueue lotQueue;

    protected Asset(String symbol, String name, double marketPrice, AssetType type) {
        this.symbol = symbol;
        this.name = name;
        this.marketPrice = marketPrice;
        this.type = type;
        this.lotQueue = new PurchaseLotQueue();
    }

    public abstract double calculateLotValue(PurchaseLot lot);
    public abstract double calculateValueOfAllLots();

    public boolean addLot(LocalDate purchaseDate, int quantity, double unitPrice) {
        if(purchaseDate==null) throw new IllegalArgumentException("Purchase date cannot be null");
        if(quantity<=0) throw new IllegalArgumentException("Quantity must be positive");
        if(unitPrice<=0) throw new IllegalArgumentException("Unit price must be positive");
        PurchaseLot lot = new PurchaseLot(purchaseDate, quantity, unitPrice);
        this.lotQueue.addLot(lot);
        return true;
    }

    public AssetType getType() {
        return this.type;
    }

    public double getMarketPrice() {
        return this.marketPrice;
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
