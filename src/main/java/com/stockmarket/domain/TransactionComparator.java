package com.stockmarket.domain;

import java.util.Comparator;

public class TransactionComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return Double.compare(Math.abs(t1.getLimit()-t1.getAsset().getMarketPrice()), Math.abs(t2.getLimit()-t2.getAsset().getMarketPrice()));
    }
}