package com.stockmarket.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class PurchaseLotQueue implements Iterable<PurchaseLot> {

    private final Deque<PurchaseLot> lots = new ArrayDeque<>();

    public void addLot(PurchaseLot lot) {
        if (lot == null) {
            throw new IllegalArgumentException("Lot cannot be null");
        }
        lots.addLast(lot); // FIFO
    }

    public PurchaseLot getNextLot() {
        return lots.pollFirst(); // FIFO
    }

    public void removeEmptyLots() {
        Iterator<PurchaseLot> it = lots.iterator();
        while (it.hasNext()) {
            if (it.next().isEmpty()) {
                it.remove();
            }
        }
    }

    public boolean isEmpty() {
        return lots.isEmpty();
    }

    public int size() {
        return lots.size();
    }

    @Override
    public Iterator<PurchaseLot> iterator() {
        return lots.iterator(); // zachowuje kolejność FIFO
    }
}
