package com.stockmarket.domain;

import java.util.Comparator;

public class SellComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        // niższa cena = wyższy priorytet
        int priceCompare = Double.compare(o1.getLimit(), o2.getLimit());
        if (priceCompare != 0) return priceCompare;

        return Long.compare(o1.getTimestamp(), o2.getTimestamp());
    }
}

