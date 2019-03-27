package edu.gatech.cs2340.spacetraders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Market {

    private Inventory marketInventory;

    public Market(Planet planet) {
        marketInventory = createInventory(planet);
    }

    public void setMarketInventory(Inventory marketInventory) {
        this.marketInventory = marketInventory;
    }

    public Market() {
    }

    private Inventory createInventory(Planet planet) {
        Inventory inventory = new Inventory();
        for (Good currentGood : Good.values()) {
            int randStock = randomStock(currentGood, planet);
            double computedPrice = priceModel(currentGood, planet);
            if (computedPrice < 0) {
                computedPrice = priceModel(currentGood, planet);
            }
            inventory.setStock(currentGood, randStock);
            inventory.setPrice(currentGood, computedPrice);
            inventory.adjustTotalStock(inventory.getTotalStock() + randStock);
        }
        return inventory;
    }


    private int randomStock(Good good, Planet planet) {
        if (validateGood(good, planet)) {
            return new Random().nextInt(50);
        }
        return 0;
    }

    private double priceModel(Good good, Planet planet) {
        if (validateGood(good, planet)) {
            return good.getBASE_PRICE() + (Math.abs(good.getIPL()) * (planet.getTechLevel().ordinal() - good
                    .getMTLP())) + computeVarianceFactor(good);
        }
        return 0;
    }

    private double computeVarianceFactor(Good good) {
        int flip = (new Random().nextBoolean()) ? 1 : -1;
        return good.getBASE_PRICE() * flip * (new Random().nextInt(good.getVarianceFactor())
                                              / 100.0);
    }

    private boolean validateGood(Good toValidate, Planet planet) {
        return toValidate.getMTLP() <= planet.getTechLevel().ordinal();
    }


    public Inventory getMarketInventory() {
        return marketInventory;
    }

    public double getPriceOfGood(Good toGetPrice) {
        return marketInventory.getPrice(toGetPrice);
    }

}
