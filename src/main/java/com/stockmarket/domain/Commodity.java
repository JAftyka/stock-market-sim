package com.stockmarket.domain;

public class Commodity extends Asset {
    private double storageCostPerUnitPerDay;
    private int daysHeld;
    private double initialStorageFeePerUnit;

    public Commodity(String symbol, String name, double marketPrice) {
        super(symbol, name, marketPrice);
        this.storageCostPerUnitPerDay = 5.0;
        this.daysHeld = 0;
        this.initialStorageFeePerUnit = 20.0;
    }

    public Commodity(String symbol, String name, double marketPrice, double storageCostPerUnitPerDay, int daysHeld) {
        super(symbol, name, marketPrice);
        if(daysHeld<0){
            throw new IllegalArgumentException("Number of days cannot be negative");
        }
        if(storageCostPerUnitPerDay<=0){
            throw new IllegalArgumentException("Storage cost must be positive");
        }
        this.storageCostPerUnitPerDay = storageCostPerUnitPerDay;
        this.daysHeld = daysHeld;
        this.initialStorageFeePerUnit = 20.0;
    }

    public double getInitialStorageFeePerUnit(){
        return this.initialStorageFeePerUnit;
    }

    public double getStorageCostPerUnitPerDay(){
        return this.storageCostPerUnitPerDay;
    }

    public int getDaysHeld(){
        return this.daysHeld;
    }

    public void addToDaysHeld(int days){
        if(days<0){
            throw new IllegalArgumentException("Number of days cannot be negative");
        }
        this.daysHeld += days;
    }

    public void setInitialStorageFeePerUnit(double initialStorageFeePerUnit){
        if(initialStorageFeePerUnit<=0){
            throw new IllegalArgumentException("Initial storage fee must be positive");
        }
        this.initialStorageFeePerUnit = initialStorageFeePerUnit;
    }

    public void setStorageCostPerUnitPerDay(double storageCostPerUnitPerDay){
        if(storageCostPerUnitPerDay<=0){
            throw new IllegalArgumentException("Storage cost must be positive");
        }
        this.storageCostPerUnitPerDay = storageCostPerUnitPerDay;
    }

    @Override
    public double calculateRealValue(int quantity) {
        return quantity * this.getMarketPrice() - this.getDaysHeld() * this.getStorageCostPerUnitPerDay() * quantity;
    }

    @Override
    public double calculatePurchaseCost(int quantity) {
        return quantity * this.getMarketPrice() + this.getInitialStorageFeePerUnit()*quantity;
    }
}