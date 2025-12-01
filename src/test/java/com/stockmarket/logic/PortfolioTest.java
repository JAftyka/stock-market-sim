/*package com.stockmarket;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

    @Test
    void testGetCash() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(1000.0, portfolio.getCash(), 0.0001);
    }

    @Test
    void testGetHoldingsCountWithNoStocksAdded() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(0, portfolio.getHoldingsCount());
    }

    @Test
    void testCalculateStockValueWithNoStocksAdded() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(0.0, portfolio.calculateStockValue(), 0.0001);
    }

    @Test
    void testCalculateTotalValueWithNoStocksAdded() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(1000.0, portfolio.calculateTotalValue(), 0.0001);
    }

    @Test
    void testInitializePortfolioWithNegativeInitialCash() {
        assertThrows(IllegalArgumentException.class, () ->  new Portfolio(-1000.0));
    }

    @Test
    void testGetHoldingsCountWithAddedStock() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 2);
        assertEquals(1, portfolio.getHoldingsCount());
    }
    
    @Test
    void testGetStockQuantity() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 2);
        assertEquals(2, portfolio.getStockQuantity(stock));
    }

    @Test
    void testGetHoldingsCountWithSameStockAddedMultipleTimes() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 2);
        portfolio.addStock(stock, 3);
        assertEquals(1, portfolio.getHoldingsCount());
    }

    @Test
    void testGetStockQuantityWithSameStockAddedMultipleTimes() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 2);
        portfolio.addStock(stock, 3);
        assertEquals(5, portfolio.getStockQuantity(stock));
    }

    @Test
    void testAddDifferentStocksCreatesSeparateHoldings() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("PLW", "PlayWay", 50.0);
        portfolio.addStock(s1, 1);
        portfolio.addStock(s2, 2);
        assertEquals(2, portfolio.getHoldingsCount());
    }

    @Test
    void testGetStockQuantityOnTwoDifferentStocksNr1() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("PLW", "PlayWay", 50.0);
        portfolio.addStock(s1, 1);
        portfolio.addStock(s2, 2);
        assertEquals(1, portfolio.getStockQuantity(s1));
    }

    @Test
    void testGetStockQuantityOnTwoDifferentStocksNr2() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("PLW", "PlayWay", 50.0);
        portfolio.addStock(s1, 1);
        portfolio.addStock(s2, 2);
        assertEquals(2, portfolio.getStockQuantity(s2));
    }

    @Test
    void testCalculateStockValue() {
        Portfolio portfolio = new Portfolio(0.0);
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("PLW", "PlayWay", 50.0);
        portfolio.addStock(s1, 2); // 200
        portfolio.addStock(s2, 3); // 150
        assertEquals(350.0, portfolio.calculateStockValue(), 0.0001);
    }

    @Test
    void testCalculateStockValueWithOneAddedStock() {
        Portfolio portfolio = new Portfolio(100.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 3); // 300
        assertEquals(300.0, portfolio.calculateStockValue(), 0.0001);
    }

    @Test
    void testCalculateTotalValueWithOneAddedStock() {
        Portfolio portfolio = new Portfolio(100.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 3); // 300
        assertEquals(400.0, portfolio.calculateTotalValue(), 0.0001);
    }

    @Test
    void testGetStockQuantityForNonExistingStock() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        assertEquals(0, portfolio.getStockQuantity(stock));
    }
    @Test
    void testGetStockQuantityForNullStockThrowsException() {
        Portfolio portfolio = new Portfolio(500.0);
        assertThrows(IllegalArgumentException.class, () -> portfolio.getStockQuantity(null));
    }

    @Test
    void testAddStockWith0QuantityThrowsException() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock(stock, 0));
    }

    @Test
    void testAddStockWithNegativeQuantityThrowsException() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock(stock, -5));
    }

    @Test
    void testAddStockWithNullStockThrowsException() {
        Portfolio portfolio = new Portfolio(500.0);
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock(null, 1));
    }

    @Test
    void testAddStockWhenPortfolioIsFullThrowsException() {
        Portfolio portfolio = new Portfolio(500.0);
        
        for (int i = 0; i < portfolio.getMaxHoldings(); i++) {
            portfolio.addStock(new Stock("S" + i, "Stock" + i, 10.0), 1);
        }
        Stock extra = new Stock("EXTRA", "Extra Stock", 5.0);
        assertThrows(IllegalStateException.class, () -> portfolio.addStock(extra, 1));
    }

    @Test
    void testGetHoldingQuantity() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 3);
        assertEquals(3, portfolio.getStockQuantity(stock));
    }

    @Test
    void testGetHoldingValue() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 3);
        assertEquals(300.0, portfolio.calculateStockValue(), 0.0001);
    }

    @Test
    void testGetHoldingWithIndex0OnEmptyPortfolioThrowsIndexException() {
        Portfolio portfolio = new Portfolio(500.0);
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHolding(0));
    }

    @Test
    void testGetHoldingWithNegativeIndexThrowsIndexException() {
        Portfolio portfolio = new Portfolio(500.0);
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHolding(-1));
    }

    @Test
    void testGetHoldingIndexOutOfBoundsThrowsIndexException() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);
        portfolio.addStock(stock, 5);
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHolding(1));
    }
}*/