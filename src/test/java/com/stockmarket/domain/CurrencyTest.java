package com.stockmarket.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyTest {
    @Test
    public void testInitializeCurrency() {
        Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 4.2284, 40);
        assertEquals(8.4568, currency.calculatePurchaseCost(2));
    }

    @Test
    public void testInitializeCurrencyWithNullSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Currency currency = new Currency(null, "Złoty/Euro", 4.2284, 40);});
    }

    @Test
    public void testInitializeCurrencyWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Currency currency = new Currency("PLN/EUR", null, 4.2284,40);});
    }

    @Test
    public void testInitializeCurrencyWithZeroPriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 0.0,40);});
    }

    @Test
    public void testInitializeCurrencyWithSpreadGreaterThanAskPriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 4.2284, 50000);});
    }

    @Test
    public void testInitializeCurrencyWithNegativeSpreadThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 4.2284, -40);});
    }

    @Test
    public void testGetSpread() {
        Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 4.2284, 40);
        assertEquals(40, currency.getSpread());
    }

    @Test
    public void testSetSpread() {
        Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 4.2284, 40);
        currency.setSpread(80);
        assertEquals(80, currency.getSpread());
    }

    @Test
    public void testSetSpreadWithNegativeSpreadThrowsException() {
        Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 4.2284, 40);
        assertThrows(IllegalArgumentException.class, () -> {currency.setSpread(-80);});
    }

    @Test
    public void testSetSpreadWithSpreadGreaterThanThrowsException() {
        Currency currency = new Currency("PLN/EUR", "Złoty/Euro", 4.2284, 40);
        assertThrows(IllegalArgumentException.class, () -> {currency.setSpread(50000);});
    }
}
