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
    void testSetInitialPriceWithNegativePriceThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setMarketPrice(-100.00));
    }

    @Test
    void testGetMarketPrice() {
        Share share = new Share("ABC", "Name", 100.0);
        assertEquals(100.0, share.getMarketPrice(), 0.0001);
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
    void testSetHandlingFeeWithNegativeFeeThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setHandlingFee(-100.00));
    }

    @Test
    void testSetHandlingFeeWithZeroFeeThrowsException() {
        Share share = new Share("ABC", "Name", 100.0);
        assertThrows(IllegalArgumentException.class, () -> share.setHandlingFee(0));
    }

    @Test
    void testCalculateRealValue() {
        Share share = new Share("ABC", "Name", 100.0);
        assertEquals(19985.0, share.calculateRealValue(200), 0.0001);
    }

    @Test
    void testCalculatePurchaseCost() {
        Share share = new Share("ABC", "Name", 100.0);
        assertEquals(20015.0, share.calculatePurchaseCost(200), 0.0001);
    }
}
