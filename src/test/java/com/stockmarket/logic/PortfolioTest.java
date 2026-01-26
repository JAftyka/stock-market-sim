package com.stockmarket.logic;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.Currency;
import com.stockmarket.domain.Share;
import com.stockmarket.exceptions.InsufficientFundsException;
import com.stockmarket.exceptions.InsufficientQuantityException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

    @Test
    void testInitialCashIsStoredCorrectly() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertEquals(1000.0, portfolio.getCash(), 0.0001);
    }

    @Test
    void testConstructorRejectsNegativeCash() {
        assertThrows(IllegalArgumentException.class, () -> new Portfolio(-50));
    }

    @Test
    void testPurchaseAssetAddsNewHolding() {
        Portfolio portfolio = new Portfolio(5000);
        Asset eur = new Currency("EUR", "Euro", 4.0, 300);

        portfolio.purchaseAsset(eur, 10, 4.0);

        assertEquals(1, portfolio.getAssetCount());
        assertEquals(10, portfolio.getAssetQuantity("EUR"));
    }

    @Test
    void testPurchaseAssetRejectsNullAsset() {
        Portfolio portfolio = new Portfolio(5000);
        assertThrows(IllegalArgumentException.class, () -> portfolio.purchaseAsset(null, 10, 5.0));
    }

    @Test
    void testPurchaseAssetRejectsZeroQuantity() {
        Portfolio portfolio = new Portfolio(5000);
        Asset eur = new Currency("EUR", "Euro", 4.0, 300);

        assertThrows(IllegalArgumentException.class, () -> portfolio.purchaseAsset(eur, 0, 4.0));
    }

    @Test
    void testPurchaseAssetRejectsNegativeQuantity() {
        Portfolio portfolio = new Portfolio(5000);
        Asset eur = new Currency("EUR", "Euro", 4.0, 300);

        assertThrows(IllegalArgumentException.class, () -> portfolio.purchaseAsset(eur, -3, 4.0));
    }

    @Test
    void testPurchaseAssetRejectsNonPositivePrice() {
        Portfolio portfolio = new Portfolio(5000);
        Asset eur = new Currency("EUR", "Euro", 4.0, 300);

        assertThrows(IllegalArgumentException.class, () -> portfolio.purchaseAsset(eur, 5, 0));
    }

    @Test
    void testPurchaseAssetRejectsWhenInsufficientCash() {
        Portfolio portfolio = new Portfolio(10);
        Asset eur = new Currency("EUR", "Euro", 4.0, 300);

        assertThrows(InsufficientFundsException.class,
                () -> portfolio.purchaseAsset(eur, 10, 4.0));
    }

    @Test
    void testMultiplePurchasesAccumulateLots() {
        Portfolio portfolio = new Portfolio(5000);
        Asset eur = new Currency("EUR", "Euro", 4.0, 300);

        portfolio.purchaseAsset(eur, 10, 4.0);
        portfolio.purchaseAsset(eur, 5, 4.5);

        assertEquals(15, portfolio.getAssetQuantity("EUR"));
    }

    @Test
    void testSellAssetReducesQuantityFIFO1() {
        Portfolio portfolio = new Portfolio(10000);
        Asset share = new Share("XYZ", "Test Share", 150);

        // ręcznie dodajemy partie
        share.addLot(LocalDate.of(2023, 1, 1), 10, 100);
        share.addLot(LocalDate.of(2023, 2, 1), 10, 120);

        // rejestrujemy aktywo w portfelu
        portfolio.purchaseAsset(share, 1, 150);
        share.getLotDeque().getLotAt(0).setQuantity(10);
        share.getLotDeque().getLotAt(1).setQuantity(10);

        portfolio.sellAsset("XYZ", 15, 200.0);
        // pierwsza partia zeszła cała i została usunięta

        assertEquals(5, share.getLotDeque().getLotAt(0).getQuantity());
    }

    @Test
    void testSellAssetReducesQuantityFIFO2() {
        Portfolio portfolio = new Portfolio(10000);
        Asset share = new Share("XYZ", "Test Share", 150);

        // ręcznie dodajemy partie
        share.addLot(LocalDate.of(2023, 1, 1), 10, 100);
        share.addLot(LocalDate.of(2023, 2, 1), 10, 120);

        // rejestrujemy aktywo w portfelu
        portfolio.purchaseAsset(share, 1, 150);
        share.getLotDeque().getLotAt(0).setQuantity(10);
        share.getLotDeque().getLotAt(1).setQuantity(10);

        portfolio.sellAsset("XYZ", 15, 200.0);
        // pierwsza partia zeszła cała i została usunięta

        assertEquals(1, share.getLotDeque().getLotAt(1).getQuantity());
    }

    @Test
    void testSellAssetThrowsWhenNotEnoughQuantity() {
        Portfolio portfolio = new Portfolio(10000);
        Asset share = new Share("XYZ", "Test Share", 150);

        share.addLot(LocalDate.now(), 5, 100);
        portfolio.purchaseAsset(share, 1, 150);
        share.getLotDeque().getLotAt(0).setQuantity(5);

        assertThrows(InsufficientQuantityException.class,
                () -> portfolio.sellAsset("XYZ", 10, 200.0));
    }

    @Test
    void testAuditReturnsZeroWhenNoAssets() {
        Portfolio portfolio = new Portfolio(1000);
        assertEquals(0.0, portfolio.audit(), 0.0001);
    }

    @Test
    void testAuditSumsValueOfAllAssets() {
        Portfolio portfolio = new Portfolio(10000);

        Asset eur = new Currency("EUR", "Euro", 5.0, 300);
        Asset share = new Share("ABC", "Company ABC", 100);

        portfolio.purchaseAsset(eur, 10, 5.0);   // wartość 10 * bidPrice
        portfolio.purchaseAsset(share, 2, 100);  // wartość 2 * 100

        double expected = eur.calculateValueOfAllLots() + share.calculateValueOfAllLots();
        assertEquals(expected, portfolio.audit(), 0.0001);
    }
}
