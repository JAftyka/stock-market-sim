package com.stockmarket.domain;

import java.util.Comparator;

public class PurchaseLotDateComparator implements Comparator<PurchaseLot> {

    @Override
    public int compare(PurchaseLot a, PurchaseLot b) {
        if (a == null || b == null) {
            throw new NullPointerException("PurchaseLot cannot be null");
        }
        return a.getPurchaseDate().compareTo(b.getPurchaseDate());
    }
}
