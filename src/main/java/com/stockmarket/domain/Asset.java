package com.stockmarket.domain;

import java.util.Objects;

public abstract class Asset {
    private final String symbol;
    private String name;
    private double marketPrice;

    protected Asset(String symbol, String name, double marketPrice) {
        if(symbol == null){
            throw new IllegalArgumentException("Asset symbol cannot be null");
        }
        if(name == null){
            throw new IllegalArgumentException("Asset name cannot be null");
        }
        if(marketPrice < 0){
            throw new IllegalArgumentException("Asset market price cannot be negative");
        }
        if(marketPrice == 0){
            throw new IllegalArgumentException("Asset market price cannot be zero");
        }
        this.symbol = symbol;
        this.name = name;
        this.marketPrice = marketPrice;
    }

    public void setName(String name){
        if(name == null){
            throw new IllegalArgumentException("Asset name cannot be null");
        }
        this.name = name;
    }
    public void setMarketPrice(double marketPrice){
        if(marketPrice < 0){
            throw new IllegalArgumentException("Asset market price cannot be negative");
        }
        if(marketPrice == 0){
            throw new IllegalArgumentException("Asset market price cannot be zero");
        }
        this.marketPrice = marketPrice;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public String getName(){
        return this.name;
    }

    public double getMarketPrice(){
        return this.marketPrice;
    }

    public abstract double calculateRealValue(int quantity);

    public abstract double calculatePurchaseCost(int quantity);


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;           //true jesli to ten sam obiekt
        if (obj == null || getClass() != obj.getClass()) return false; //false jesli null lub inny typ
        Asset other = (Asset) obj;
        return Objects.equals(this.symbol, other.getSymbol()); //sprawdzam czy symbole sa takie same
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
