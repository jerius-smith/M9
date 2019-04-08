package edu.gatech.cs2340.spacetraders.model;

import java.util.Random;

/**
 * The type Market.
 */
public class Market {

    /**
     * The constant DOUBLE.
     */
    private static final double DOUBLE = 100.0;
    /**
     * The constant BOUND.
     */
    private static final int BOUND = 50;
    private final Inventory marketInventory;

    /**
     * Instantiates a new Market.
     *
     * @param planet the planet
     */
    public Market(Planet planet) {
        marketInventory = createInventory(planet);
    }

//    /**
//     * Sets market inventory.
//     *
//     * @param marketInventory the market inventory
//     */
//    public void setMarketInventory(Inventory marketInventory) {
//        this.marketInventory = marketInventory;
//    }

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
            Random rand = new Random();
            return rand.nextInt(Market.BOUND);
        }
        return 0;
    }

    private double priceModel(Good good, Planet planet) {
        if (validateGood(good, planet)) {
            TechLevel tl = planet.getTechLevel();
            return good.getBASE_PRICE() + (Math.abs(good.getIPL()) * (
                    tl.ordinal() - good.getMTLP())) + computeVarianceFactor(
                    good);
        }
        return 0;
    }

    private double computeVarianceFactor(Good good) {
        Random rand = new Random();
        int flip = (rand.nextBoolean()) ? 1 : -1;
        return good.getBASE_PRICE() * flip * (rand.nextInt(good.getVarianceFactor())
                                              / DOUBLE);
    }

    private boolean validateGood(Good toValidate, Planet planet) {
        TechLevel tl = planet.getTechLevel();
        return toValidate.getMTLP() <= tl.ordinal();
    }


    /**
     * Gets market inventory.
     *
     * @return the market inventory
     */
    public Inventory getMarketInventory() {
        return marketInventory;
    }

    /**
     * Gets stock.
     *
     * @param toCheck the to check
     * @return the stock
     */
    public int getStock(Good toCheck) {
        return marketInventory.getStock(toCheck);
    }

    /**
     * Sets stock.
     *
     * @param toChange the to change
     * @param stock    the stock
     */
    public void setStock(Good toChange, int stock) {
        marketInventory.setStock(toChange, stock);
    }

    /**
     * Adjust total stock.
     *
     * @param stock the stock
     */
    public void adjustTotalStock(int stock) {
        marketInventory.adjustTotalStock(stock);
    }

    /**
     * Gets total stock.
     *
     * @return the total stock
     */
    public int getTotalStock() { return marketInventory.getTotalStock(); }


    /**
     * Gets price of good.
     *
     * @param toGetPrice the to get price
     * @return the price of good
     */
    public double getPriceOfGood(Good toGetPrice) {
        return marketInventory.getPrice(toGetPrice);
    }

}
