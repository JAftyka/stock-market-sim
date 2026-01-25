package com.stockmarket.domain;

import java.util.PriorityQueue;
import java.util.Queue;

public class TransactionManager {

    private final PriorityQueue<Transaction> buyQueue;
    private final PriorityQueue<Transaction> sellQueue;

    public TransactionManager() {
        buyQueue = new PriorityQueue<>(new BuyComparator());
        sellQueue = new PriorityQueue<>(new SellComparator());
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.BUY) {
            buyQueue.offer(transaction);
            matchOrders();
            return true;
        }
        if (transaction.getType() == TransactionType.SELL) {
            sellQueue.offer(transaction);
            matchOrders();
            return true;
        }
        return false;
    }

    private void matchOrders() {
        while (!buyQueue.isEmpty() && !sellQueue.isEmpty()) {

            Transaction buy = buyQueue.peek();
            Transaction sell = sellQueue.peek();

            // różne aktywa → nie matchujemy
            if (!buy.getSymbol().equals(sell.getSymbol())) {
                break;
            }

            // brak zgodności cenowej
            if (buy.getLimit() < sell.getLimit()) {
                break;
            }

            int tradedQty = Math.min(buy.getQuantity(), sell.getQuantity());
            double tradePrice = sell.getLimit(); // pasywne zlecenie wyznacza cenę

            System.out.println("TRADE " + tradedQty + " @ " + tradePrice);

            buy.setQuantity(buy.getQuantity() - tradedQty);
            sell.setQuantity(sell.getQuantity() - tradedQty);

            if (buy.getQuantity() == 0) buyQueue.poll();
            if (sell.getQuantity() == 0) sellQueue.poll();
        }
    }
}
