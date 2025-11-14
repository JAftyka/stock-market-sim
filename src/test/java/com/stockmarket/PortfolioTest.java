package com.stockmarket;

import com.stockmarket.Portfolio;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

    @Test
    void testEmptyPortfolio() {
        Portfolio portfolio = new Portfolio(1000.0);

        assertEquals(1000.0, portfolio.getCash());
        assertEquals(0, portfolio.getHoldingsCount());
        assertEquals(0.0, portfolio.calculateStockValue());
        assertEquals(1000.0, portfolio.calculateTotalValue());
    }

    @Test
    void testAddFirstStock() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        portfolio.addStock(stock, 2);

        assertEquals(1, portfolio.getHoldingsCount());
        assertEquals(2, portfolio.getStockQuantity(stock));
    }

    @Test
    void testAddSameStockMultipleTimesSumsQuantities() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        portfolio.addStock(stock, 2);
        portfolio.addStock(stock, 3);

        assertEquals(1, portfolio.getHoldingsCount());
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
        assertEquals(1, portfolio.getStockQuantity(s1));
        assertEquals(2, portfolio.getStockQuantity(s2));
    }

    @Test
    void testCalculateStockValue() {
        Portfolio portfolio = new Portfolio(0.0);
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("PLW", "PlayWay", 50.0);

        portfolio.addStock(s1, 2); // 200
        portfolio.addStock(s2, 3); // 150

        assertEquals(350.0, portfolio.calculateStockValue());
    }

    @Test
    void testCalculateTotalValue() {
        Portfolio portfolio = new Portfolio(100.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        portfolio.addStock(stock, 3); // 300

        assertEquals(300.0, portfolio.calculateStockValue());
        assertEquals(400.0, portfolio.calculateTotalValue());
    }

    @Test
    void testGetStockQuantityForNonExistingStock() {
        Portfolio portfolio = new Portfolio(500.0);

        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        assertEquals(0, portfolio.getStockQuantity(stock));
    }

    @Test
    void testAddStockNullOrInvalidQuantityThrowsException() {
        Portfolio portfolio = new Portfolio(500.0);
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock(null, 1));
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock(stock, 0));
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock(stock, -5));
    }

    @Test
    void testAddStockWhenPortfolioIsFullThrowsException() {
        Portfolio portfolio = new Portfolio(500.0);

        // Create 10 different stocks
        for (int i = 0; i < 10; i++) {
            portfolio.addStock(new Stock("S" + i, "Stock" + i, 10.0), 1);
        }

        // Attempt to add the 11th stock should fail
        Stock extra = new Stock("EXTRA", "Extra Stock", 5.0);
        assertThrows(IllegalStateException.class, () -> portfolio.addStock(extra, 1));
    }

    @Test
    void testGetHoldingThrowsIndexException() {
        Portfolio portfolio = new Portfolio(500.0);

        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHolding(0));
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHolding(-1));
    }
}
