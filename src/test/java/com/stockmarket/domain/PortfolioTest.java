package com.stockmarket.domain;

import com.stockmarket.logic.Portfolio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testAuditWithNoAssetsAdded() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(0.0, portfolio.audit(), 0.0001);
    }

    @Test
    void testInitializePortfolioWithNegativeInitialCash() {
        assertThrows(IllegalArgumentException.class, () -> new Portfolio(-1000.0));
    }

    @Test
    void testGetHoldingsCountWithAddedStock() {
        Portfolio portfolio = new Portfolio(500.0);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        portfolio.purchaseAsset(currency, 20);
        assertEquals(1, portfolio.getHoldingsCount());
    }
}