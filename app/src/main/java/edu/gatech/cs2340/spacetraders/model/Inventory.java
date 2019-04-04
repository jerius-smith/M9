package edu.gatech.cs2340.spacetraders.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Inventory.
 */
public class Inventory {
    private final Map<Good, GoodAttributes> inventory;
    private int totalStock;

    /**
     * The type Good attributes.
     */
    private class GoodAttributes {
        private int stock;
        private double price;

        /**
         * Gets stock.
         *
         * @return the stock
         */
        public int getStock() {
            return stock;
        }

        /**
         * Sets stock.
         *
         * @param stock the stock
         */
        public void setStock(int stock) {
            this.stock = stock;
        }

        /**
         * Gets price.
         *
         * @return the price
         */
        public double getPrice() {
            return price;
        }

        /**
         * Sets price.
         *
         * @param price the price
         */
        public void setPrice(double price) {
            this.price = price;
        }

        /**
         * Instantiates a new Good attributes.
         */
        GoodAttributes() {
            this(0, 0);
        }

        /**
         * Instantiates a new Good attributes.
         *
         * @param stock the stock
         * @param price the price
         */
        GoodAttributes(int stock, double price) {
            this.stock = stock;
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (getClass() != o.getClass())) {
                return false;
            }
            GoodAttributes that = (GoodAttributes) o;
            return (stock == that.stock) && (Double.compare(that.price, price) == 0);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stock, price);
        }
    }

    /**
     * Instantiates a new Inventory.
     */
    public Inventory() {
        inventory = new HashMap<>();
        for (Good currentGood : Good.values()) {
            inventory.put(currentGood, new GoodAttributes());
        }
        totalStock = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Inventory inventory1 = (Inventory) o;
        return (totalStock == inventory1.totalStock) && Objects
                .equals(inventory, inventory1.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventory, totalStock);
    }

    /**
     * Gets total stock.
     *
     * @return the total stock
     */
    public int getTotalStock() {
        return totalStock;
    }

    /**
     * Sets stock.
     *
     * @param toChange the to change
     * @param stock    the stock
     */
    public void setStock(Good toChange, int stock) {
        Objects.requireNonNull(inventory.get(toChange)).stock = stock;
    }

    /**
     * Adjust total stock.
     *
     * @param adjustedStock the adjusted stock
     */
    public void adjustTotalStock(int adjustedStock) {
        totalStock = adjustedStock;
    }

    /**
     * Sets price.
     *
     * @param toChange the to change
     * @param price    the price
     */
    public void setPrice(Good toChange, double price) {
        Objects.requireNonNull(inventory.get(toChange)).price = price;
    }

    /**
     * Gets stock.
     *
     * @param toGet the to get
     * @return the stock
     */
    public int getStock(Good toGet) {
        return Objects.requireNonNull(inventory.get(toGet)).stock;
    }

    /**
     * Gets price.
     *
     * @param toGet the to get
     * @return the price
     */
    public double getPrice(Good toGet) {
        return Objects.requireNonNull(inventory.get(toGet)).price;
    }

//    /**
//     * Gets inventory.
//     *
//     * @return the inventory
//     */
//    public Map<Good, GoodAttributes> getInventory() {
//        return Collections.unmodifiableMap(inventory);
//    }

//    /**
//     * Sets inventory.
//     *
//     * @param inventory the inventory
//     */
//    public void setInventory(Map<Good, GoodAttributes> inventory) {
//        this.inventory = inventory;
//    }

//    /**
//     * Gets good attribute.
//     *
//     * @param good the good
//     * @return the good attribute
//     */
//    public GoodAttributes getGoodAttribute(Good good) {
//        return inventory.get(good);
//    }
}
