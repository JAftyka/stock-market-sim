package com.stockmarket.domain;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PurchaseLotQueue implements Iterable<PurchaseLot> {

    private final Queue<PurchaseLot> lotQueue =
            new PriorityQueue<>(new PurchaseLotDateComparator());

    public PurchaseLot addLot(PurchaseLot lot) {
        lotQueue.offer(lot);
        return lot;
    }

    public PurchaseLot getNextLot() {
        return lotQueue.poll();
    }

    @Override
    public Iterator<PurchaseLot> iterator() {
        return lotQueue.iterator();
    }
}
