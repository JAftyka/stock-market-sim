package com.stockmarket.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PolymorphismTest {

    @Test
    void testShareAndCommodityHaveSameLotValueLogic() {
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 5, 100);

        Asset share = new Share("ABC", "Share", 200);
        Asset commodity = new Commodity("ABC", "Commodity", 200);

        double shareValue = share.calculateLotValue(lot);
        double commodityValue = commodity.calculateLotValue(lot);

        // 5 * 200 = 1000
        assertEquals(shareValue, commodityValue, 0.0001);
    }

    @Test
    void testCurrencyHasDifferentLotValueLogic() {
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 5, 100);

        Asset currency = new Currency("ABC", "Currency", 200, 40);

        double currencyValue = currency.calculateLotValue(lot);

        // currencyValue = 5 * (200 - 0.0040) = 999.98
        assertNotEquals(1000.0, currencyValue, 0.0001);
    }

    @Test
    void testDifferentLotValueImplementations1() {
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 5, 100);

        Asset share = new Share("ABC", "Share", 200);
        Asset currency = new Currency("ABC", "Currency", 200, 40);

        double shareValue = share.calculateLotValue(lot);
        double currencyValue = currency.calculateLotValue(lot);

        assertNotEquals(shareValue, currencyValue, 0.0001);
    }

    @Test
    void testDifferentLotValueImplementations2() {
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 5, 100);

        Asset commodity = new Commodity("ABC", "Commodity", 200);
        Asset currency = new Currency("ABC", "Currency", 200, 40);

        double commodityValue = commodity.calculateLotValue(lot);
        double currencyValue = currency.calculateLotValue(lot);

        assertNotEquals(commodityValue, currencyValue, 0.0001);
    }
}
