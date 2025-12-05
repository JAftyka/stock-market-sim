package com.stockmarket.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommodityTest {
    @Test
    public void testInitializeCommodityWithDefaultValues() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        assertEquals(440.0, commodity.calculatePurchaseCost(2));
    }

    @Test
    public void testInitializeCommodityWithGivenValues() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0, 2,15);
        assertEquals(340.0, commodity.calculateRealValue(2));
    }

    @Test
    public void testInitializeCommodityWithNullSymbol1ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity(null, "Złoto", 200.0);});
    }

    @Test
    public void testInitializeCommodityWithNullSymbol2ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity(null, "Złoto", 200.0, 2,15);});
    }

    @Test
    public void testInitializeCommodityWithNullName1ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity("Au", null, 200.0);});
    }

    @Test
    public void testInitializeCommodityWithNullName2ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity("Au", null, 200.0, 2,15);});
    }

    @Test
    public void testInitializeCommodityWithZeroPrice1ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity("Au", "Złoto", 0.0);});
    }

    @Test
    public void testInitializeCommodityWithZeroPrice2ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity("Au", "Złoto", 0.0, 2,15);});
    }

    @Test
    public void testInitializeCommodityWithNegativePrice1ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity("Au", "Złoto", -200.0);});
    }

    @Test
    public void testInitializeCommodityWithNegativePrice2ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity("Au", "Złoto", -200.0, 2,15);});
    }

    @Test
    public void testInitializeCommodityWithNullSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Commodity commodity = new Commodity(null, "Złoto", 200.0);});
    }

    @Test
    public void testNegativeDaysHeldThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {new Commodity("Au", "Złoto", 200.0, 2, -5);});
    }

    @Test
    public void testZeroStorageCostThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {new Commodity("Au", "Złoto", 200.0, 0, 10);});
    }

    @Test
    public void testNegativeStorageCostThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {new Commodity("Au", "Złoto", 200.0, -10, 10);});
    }
}
