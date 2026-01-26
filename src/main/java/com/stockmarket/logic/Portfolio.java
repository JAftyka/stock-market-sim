package com.stockmarket.logic;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.PurchaseLot;
import com.stockmarket.exception.InsufficientFundsException;
import com.stockmarket.exception.InsufficientQuantityException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Collection;

public class Portfolio {

    private final HashMap<String, Asset> symbolAssetMap;
    private double cash;

    public Portfolio(double initialCash) {
        if (initialCash < 0) {
            throw new IllegalArgumentException("Initial cash cannot be negative");
        }
        this.cash = initialCash;
        this.symbolAssetMap = new HashMap<>();
    }

    public double getCash() {
        return this.cash;
    }

    public int getAssetCount() {
        return this.symbolAssetMap.size();
    }

    public Asset getAssetBySymbol(String symbol) {
        return this.symbolAssetMap.get(symbol);
    }

    /**
     * Kupno aktywa — używa logiki domenowej aktywa (polimorfizm).
     */
    public boolean purchaseAsset(Asset asset, int quantity, double unitPrice) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }

        // koszt zakupu zależny od typu aktywa (akcje → prowizja, waluty → spread itd.)
        double totalCost = asset.calculatePurchaseCost(quantity, unitPrice);

        if (totalCost > this.cash) {
            throw new InsufficientFundsException("Not enough cash to purchase asset");
        }

        if (!symbolAssetMap.containsKey(asset.getSymbol())) {
            symbolAssetMap.put(asset.getSymbol(), asset);
        }

        asset.addLot(LocalDate.now(), quantity, unitPrice);
        this.cash -= totalCost;

        return true;
    }

    /**
     * Zwraca całkowitą ilość aktywa (sumę wszystkich partii).
     */
    public int getAssetQuantity(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol cannot be null");
        }

        Asset stored = symbolAssetMap.get(symbol);
        if (stored == null) {
            return 0;
        }

        int total = 0;
        for (PurchaseLot lot : stored.getLotQueue()) {
            total += lot.getQuantity();
        }
        return total;
    }

    /**
     * Sprzedaż aktywa metodą FIFO — zwraca P&L.
     */
    public double sellAsset(String symbol, int quantityToSell, double sellPrice) {
        if (quantityToSell <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (sellPrice <= 0) {
            throw new IllegalArgumentException("Sell price must be positive");
        }

        Asset asset = symbolAssetMap.get(symbol);
        if (asset == null) {
            throw new IllegalStateException("Asset not found in portfolio");
        }

        int remaining = quantityToSell;
        double totalProfit = 0.0;

        for (PurchaseLot lot : asset.getLotQueue()) {
            if (remaining == 0) break;

            int lotQty = lot.getQuantity();
            int used = Math.min(lotQty, remaining);

            // zysk z tej partii
            double profit = asset.calculateProfitFromLot(used, lot.getUnitPrice(), sellPrice);
            totalProfit += profit;

            // aktualizacja partii
            lot.setQuantity(lotQty - used);
            remaining -= used;
        }

        if (remaining > 0) {
            throw new InsufficientQuantityException("Not enough quantity to sell");
        }

        asset.getLotQueue().removeEmptyLots();

        // wpływ gotówki po sprzedaży (uwzględnia prowizję / spread)
        double realRevenue = asset.calculateRealSaleValue(quantityToSell, sellPrice);
        this.cash += realRevenue;

        return totalProfit;
    }

    /**
     * Wycena portfela.
     */
    public double audit() {
        double sum = 0.0;
        Collection<Asset> assets = symbolAssetMap.values();
        for (Asset asset : assets) {
            sum += asset.calculateValueOfAllLots();
        }
        return sum;
    }
}
