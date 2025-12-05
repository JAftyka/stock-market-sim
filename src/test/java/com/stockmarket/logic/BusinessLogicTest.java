package com.stockmarket.logic;

import com.stockmarket.domain.Share;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusinessLogicTest {

    @Test
    void testExpectedPurchaseCost() {
        Share share = new Share("XYZ", "XYZ Corp", 100.0);
        share.setHandlingFee(20.0);

        assertEquals(1020.0, share.calculatePurchaseCost(10), 0.0001);
    }

    @Test
    void testBuyingAssetWhenCashIsSufficient() {
        Share share = new Share("XYZ", "XYZ Corp", 100.0);
        share.setHandlingFee(20.0);
        Portfolio portfolio = new Portfolio(20000.0);
        portfolio.purchaseAsset(share,10);

        assertEquals(1,portfolio.getHoldingsCount());
    }

    @Test
    void testBuyingAssetWhenCashIsInsufficientThrowsException() {
        Share share = new Share("XYZ", "XYZ Corp", 100.0);
        share.setHandlingFee(20.0);
        Portfolio portfolio = new Portfolio(200.0);

        assertThrows(IllegalStateException.class, () -> {portfolio.purchaseAsset(share, 10);});
    }

    @Test
    void testInitializingPortfolioWithNegativeCashThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Portfolio portfolio = new Portfolio(-100.0);});
    }

    @Test
    void testPurchasingMoreAssetsThanAllowedThrowsException() {
        Portfolio portfolio = new Portfolio(10000.0);
        for (int i = 1; i <= 10; i++) {
            Share share = new Share("SYM" + i, "Some Share", 100.0);
            share.setHandlingFee(10.0);
            portfolio.purchaseAsset(share, 1);
        }
        Share share = new Share("XYZ", "Extra Share", 100.0);
        assertThrows(IllegalStateException.class, () -> {portfolio.purchaseAsset(share, 1);});
    }

    @Test
    void testAudit() {
        Portfolio portfolio = new Portfolio(10000.0);
        for (int i = 1; i <= 10; i++) {
            Share share = new Share("SYM" + i, "Some Share", 100.0);
            share.setHandlingFee(10.0);
            portfolio.purchaseAsset(share, 1);
        }
        assertEquals(900,portfolio.audit());
    }

    @Test
    void testAuditWithNoAssetsAdded() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(0.0, portfolio.audit(), 0.0001);
    }
}