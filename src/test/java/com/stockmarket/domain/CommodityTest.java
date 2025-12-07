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

    @Test
    public void testAddDays() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        commodity.addToDaysHeld(2);
        assertEquals(2,commodity.getDaysHeld());
    }

    @Test
    public void testAddDaysWithNegativeDaysThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        assertThrows(IllegalArgumentException.class, () -> {commodity.addToDaysHeld(-2);});
    }

    @Test
    public void testAddDaysWithZeroDaysThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        assertThrows(IllegalArgumentException.class, () -> {commodity.addToDaysHeld(0);});
    }

    @Test
    public void testSetInitialStorageFeePerUnit() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        commodity.setInitialStorageFeePerUnit(10);
        assertEquals(10,commodity.getInitialStorageFeePerUnit());
    }

    @Test
    public void testSetInitialStorageFeePerUnitWithNegativeFeeThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        assertThrows(IllegalArgumentException.class, () -> {commodity.setInitialStorageFeePerUnit(-10);});
    }

    @Test
    public void testSetInitialStorageFeePerUnitWithZeroFeeThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        assertThrows(IllegalArgumentException.class, () -> {commodity.setInitialStorageFeePerUnit(0);});
    }

    @Test
    public void testSetStorageCostPerUnitPerDay() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        commodity.setStorageCostPerUnitPerDay(10);
        assertEquals(10,commodity.getStorageCostPerUnitPerDay());
    }

    @Test
    public void testSetStorageCostPerUnitPerDayWithNegativeCostThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        assertThrows(IllegalArgumentException.class, () -> {commodity.setStorageCostPerUnitPerDay(-10);});
    }

    @Test
    public void testSetStorageCostPerUnitPerDayWithZeroCostThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        assertThrows(IllegalArgumentException.class, () -> {commodity.setStorageCostPerUnitPerDay(0);});
    }
}
