package com.stockmarket.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CommodityTest {

    @Test
    public void testDefaultConstructorInitializesValues() {
        Commodity c = new Commodity("AU", "Gold", 200.0);

        assertEquals("AU", c.getSymbol());
        assertEquals("Gold", c.getName());
        assertEquals(200.0, c.getMarketPrice());
        assertEquals(5.0, c.getStorageCostPerUnitPerDay());
        assertEquals(20.0, c.getInitialStorageFeePerUnit());
    }

    @Test
    public void testFullConstructorInitializesValues() {
        Commodity c = new Commodity("AU", "Gold", 200.0, 3.0, 12.0);

        assertEquals(3.0, c.getStorageCostPerUnitPerDay());
        assertEquals(12.0, c.getInitialStorageFeePerUnit());
    }

    @Test
    public void testConstructorThrowsOnZeroStorageCost() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("AU", "Gold", 200.0, 0.0, 10.0));
    }

    @Test
    public void testConstructorThrowsOnNegativeStorageCost() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("AU", "Gold", 200.0, -1.0, 10.0));
    }

    @Test
    public void testConstructorThrowsOnZeroInitialFee() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("AU", "Gold", 200.0, 2.0, 0.0));
    }

    @Test
    public void testConstructorThrowsOnNegativeInitialFee() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("AU", "Gold", 200.0, 2.0, -5.0));
    }

    @Test
    public void testSetStorageCostPerUnitPerDay() {
        Commodity c = new Commodity("AU", "Gold", 200.0);
        c.setStorageCostPerUnitPerDay(7.5);

        assertEquals(7.5, c.getStorageCostPerUnitPerDay());
    }

    @Test
    public void testSetStorageCostPerUnitPerDayThrows() {
        Commodity c = new Commodity("AU", "Gold", 200.0);

        assertThrows(IllegalArgumentException.class,
                () -> c.setStorageCostPerUnitPerDay(0));

        assertThrows(IllegalArgumentException.class,
                () -> c.setStorageCostPerUnitPerDay(-3));
    }

    @Test
    public void testSetInitialStorageFeePerUnit() {
        Commodity c = new Commodity("AU", "Gold", 200.0);
        c.setInitialStorageFeePerUnit(15.0);

        assertEquals(15.0, c.getInitialStorageFeePerUnit());
    }

    @Test
    public void testSetInitialStorageFeePerUnitThrows() {
        Commodity c = new Commodity("AU", "Gold", 200.0);

        assertThrows(IllegalArgumentException.class,
                () -> c.setInitialStorageFeePerUnit(0));

        assertThrows(IllegalArgumentException.class,
                () -> c.setInitialStorageFeePerUnit(-10));
    }

    @Test
    public void testCalculateLotValue() {
        Commodity c = new Commodity("AU", "Gold", 200.0);
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 3, 150.0);

        assertEquals(600.0, c.calculateLotValue(lot));
    }

    @Test
    public void testCalculateValueOfAllLots() {
        Commodity c = new Commodity("AU", "Gold", 200.0);

        c.addLot(LocalDate.now(), 2, 150.0);
        c.addLot(LocalDate.now(), 3, 160.0);

        assertEquals(1000.0, c.calculateValueOfAllLots());
    }

    @Test
    public void testCalculatePurchaseCost() {
        Commodity c = new Commodity("AU", "Gold", 200.0, 2.0, 10.0);

        // koszt = quantity * unitPrice + quantity * initialFee
        // = 5 * 150 + 5 * 10 = 750 + 50 = 800
        assertEquals(800.0, c.calculatePurchaseCost(5, 150.0));
    }

    @Test
    public void testCalculateRealSaleValue() {
        Commodity c = new Commodity("AU", "Gold", 200.0);

        assertEquals(600.0, c.calculateRealSaleValue(3, 200.0));
    }

    @Test
    public void testCalculateProfitFromLot() {
        Commodity c = new Commodity("AU", "Gold", 200.0, 2.0, 10.0);

        // dodajemy partię, żeby calculateStorageCost mogło ją znaleźć
        LocalDate purchaseDate = LocalDate.now().minusDays(5);
        c.addLot(purchaseDate, 4, 150.0);

        // storage cost:
        // days = 5
        // daily cost = 5 dni * 2 zł * 4 szt = 40
        // initial fee = 4 * 10 = 40
        // total storage = 80

        // profit = (sellPrice - lotPrice) * quantity - storage
        // = (200 - 150) * 4 - 80
        // = 50 * 4 - 80
        // = 200 - 80 = 120

        assertEquals(120.0, c.calculateProfitFromLot(4, 150.0, 200.0));
    }
}
