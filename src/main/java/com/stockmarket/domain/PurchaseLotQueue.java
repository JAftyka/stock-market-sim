package com.stockmarket.domain;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PurchaseLotQueue {

    private final Queue<PurchaseLot> lotQueue = new PriorityQueue<>(new PurchaseLotDateComparator());

    public PurchaseLot addLot(PurchaseLot lot) {
        lotQueue.offer(lot);
        return lot;
    }

    public PurchaseLot getNextLot() {
        return lotQueue.poll();
    }
}

