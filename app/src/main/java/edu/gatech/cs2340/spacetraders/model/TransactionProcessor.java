package edu.gatech.cs2340.spacetraders.model;

public class TransactionProcessor {

    // You cannot buy more goods than the cargo capacity
    public static boolean validateCargoCapacity(Player player, Ship toValidate) {
        return (player.getInventory().getTotalStock() < toValidate.getCargoCapacity());
    }

    // You cannot buy more goods than you have money
    public static boolean validatePlayerMoney(Player player, double marketPrice) {
        return (player.getCredits() >= marketPrice);
    }

    // You cannot sell more goods than you own
    public static boolean validateSellingGoods(Player player) {
        return player.getInventory().getTotalStock() > 0;
    }

    public boolean buyItem(Player player, Good toBuy) {

    }




}
