package com.stockmarket;

import com.stockmarket.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockTest {

  @Test
  void testInitializeStockWithNullSymbol() {
    assertThrows(IllegalArgumentException.class, () -> new Stock(null, "Name", 100.0));
  }

  @Test
  void testInitializeStockWithNullName() {
    assertThrows(IllegalArgumentException.class, () -> new Stock("SYM", null, 100.0));
  }

  @Test
  void testInitializeStockWithNegativeInitialPrice() {
    assertThrows(IllegalArgumentException.class, () -> new Stock("SYM", "Name", -10.0));
  }
  
  @Test
  void testGetSymbol() {
      Stock stock = new Stock("CDR", "CD Projekt", 100.0);
      assertEquals("CDR", stock.getSymbol());
  }

  @Test
  void testGetName() {
    Stock stock = new Stock("CDR", "CD Projekt", 100.0);
    assertEquals("CD Projekt", stock.getName());
  }

  @Test
  void testGetInitialPrice() {
    Stock stock = new Stock("CDR", "CD Projekt", 100.0);
    assertEquals(100.0, stock.getInitialPrice(), 0.0001);
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
