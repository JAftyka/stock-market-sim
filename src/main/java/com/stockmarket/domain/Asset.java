package com.stockmarket.domain;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Asset {

    private final String symbol;
    private String name;
    private final AssetType type;
    private double marketPrice;
    private final PurchaseLotDeque lotStack;

    protected Asset(String symbol, String name, double marketPrice, AssetType type) {
        if (symbol == null) throw new IllegalArgumentException("Asset symbol cannot be null");
        if (name == null) throw new IllegalArgumentException("Asset name cannot be null");
        if (marketPrice <= 0) throw new IllegalArgumentException("Market price must be positive");

        this.symbol = symbol;
        this.name = name;
        this.marketPrice = marketPrice;
        this.type = type;
        this.lotStack = new PurchaseLotDeque();
    }

    // Wartość pojedynczej partii
    public abstract double calculateLotValue(PurchaseLot lot);

    // Wartość wszystkich partii aktywa
    public abstract double calculateValueOfAllLots();

    // Koszt zakupu aktywa (akcje → prowizja, waluty → spread
    public abstract double calculatePurchaseCost(int quantity, double unitPrice);

    // Realny wpływ gotówki przy sprzedaży (po prowizji/spreadzie)
    public abstract double calculateRealSaleValue(int quantity, double sellPrice);

    // Zysk z danej partii przy sprzedaży FIFO
    public abstract double calculateProfitFromLot(int quantity, double lotUnitPrice, double sellPrice);

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

    public void setName(String newName) {
        if (newName == null) throw new IllegalArgumentException("Name cannot be null");
        this.name = newName;
    }

    public void setMarketPrice(double newPrice) {
        if (newPrice <= 0) throw new IllegalArgumentException("Market price must be positive");
        this.marketPrice = newPrice;
    }

    public PurchaseLotDeque getLotDeque() {
        return this.lotStack;
    }

    public boolean addLot(LocalDate purchaseDate, int quantity, double unitPrice) {
        if (purchaseDate == null) throw new IllegalArgumentException("Purchase date cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (unitPrice <= 0) throw new IllegalArgumentException("Unit price must be positive");

        PurchaseLot lot = new PurchaseLot(purchaseDate, quantity, unitPrice);
        this.lotStack.addLot(lot);
        return true;
    }

    public int getTotalQuantity() {
        int total = 0;
        for (PurchaseLot lot : lotStack) {
            total += lot.getQuantity();
        }
        return total;
    }

    public void removeEmptyLots() {
        this.lotStack.removeEmptyLots();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asset other = (Asset) obj;
        return Objects.equals(this.symbol, other.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
