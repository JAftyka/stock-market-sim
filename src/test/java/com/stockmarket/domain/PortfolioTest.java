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

    //@Test
    //void testPurchaseAsset() {
    //    Portfolio portfolio = new Portfolio(500);
    //    Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
    //    portfolio.purchaseAsset(currency, 20);
    //    assertEquals(currency,portfolio.getHolding(0).getAsset());
    //}

    //@Test
    //void testPurchaseAssetWithExistingAsset() {
    //    Portfolio portfolio = new Portfolio(500);
    //    Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
    //    portfolio.purchaseAsset(currency,10);
    //    assertEquals(1,portfolio.getHoldingsCount());
    //}

    @Test
    void testPurchaseAssetWithNullAssetThrowsException() {
        Portfolio portfolio = new Portfolio(500);
        assertThrows(IllegalArgumentException.class, () -> portfolio.purchaseAsset(null,1));
    }

    @Test
    void testPurchaseAssetWithZeroQuantityThrowsException() {
        Portfolio portfolio = new Portfolio(500);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        assertThrows(IllegalArgumentException.class, () -> portfolio.purchaseAsset(currency,0));
    }

    @Test
    void testPurchaseAssetWithNegativeQuantityThrowsException() {
        Portfolio portfolio = new Portfolio(500);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        assertThrows(IllegalArgumentException.class, () -> portfolio.purchaseAsset(currency,-5));
    }

    @Test
    void testGetHoldingWithNegativeIndexThrowsException() {
        Portfolio portfolio = new Portfolio(500);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        portfolio.purchaseAsset(currency,1);
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHolding(-1));
    }

    @Test
    void testGetHoldingWithIndexGreaterThanHoldingsCountThrowsException() {
        Portfolio portfolio = new Portfolio(500);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        portfolio.purchaseAsset(currency,1);
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHolding(2));
    }

    //@Test
    //void testGetHolding() {
    //    Portfolio portfolio = new Portfolio(500);
    //    Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
    //    portfolio.purchaseAsset(currency,1);
    //    assertEquals(,portfolio.getHolding(0).);
    //}
}