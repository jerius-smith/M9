package edu.gatech.cs2340.spacetraders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Inventory {
    private Map<Good, GoodAttributes> inventory;
    private int totalStock;

    private class GoodAttributes {
        private int stock;
        private double price;

        private GoodAttributes() {
            this(0, 0);
        }

        private GoodAttributes(int stock, double price) {
            this.stock = stock;
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            GoodAttributes that = (GoodAttributes) o;
            return stock == that.stock && Double.compare(that.price, price) == 0;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inventory inventory1 = (Inventory) o;
        return totalStock == inventory1.totalStock && Objects
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
        inventory.get(toChange).stock = stock;
    }

    public void adjustTotalStock(int adjustedStock) {
        totalStock = adjustedStock;
    }

    public void setPrice(Good toChange, double price) {
        inventory.get(toChange).price = price;
    }

    public int getStock(Good toGet) {
        return inventory.get(toGet).stock;
    }

    public double getPrice(Good toGet) {
        return inventory.get(toGet).price;
    }

    public Map<Good, GoodAttributes> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Good, GoodAttributes> inventory) {
        this.inventory = inventory;
    }

    public GoodAttributes getGoodAttribute(Good good) {
        return inventory.get(good);
    }
}
