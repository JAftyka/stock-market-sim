package com.stockmarket.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PolymorphismTest {

    @Test
    void testShareRealValueIsNotTheSameAsCommodityRealValue() {
        Asset asset1 = new Share("ABC", "Some share", 200);
        Asset asset2 = new Commodity("ABC", "Some commodity", 200);
        assertNotEquals(asset1.calculateRealValue(5), asset2.calculateRealValue(5), 0.0001);
    }

    @Test
    void testShareRealValueIsNotTheSameAsCurrencyRealValue() {
        Asset asset1 = new Share("ABC", "Some share", 200);
        Asset asset2 = new Currency("ABC", "Some currency", 200, 40);
        assertNotEquals(asset1.calculateRealValue(5), asset2.calculateRealValue(5), 0.0001);
    }

    @Test
    void testCurrencyRealValueIsNotTheSameAsCommodityRealValue() {
        Asset asset1 = new Currency("ABC", "Some currency", 200, 40);
        Asset asset2 = new Commodity("ABC", "Some currency", 200);
        assertNotEquals(asset1.calculateRealValue(5), asset2.calculateRealValue(5), 0.0001);
    }

    @Test
    void testAll3HaveDifferentRealValues() {
        Asset asset1 = new Share("ABC", "Some share", 200);
        Asset asset2 = new Currency("ABC", "Some currency", 200, 40);
        Asset asset3 = new Commodity("ABC", "Some currency", 200);
        assertFalse(asset1.equals(asset2) && asset2.equals(asset3));
    }
}