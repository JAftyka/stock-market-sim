package com.stockmarket.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PolymorphismTest {

    @Test
    void testDifferentLotValueImplementations1() {
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 5, 100);

        Asset share = new Share("ABC", "Share", 200);
        Asset commodity = new Commodity("ABC", "Commodity", 200);
        Asset currency = new Currency("ABC", "Currency", 200, 40);

        double shareValue = share.calculateLotValue(lot);
        double commodityValue = commodity.calculateLotValue(lot);
        double currencyValue = currency.calculateLotValue(lot);

        assertNotEquals(shareValue, currencyValue, 0.0001);
    }

    @Test
    void testDifferentLotValueImplementations2() {
        PurchaseLot lot = new PurchaseLot(LocalDate.now(), 5, 100);

        Asset share = new Share("ABC", "Share", 200);
        Asset commodity = new Commodity("ABC", "Commodity", 200);
        Asset currency = new Currency("ABC", "Currency", 200, 40);

        double shareValue = share.calculateLotValue(lot);
        double commodityValue = commodity.calculateLotValue(lot);
        double currencyValue = currency.calculateLotValue(lot);

        assertNotEquals(commodityValue, currencyValue, 0.0001);
    }
}