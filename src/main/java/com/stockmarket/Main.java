package com.stockmarket;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.Share;
import com.stockmarket.io.PortfolioPersistence;
import com.stockmarket.logic.Portfolio;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        File file = new File("data/portfolio.txt");
        file.getParentFile().mkdirs();

        PortfolioPersistence persistence = new PortfolioPersistence();
        Portfolio portfolio;

        if (file.exists()) {
            System.out.println("Wczytywanie portfela z pliku...");
            portfolio = persistence.load(file);
        } else {
            System.out.println("Tworzenie nowego portfela...");
            portfolio = new Portfolio(10000.0);
        }

        System.out.println("Kupujemy akcję ABC");
        Asset share = new Share("ABC", "Share", 200);
        portfolio.purchaseAsset(share, 5, 200);

        System.out.println("Stan gotówki: " + portfolio.getCash());
        System.out.println("Wartość portfela: " + portfolio.audit());

        persistence.save(portfolio, file);
        System.out.println("Zapisano portfel.");
    }
}
