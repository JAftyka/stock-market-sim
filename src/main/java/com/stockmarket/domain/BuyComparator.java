package com.stockmarket.domain;

import java.util.Comparator;

public class BuyComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        // wyższa cena = wyższy priorytet
        int priceCompare = Double.compare(o2.getLimit(), o1.getLimit());
        if (priceCompare != 0) return priceCompare;

        // jeśli ceny równe → starsze zlecenie ma pierwszeństwo
        return Long.compare(o1.getTimestamp(), o2.getTimestamp());
    }
}
