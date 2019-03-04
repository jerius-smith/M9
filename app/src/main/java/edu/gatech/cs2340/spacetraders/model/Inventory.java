package edu.gatech.cs2340.spacetraders.model;

import java.util.HashMap;
import java.util.Map;

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
    }

    public Inventory() {
        inventory = new HashMap<>();
        for (Good currentGood : Good.values()) {
            inventory.put(currentGood, new GoodAttributes());
        }
        totalStock = 0;
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Total stock: " + totalStock + "\n").
            append("This inventory has: \n");
        for (Good curr : inventory.keySet()) {
                GoodAttributes attribute = inventory.get(curr);
            str.append(attribute.stock + " units of " + curr + " priced at $" + attribute.price + " each\n");
        }
        return str.toString();
    }
}
