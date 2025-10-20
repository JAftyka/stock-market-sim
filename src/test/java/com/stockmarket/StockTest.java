package com.stockmarket;

import com.stockmarket.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
  
  @Test
  void testCreate() {
    String symbol = "CDR";
    String name ="CD Project Red";
    double initialPrice = 2500;
    Stock stock = new Stock(symbol, name, initialPrice);
    assertNotNull(stock);
  }

  @Test
  void testGetters() {
    Stock stock = new Stock("CDR", "CD Project Red", 2500);
    assertEquals("CDR",stock.getSymbol());
    assertEquals("CD Project Red",stock.getName());
    assertEquals(2500,stock.getInitialPrice());
  }

  @Test
  void testEqualsAndHashCode() {
    Stock stock1 = new Stock("CDR", "CD Project Red", 2500);
    Stock stock2 = new Stock("CDR", "CD Project Red", 2000);
    Stock stock3 = new Stock("PGE", "Polska Grupa Energetyczna", 4000);
    assertEquals(stock1,stock2);
    assertEquals(stock1.hashCode(), stock2.hashCode());
    assertNotEquals(stock1,stock3);
  }
}
