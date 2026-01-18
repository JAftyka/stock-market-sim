package com.stockmarket.domain;

import java.util.PriorityQueue;
import java.util.Queue;

public class TransactionManager {

    private Queue<Transaction> buyQueue;
    private Queue<Transaction> sellQueue;

    public TransactionManager() {
        TransactionComparator comparator = new TransactionComparator();
        buyQueue = new PriorityQueue<>(comparator);
        sellQueue = new PriorityQueue<>(comparator);
    }

    public boolean addTransaction(Transaction transaction){
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

    public boolean matchOrders(){
        while (!buyQueue.isEmpty() && !sellQueue.isEmpty()) {

            Transaction buy = buyQueue.peek();
            Transaction sell = sellQueue.peek();

            if (buy.getLimit() >= sell.getLimit()) {

                int tradedQty = Math.min(buy.getQuantity(), sell.getQuantity());
                double tradePrice = sell.getLimit();

                System.out.println("TRADE " + tradedQty + " @ " + tradePrice);

                buy.setQuantity(buy.getQuantity() - tradedQty);
                sell.setQuantity(sell.getQuantity() - tradedQty);

                if (buy.getQuantity() == 0) buyQueue.poll();
                if (sell.getQuantity() == 0) sellQueue.poll();

            } else {
                break;
            }
        }
        return true;
    }
}