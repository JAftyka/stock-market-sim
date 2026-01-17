package com.stockmarket.domain;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;
import java.lang.Math;

public class TransactionManager {
    private Queue<Transaction> buyQueue = new PriorityQueue<>((a, b) -> Double.compare(b.price, a.price));
    private Queue<Transaction> sellQueue = new PriorityQueue<>((a, b) -> Double.compare(a.price, b.price));

    public TransactionManager(){
        buyQueue = new PriorityQueue<>((a, b) -> Double.compare(b.getPrice(), a.getPrice())); // Max-heap
        sellQueue = new PriorityQueue<>((a, b) -> Double.compare(a.getPrice(), b.getPrice())); // Min-heap
    }

    public boolean addTransaction(Transaction transaction){
        if(transaction.getType()==TransactionType.BUY){
            buyQueue.offer(transaction);
            matchOrders();
            return true;
        }
        if(transaction.getType()==TransactionType.SELL){
            sellQueue.offer(transaction);
            matchOrders();
            return true;
        }
        else return false;
    }

    public boolean matchOrders(){
        while (!buyQueue.isEmpty() && !sellQueue.isEmpty()) {
            Transaction buy = buyQueue.peek();
            Transaction sell = sellQueue.peek();

            if (buy.price >= sell.price) {
                int tradedQty = Math.min(buy.quantity, sell.quantity);
                double tradePrice = sell.price;

                System.out.println("TRADE " + tradedQty + " @ " + tradePrice);

                buy.quantity -= tradedQty;
                sell.quantity -= tradedQty;

                if (buy.quantity == 0) {
                    buyQueue.poll();
                }
                if (sell.quantity == 0) {
                    sellQueue.poll();
                }
            }
            else break;
        }
        return true;
    }

    public class TransactionComparator implements Comparator<Transaction> {
        @Override
        public int compare(Transaction t1, Transaction t2) {
            return Double.compare(Math.abs(t1.getLimit()-t1.getAsset().getMarketPrice()), Math.abs(t2.getLimit()-t2.getAsset().getMarketPrice()));
        }
    }
}