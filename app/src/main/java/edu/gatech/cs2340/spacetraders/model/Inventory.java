package edu.gatech.cs2340.spacetraders.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Inventory {
    private Map<Good, GoodAttributes> inventory;
    private int totalStock;

    public class GoodAttributes {
        private int stock;
        private double price;

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        GoodAttributes() {
            this(0, 0);
        }

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

    public int getTotalStock() {
        return totalStock;
    }

    public void setStock(Good toChange, int stock) {
        Objects.requireNonNull(inventory.get(toChange)).stock = stock;
    }

    public void adjustTotalStock(int adjustedStock) {
        totalStock = adjustedStock;
    }

    public void setPrice(Good toChange, double price) {
        Objects.requireNonNull(inventory.get(toChange)).price = price;
    }

    public int getStock(Good toGet) {
        return Objects.requireNonNull(inventory.get(toGet)).stock;
    }

    public double getPrice(Good toGet) {
        return Objects.requireNonNull(inventory.get(toGet)).price;
    }

    public Map<Good, GoodAttributes> getInventory() {
        return Collections.unmodifiableMap(inventory);
    }

    public void setInventory(Map<Good, GoodAttributes> inventory) {
        this.inventory = inventory;
    }

    public GoodAttributes getGoodAttribute(Good good) {
        return inventory.get(good);
    }
}
