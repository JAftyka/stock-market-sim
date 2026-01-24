package com.stockmarket.domain;

import java.util.Comparator;

public class TransactionComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction t1, Transaction t2) {
        double d1 = Math.abs(t1.getLimit() - t1.getAsset().getMarketPrice());
        double d2 = Math.abs(t2.getLimit() - t2.getAsset().getMarketPrice());
        return Double.compare(d1, d2);
    }
}