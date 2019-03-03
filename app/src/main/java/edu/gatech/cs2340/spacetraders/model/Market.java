package edu.gatech.cs2340.spacetraders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Market {

    private Inventory marketInventory;

    public Market(Planet planet) {
        marketInventory = createInventory(planet);
    }

    private Inventory createInventory(Planet planet) {
        Inventory inventory = new Inventory();
        for (Good currentGood : Good.values()) {
            inventory.setStock(currentGood, randomStock(currentGood, planet));
            inventory.setPrice(currentGood, priceModel(currentGood, planet));
        }
        return inventory;
    }

    private int randomStock(Good good, Planet planet) {
        if (validateGood(good, planet)) {
            return new Random().nextInt(10) * 5;
        }
        return 0;
    }

    private double priceModel(Good good, Planet planet) {
        if (validateGood(good, planet)) {
            return good.getBASE_PRICE() + (good.getIPL() * (planet.getTechLevel().ordinal() - good
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

}
