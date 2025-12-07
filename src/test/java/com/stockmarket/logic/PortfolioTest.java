package com.stockmarket.domain;

import com.stockmarket.logic.Portfolio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PortfolioTest {

    @Test
    void testGetMaxHoldings() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(10,portfolio.getMaxHoldings());
    }

    @Test
    void testGetCash() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(1000.0, portfolio.getCash(), 0.0001);
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
    void testGetHoldingsCountWithNoStocksAdded() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(0, portfolio.getHoldingsCount());
    }

    @Test
    void testPurchaseAsset() {
        Portfolio portfolio = new Portfolio(500);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        portfolio.purchaseAsset(currency, 20);
        assertEquals(1, portfolio.getHoldingsCount());
    }

    @Test
    void testPurchaseMultipleDifferentAssets() {
        Portfolio portfolio = new Portfolio(5000);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        Asset share = new Share("CDR","CD Projekt", 100);
        portfolio.purchaseAsset(currency,10);
        portfolio.purchaseAsset(share,2);
        assertEquals(2, portfolio.getHoldingsCount());
    }

    @Test
    void testPurchaseOneAssetMultipleTimes() {
        Portfolio portfolio = new Portfolio(5000);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        portfolio.purchaseAsset(currency,10);
        portfolio.purchaseAsset(currency,2);
        assertEquals(12, portfolio.getHoldingQuantity(0));
    }

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
    void testGetHoldingQuantityWithNegativeIndexThrowsException() {
        Portfolio portfolio = new Portfolio(500);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        portfolio.purchaseAsset(currency,1);
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHoldingQuantity(-1));
    }

    @Test
    void testGetHoldingQuantityWithIndexGreaterThanHoldingsCountThrowsException() {
        Portfolio portfolio = new Portfolio(500);
        Asset currency = new Currency("EUR", "Euro", 4.2419, 300);
        portfolio.purchaseAsset(currency,1);
        assertThrows(IndexOutOfBoundsException.class, () -> portfolio.getHoldingQuantity(2));
    }
}