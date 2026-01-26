package com.stockmarket.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShareTest {

    @Test
    void testInitializeShareWithNullSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Share(null, "Name", 100.0));
    }

    @Test
    void testInitializeShareWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Share("ABC", null, 100.0));
    }

    @Test
    void testInitializeShareWithNegativeInitialPriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Share("ABC", "Name", -10.0));
    }

    @Test
    void testInitializeShareWithZeroInitialPriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Share("ABC", "Name", 0.0));
    }

    @Test
    void testGetSymbol() {
        Share share = new Share("ABC", "Name", 100.0);
        assertEquals("ABC", share.getSymbol());
    }

    @Test
    void testGetName() {
        Share share = new Share("ABC", "Name", 100.0);
        assertEquals("Name", share.getName());
    }

    @Test
    void testSetName() {
        Share share = new Share("ABC", "Name", 100.0);
        share.setName("Another name");
        assertEquals("Another name", share.getName());
    }

    @Test
    void testSetNameWithNullNameThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setName(null));
    }

    @Test
    void testSetMarketPrice() {
        Share share = new Share("ABC", "Name", 100.0);
        share.setMarketPrice(500);
        assertEquals(500, share.getMarketPrice());
    }

    @Test
    void testSetMarketPriceWithZeroThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setMarketPrice(0.0));
    }

    @Test
    void testSetMarketPriceWithNegativeThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setMarketPrice(-100.00));
    }

    @Test
    void testGetHandlingFee() {
        Share share = new Share("ABC", "Name", 100.0);
        assertEquals(15.0, share.getHandlingFee(), 0.0001);
    }

    @Test
    void testSetHandlingFee() {
        Share share = new Share("ABC", "Name", 100.0);
        share.setHandlingFee(20.0);
        assertEquals(20.0, share.getHandlingFee(), 0.0001);
    }

    @Test
    void testSetHandlingFeeWithZeroThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setHandlingFee(0));
    }

    @Test
    void testSetHandlingFeeWithNegativeThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setHandlingFee(-100.00));
    }

    @Test
    void testCalculatePurchaseCost() {
        Share share = new Share("ABC", "Name", 100.0);

        // koszt = quantity * unitPrice + handlingFee
        // = 200 * 100 + 15 = 20000 + 15 = 20015
        assertEquals(20015.0, share.calculatePurchaseCost(200, 100.0), 0.0001);
    }

    @Test
    void testCalculateRealSaleValue() {
        Share share = new Share("ABC", "Name", 100.0);

        // wpływ = quantity * sellPrice - handlingFee
        // = 200 * 100 - 15 = 20000 - 15 = 19985
        assertEquals(19985.0, share.calculateRealSaleValue(200, 100.0), 0.0001);
    }

    @Test
    void testCalculateProfitFromLot() {
        Share share = new Share("ABC", "Name", 100.0);

        // zysk = (sellPrice - lotPrice) * quantity - handlingFee
        // = (150 - 100) * 10 - 15
        // = 50 * 10 - 15 = 500 - 15 = 485
        assertEquals(485.0, share.calculateProfitFromLot(10, 100.0, 150.0), 0.0001);
    }

    @Test
    void testCalculateLotValue() {
        Share share = new Share("ABC", "Name", 100.0);
        PurchaseLot lot = new PurchaseLot(java.time.LocalDate.now(), 5, 80.0);

        assertEquals(500.0, share.calculateLotValue(lot));
    }

    @Test
    void testCalculateValueOfAllLots() {
        Share share = new Share("ABC", "Name", 100.0);

        share.addLot(java.time.LocalDate.now(), 3, 80.0);
        share.addLot(java.time.LocalDate.now(), 2, 90.0);

        // 5 sztuk * 100 PLN
        assertEquals(500.0, share.calculateValueOfAllLots());
    }
}
