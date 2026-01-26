package com.stockmarket.io;

import com.stockmarket.domain.*;
import com.stockmarket.exceptions.DataIntegrityException;
import com.stockmarket.logic.Portfolio;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class PortfolioPersistence {

    private static final String HEADER_PREFIX = "HEADER";
    private static final String ASSET_PREFIX = "ASSET";
    private static final String LOT_PREFIX = "LOT";
    private static final String SEPARATOR = "\\|";

    // -------- ZAPIS --------

    public void save(Portfolio portfolio, File file) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            // HEADER
            writer.write("HEADER|CASH|" + portfolio.getCash());
            writer.newLine();

            // ASSETS + LOTS
            for (Asset asset : portfolio.getAllAssets()) {
                writer.write(buildAssetLine(asset));
                writer.newLine();

                PurchaseLotDeque queue = asset.getLotDeque();
                for (PurchaseLot lot : queue) {
                    writer.write(buildLotLine(lot));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error while saving portfolio to file", e);
        }
    }

    private String buildAssetLine(Asset asset) {
        return "ASSET"
                + "|" + asset.getType().name()
                + "|" + asset.getSymbol()
                + "|" + asset.getName()
                + "|" + asset.getMarketPrice();
    }

    private String buildLotLine(PurchaseLot lot) {
        return "LOT"
                + "|" + lot.getPurchaseDate()
                + "|" + lot.getQuantity()
                + "|" + lot.getUnitPrice();
    }

    // -------- ODCZYT --------

    public Portfolio load(File file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line = reader.readLine();
            if (line == null || !line.startsWith(HEADER_PREFIX)) {
                throw new DataIntegrityException("Missing or invalid HEADER line");
            }

            double cash = parseHeaderCash(line);
            Portfolio portfolio = new Portfolio(cash);

            Asset currentAsset = null;
            int declaredQuantityForAsset = 0;
            int sumLotsForAsset = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(SEPARATOR);
                if (parts.length == 0) {
                    continue;
                }

                String prefix = parts[0];

                if (ASSET_PREFIX.equals(prefix)) {
                    // jeśli kończymy poprzednie aktywo → walidacja sumy LOT
                    if (currentAsset != null) {
                        int actualQuantity = currentAsset.getTotalQuantity();
                        if (actualQuantity != sumLotsForAsset) {
                            throw new DataIntegrityException("Quantity mismatch for asset: " + currentAsset.getSymbol());
                        }
                    }

                    currentAsset = parseAssetLine(parts);
                    portfolio.registerLoadedAsset(currentAsset);
                    sumLotsForAsset = 0;

                } else if (LOT_PREFIX.equals(prefix)) {
                    if (currentAsset == null) {
                        throw new DataIntegrityException("LOT line without preceding ASSET");
                    }
                    PurchaseLot lot = parseLotLine(parts);
                    currentAsset.getLotDeque().addLot(lot);
                    sumLotsForAsset += lot.getQuantity();

                } else {
                    throw new DataIntegrityException("Unknown line prefix: " + prefix);
                }
            }

            // walidacja ostatniego aktywa
            if (currentAsset != null) {
                int actualQuantity = currentAsset.getTotalQuantity();
                if (actualQuantity != sumLotsForAsset) {
                    throw new DataIntegrityException("Quantity mismatch for asset: " + currentAsset.getSymbol());
                }
            }

            return portfolio;

        } catch (IOException e) {
            throw new RuntimeException("Error while loading portfolio from file", e);
        }
    }

    private double parseHeaderCash(String line) {
        String[] parts = line.split(SEPARATOR);
        if (parts.length != 3) {
            throw new DataIntegrityException("Invalid HEADER format");
        }
        try {
            return Double.parseDouble(parts[2]);
        } catch (NumberFormatException e) {
            throw new DataIntegrityException("Invalid cash value in HEADER");
        }
    }

    private Asset parseAssetLine(String[] parts) {
        if (parts.length != 5) {
            throw new DataIntegrityException("Invalid ASSET line format");
        }

        String typeStr = parts[1];
        String symbol = parts[2];
        String name = parts[3];
        double marketPrice;

        try {
            marketPrice = Double.parseDouble(parts[4]);
        } catch (NumberFormatException e) {
            throw new DataIntegrityException("Invalid market price for asset: " + symbol);
        }

        AssetType type;
        try {
            type = AssetType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            throw new DataIntegrityException("Unknown asset type: " + typeStr);
        }

        if (type == AssetType.SHARE) {
            return new Share(symbol, name, marketPrice);
        } else if (type == AssetType.CURRENCY) {
            return new Currency(symbol, name, marketPrice, 0);
        } else if (type == AssetType.COMMODITY) {
            return new Commodity(symbol, name, marketPrice);
        } else {
            throw new DataIntegrityException("Unsupported asset type: " + type);
        }
    }

    private PurchaseLot parseLotLine(String[] parts) {
        if (parts.length != 4) {
            throw new DataIntegrityException("Invalid LOT line format");
        }

        LocalDate date;
        int quantity;
        double unitPrice;

        try {
            date = LocalDate.parse(parts[1]);
        } catch (Exception e) {
            throw new DataIntegrityException("Invalid LOT date: " + parts[1]);
        }

        try {
            quantity = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new DataIntegrityException("Invalid LOT quantity: " + parts[2]);
        }

        try {
            unitPrice = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            throw new DataIntegrityException("Invalid LOT unit price: " + parts[3]);
        }

        return new PurchaseLot(date, quantity, unitPrice);
    }
}
