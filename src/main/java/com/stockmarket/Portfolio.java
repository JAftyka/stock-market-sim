package com.stockmarket;

import com.stockmarket.Stock;

public class Portfolio {

    private static final int MAX_HOLDINGS = 10;
    
    private static class StockHolding {

        private Stock stock;
        private int quantity;

        public StockHolding(Stock stock, int quantity) {
            this.stock = stock;
            this.quantity = quantity;
        }
        
        public Stock getStock() {
            return this.stock;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void addQuantity(int quantity) {

            this.quantity += quantity;
        }
    }

    private double cash;
    private StockHolding[] holdings;
    private int holdingsCount;

    public Portfolio(double initialCash) {
      this.cash = initialCash;
      this.holdings = new StockHolding[MAX_HOLDINGS];
      this.holdingsCount = 0;
    }

    public int getMaxHoldings() {
        return MAX_HOLDINGS;
    }
    
    public double getCash() {
        return this.cash;
    }

    public StockHolding getHolding(int index) {
        if (index < 0 || index >= holdingsCount) {
            throw new IndexOutOfBoundsException("Niepoprawny indeks");
        }
        return holdings[index];
    }

    public int getHoldingsCount() {
        return this.holdingsCount;
    }

    public void addStock(Stock stock, int quantity) {
        if (stock == null || quantity <= 0) {
            throw new IllegalArgumentException("Akcja nie może być null i ilość musi być dodatnia");
        }
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].getStock().equals(stock)) {
                holdings[i].addQuantity(quantity);
                return;
            }
        }
        if (holdingsCount >= holdings.length) {
            throw new IllegalStateException("Portfel pełny, nie można dodać więcej pozycji");
        }
        holdings[holdingsCount] = new StockHolding(stock, quantity);
        holdingsCount++;
    }

    public double calculateStockValue() {
        double sum = 0;
        for (int i = 0; i < holdingsCount; i++) {
            sum+=holdings[i].getStock().getInitialPrice()*holdings[i].getQuantity();
        }
        return sum;
    }

    public double calculateTotalValue() {
        return cash + calculateStockValue();
    }

    public int getStockQuantity(Stock stock) {
    for (int i = 0; i < holdingsCount; i++) {
        if (holdings[i].getStock().equals(stock)) {
            return holdings[i].getQuantity();
        }
    }
    return 0;
}
}

