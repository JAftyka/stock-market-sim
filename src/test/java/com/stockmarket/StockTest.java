package com.stockmarket;

import com.stockmarket.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
  
  @Test
    void testConstructorAndGetters() {
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        assertEquals("CDR", stock.getSymbol());
        assertEquals("CD Projekt", stock.getName());
        assertEquals(100.0, stock.getInitialPrice());
    }

    @Test
    void testEqualsSameObject() {
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        assertEquals(stock, stock);
    }

    @Test
    void testEqualsNull() {
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        assertNotEquals(stock, null);
    }

    @Test
    void testEqualsDifferentClass() {
        Stock stock = new Stock("CDR", "CD Projekt", 100.0);

        assertNotEquals(stock, "Not a stock");
    }

    @Test
    void testEqualsSameSymbolDifferentNameAndPrice() {
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("CDR", "Some Other Name", 999.9);

        assertEquals(s1, s2);
    }

    @Test
    void testEqualsDifferentSymbol() {
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("XYZ", "X Corp", 50.0);

        assertNotEquals(s1, s2);
    }

    @Test
    void testHashCodeSameSymbol() {
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("CDR", "Another Name", 500.0);

        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testHashCodeDifferentSymbol() {
        Stock s1 = new Stock("CDR", "CD Projekt", 100.0);
        Stock s2 = new Stock("XYZ", "Other", 200.0);

        assertNotEquals(s1.hashCode(), s2.hashCode());
    }
}
