package com.stockmarket.domain;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Asset {

    private final String symbol;
    private String name;
    private final AssetType type;
    private double marketPrice;
    private PurchaseLotQueue lotQueue;

    protected Asset(String symbol, String name, double marketPrice, AssetType type) {
        if(symbol==null) throw new IllegalArgumentException("Asset symbol cannot be null");
        if(name==null) throw new IllegalArgumentException("Asset name cannot be null");
        if(marketPrice<=0) throw new IllegalArgumentException("Market price must be positive");
        this.symbol = symbol;
        this.name = name;
        this.marketPrice = marketPrice;
        this.type = type;
        this.lotQueue = new PurchaseLotQueue();
    }

    public abstract double calculateLotValue(PurchaseLot lot);
    public abstract double calculateValueOfAllLots();

    public String getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return this.name;
    }

    public AssetType getType() {
        return this.type;
    }

    public double getMarketPrice() {
        return this.marketPrice;
    }

    public void setName(String newName){
        if (newName==null) throw new IllegalArgumentException("Name cannot be null");
        this.name=newName;
    }

    public void setMarketPrice(double newPrice) {
        if(newPrice<=0) throw new IllegalArgumentException("Market price must be positive");
        this.marketPrice=newPrice;
    }

    public PurchaseLotQueue getLotQueue() {
        return this.lotQueue;
    }

    public boolean addLot(LocalDate purchaseDate, int quantity, double unitPrice) {
        if(purchaseDate==null) throw new IllegalArgumentException("Purchase date cannot be null");
        if(quantity<=0) throw new IllegalArgumentException("Quantity must be positive");
        if(unitPrice<=0) throw new IllegalArgumentException("Unit price must be positive");
        PurchaseLot lot = new PurchaseLot(purchaseDate, quantity, unitPrice);
        this.lotQueue.addLot(lot);
        return true;
    }

    public int getTotalQuantity() {
        int total = 0;
        for (PurchaseLot lot : lotQueue) {
            total += lot.getQuantity();
        }
        return total;
    }

    public void removeEmptyLots() {
        this.lotQueue.removeEmptyLots();
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
