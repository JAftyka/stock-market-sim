package com.stockmarket.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CommodityTest {

    @Test
    public void testInitializeCommodityWithDefaultValues() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);

        assertEquals("Au", commodity.getSymbol());
        assertEquals("Złoto", commodity.getName());
        assertEquals(200.0, commodity.getMarketPrice());
        assertEquals(5.0, commodity.getStorageCostPerUnitPerDay());
        assertEquals(20.0, commodity.getInitialStorageFeePerUnit());
    }

    @Test
    public void testInitializeCommodityWithGivenValues() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0, 2.0, 15.0);

        assertEquals(2.0, commodity.getStorageCostPerUnitPerDay());
        assertEquals(15.0, commodity.getInitialStorageFeePerUnit());
    }

    @Test
    public void testNullSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity(null, "Złoto", 200.0));
    }

    @Test
    public void testNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("Au", null, 200.0));
    }

    @Test
    public void testZeroMarketPriceThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("Au", "Złoto", 0.0));
    }

    @Test
    public void testNegativeMarketPriceThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("Au", "Złoto", -10.0));
    }

    @Test
    public void testZeroStorageCostThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("Au", "Złoto", 200.0, 0.0, 10.0));
    }

    @Test
    public void testNegativeStorageCostThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("Au", "Złoto", 200.0, -5.0, 10.0));
    }

    @Test
    public void testZeroInitialFeeThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("Au", "Złoto", 200.0, 2.0, 0.0));
    }

    @Test
    public void testNegativeInitialFeeThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commodity("Au", "Złoto", 200.0, 2.0, -10.0));
    }

    @Test
    public void testSetStorageCostPerUnitPerDay() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        commodity.setStorageCostPerUnitPerDay(7.5);

        assertEquals(7.5, commodity.getStorageCostPerUnitPerDay());
    }

    @Test
    public void testSetStorageCostPerUnitPerDayThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);

        assertThrows(IllegalArgumentException.class,
                () -> commodity.setStorageCostPerUnitPerDay(0));

        assertThrows(IllegalArgumentException.class,
                () -> commodity.setStorageCostPerUnitPerDay(-3));
    }

    @Test
    public void testSetInitialStorageFeePerUnit() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        commodity.setInitialStorageFeePerUnit(12.0);

        assertEquals(12.0, commodity.getInitialStorageFeePerUnit());
    }

    @Test
    public void testSetInitialStorageFeePerUnitThrowsException() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);

        assertThrows(IllegalArgumentException.class,
                () -> commodity.setInitialStorageFeePerUnit(0));

        assertThrows(IllegalArgumentException.class,
                () -> commodity.setInitialStorageFeePerUnit(-5));
    }

    @Test
    public void testCalculateLotValue() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 3, 150.0);

        assertEquals(600.0, commodity.calculateLotValue(lot));
    }

    @Test
    public void testCalculateValueOfAllLots() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0);

        commodity.addLot(LocalDate.now(), 2, 150.0);
        commodity.addLot(LocalDate.now(), 3, 160.0);

        // 5 sztuk * 200 PLN
        assertEquals(1000.0, commodity.calculateValueOfAllLots());
    }

    @Test
    public void testCalculateStorageCostForLot() {
        Commodity commodity = new Commodity("Au", "Złoto", 200.0, 2.0, 10.0);

        PurchaseLot lot = new PurchaseLot(LocalDate.now().minusDays(5), 4, 150.0);

        // 5 dni * 2 zł * 4 szt = 40
        // initial fee = 4 * 10 = 40
        // total = 80
        assertEquals(80.0,
                commodity.calculateStorageCostForLot(lot, LocalDate.now()));
    }
}
