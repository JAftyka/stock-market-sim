package com.stockmarket.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class PurchaseLotDeque implements Iterable<PurchaseLot> {

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

    public PurchaseLot peekFirst() {
        return lots.peekFirst();
    }

    public void removeEmptyLots() {
        Iterator<PurchaseLot> it = lots.iterator();
        while (it.hasNext()) {
            PurchaseLot lot = it.next();
            if (lot.isEmpty()) {
                it.remove();
            }
        }
    }

    public void clear() {
        lots.clear();
    }

    public boolean isEmpty() {
        return lots.isEmpty();
    }

    public int size() {
        return lots.size();
    }

    public PurchaseLot getLotAt(int index) {
        if (index < 0 || index >= lots.size()) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        int i = 0;
        for (PurchaseLot lot : lots) {
            if (i == index) {
                return lot;
            }
            i++;
        }

        throw new IndexOutOfBoundsException("Index out of range");
    }

    @Override
    public Iterator<PurchaseLot> iterator() {
        return lots.iterator(); // zachowuje kolejność FIFO
    }
}
