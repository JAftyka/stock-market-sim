package com.stockmarket.logic;

import com.stockmarket.domain.Share;
import com.stockmarket.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessLogicTest {

    @Test
    void testExpectedPurchaseCost() {
        Share share = new Share("XYZ", "XYZ Corp", 100.0);
        share.setHandlingFee(20.0);

        // koszt = quantity * unitPrice + handlingFee
        // = 10 * 100 + 20 = 1020
        assertEquals(1020.0, share.calculatePurchaseCost(10, 100.0), 0.0001);
    }

    @Test
    void testBuyingAssetWhenCashIsSufficient() {
        Share share = new Share("XYZ", "XYZ Corp", 100.0);
        share.setHandlingFee(20.0);

        Portfolio portfolio = new Portfolio(20000.0);

        portfolio.purchaseAsset(share, 10, 250.0);

        assertEquals(1, portfolio.getAssetCount());
        assertEquals(20000.0 - (10 * 250.0 + 20.0), portfolio.getCash(), 0.0001);
    }

    @Test
    void testBuyingAssetWhenCashIsInsufficientThrowsException() {
        Share share = new Share("XYZ", "XYZ Corp", 100.0);
        share.setHandlingFee(20.0);

        Portfolio portfolio = new Portfolio(200.0);

        assertThrows(InsufficientFundsException.class,
                () -> portfolio.purchaseAsset(share, 10, 300.0));
    }

    @Test
    void testInitializingPortfolioWithNegativeCashThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Portfolio(-100.0));
    }

    @Test
    void testAudit() {
        Portfolio portfolio = new Portfolio(10000.0);

        for (int i = 1; i <= 10; i++) {
            Share share = new Share("SYM" + i, "Some Share", 100.0);
            share.setHandlingFee(10.0);
            portfolio.purchaseAsset(share, 1, 110.0);
        }

        // 10 aktywów, każdy ma 1 sztukę po cenie rynkowej 100
        assertEquals(1000.0, portfolio.audit(), 0.0001);
    }

    @Test
    void testAuditWithNoAssetsAdded() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(0.0, portfolio.audit(), 0.0001);
    }
}
