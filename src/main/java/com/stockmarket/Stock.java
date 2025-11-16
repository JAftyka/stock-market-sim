package com.stockmarket;

import java.util.Objects;

public class Stock {
  private String symbol;
  private String name;
  private double initialPrice;

  public Stock(String symbol, String name, double initialPrice) {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol cannot be null"); } 
    this.symbol = symbol; 
    if (name == null) { 
      throw new IllegalArgumentException("Name cannot be null"); 
    } 
    this.name = name;
    if (initialPrice < 0) {
      throw new IllegalArgumentException("Initial price cannot be negative");
    }
    this.initialPrice = initialPrice;

  }
  public String getSymbol() {
    return this.symbol;
  }
  public String getName() {
    return this.name;
  }
  public double getInitialPrice() {
    return this.initialPrice;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;           //true jesli to ten sam obiekt
    if (obj == null || getClass() != obj.getClass()) return false; //false jesli pusty lub inny typ
    Stock other = (Stock) obj;
    return Objects.equals(this.symbol, other.symbol); //sprawdzam czy symbole sa takie same
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(symbol);
  }
}
