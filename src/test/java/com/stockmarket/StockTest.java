package com.stockmarket;

import com.stockmarket.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockTest {

  @Test
  void testInitializeStockWithNullSymbolThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Stock(null, "Name", 100.0));
  }

  @Test
  void testInitializeStockWithNullNameThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Stock("SYM", null, 100.0));
  }

  @Test
  void testInitializeStockWithNegativeInitialPriceThrowsException() {
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
  void testSetName() {
      Stock stock = new Stock("CDR", "CD Projekt", 100.0);
      stock.setName("CD Projekt Red");
      assertEquals("CD Projekt Red", stock.getName());
  }

  @Test
  void testSetNameWithNullNameThrowsException() {
      Stock stock = new Stock("CDR", "CD Projekt", 100.0);
      assertThrows(IllegalArgumentException.class, () -> stock.setName(null));
  }

  @Test
  void testSetInitialPrice() {
      Stock stock = new Stock("CDR", "CD Projekt", 100.0);
      stock.setInitialPrice(500.0);
      assertEquals(500, stock.getInitialPrice());
  }

  @Test
  void testSetNameWithNegativePriceThrowsException() {
      Stock stock = new Stock("CDR", "CD Projekt", 100.0);
      assertThrows(IllegalArgumentException.class, () -> stock.setInitialPrice(-100.00));
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
  void testEqualsBothStocksAreEqual() {
      Stock s1 = new Stock("CDR", "Name1", 100.0);
      Stock s2 = new Stock("CDR", "Name2", 200.0);
      assertTrue(s1.equals(s2) && s2.equals(s1));
  }

  @Test
  void testEqualsNull() {
    Stock stock = new Stock("CDR", "CD Projekt", 100.0);
    assertNotEquals(null, stock);
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
