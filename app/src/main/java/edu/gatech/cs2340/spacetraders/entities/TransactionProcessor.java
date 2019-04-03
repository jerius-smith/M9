package edu.gatech.cs2340.spacetraders.entities;

import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.Market;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;

@SuppressWarnings("FeatureEnvy")
public class TransactionProcessor {

    // You cannot buy more goods than the cargo capacity
    private static String validateCargoCapacity(Player player, Ship toValidate) {
        if (player.getInventory().getTotalStock() >= toValidate.getCargoCapacity()) {
            return "You cannot buy more goods than the cargo capacity\n";
        } else {
            return "";
        }
    }

    // You cannot buy more goods than you have money
    private static String validatePlayerMoney(Player player, double marketPrice) {
        if (player.getCredits() < marketPrice) {
            return "You cannot buy more goods than you have money\n";
        } else {
            return "";
        }
    }

    private static boolean validateSellingGoods(Player player) {
        return player.getInventory().getTotalStock() > 0;
    }

    // You cannot sell more goods than you own
    private static String validateSellingSpecificGood(Player player, Good toSell) {
        if ((player.getInventory().getStock(toSell) <= 0) || !validateSellingGoods(player)) {
            return "You cannot sell more goods than you own\n";
        } else {
            return "";
        }
    }

    // Checks whether the market has enough of a good the player is trying to
    // buy or sell
    private static String validateMarketCapacity(Market market, Good toCheck) {
        if (market.getMarketInventory().getStock(toCheck) <= 0) {
            return "Cannot buy item because market does not have enough\n";
        } else {
            return "";
        }
    }

    public static void buyItem(Player player, Good toBuy, Market market)
            throws MarketActivityException {
        double goodPrice = market.getPriceOfGood(toBuy);
        String result =
                validateCargoCapacity(player, player.getShip()) + validatePlayerMoney(player,
                                                                                      goodPrice)
                + validateMarketCapacity(market, toBuy);
        if (!result.isEmpty()) {
            throw new MarketActivityException(result);
        } else {
            player.setCredits(player.getCredits() - goodPrice);
            int currPlayerStockOfGood = player.getInventory().getStock(toBuy);
            player.getInventory().setStock(toBuy, currPlayerStockOfGood + 1);
            player.getInventory().adjustTotalStock(player.getInventory().getTotalStock() + 1);

            int currMarketStockOfGood = market.getMarketInventory().getStock(toBuy);
            market.getMarketInventory().setStock(toBuy, currMarketStockOfGood - 1);
            market.getMarketInventory()
                    .adjustTotalStock(market.getMarketInventory().getTotalStock() + 1);
        }
    }



    public static void sellItem(Player player, Good toSell, Market market)
            throws MarketActivityException {
        String result = validateSellingSpecificGood(player, toSell);
        if (!result.isEmpty()) {
            throw new MarketActivityException(result);
        } else {
            double goodPrice = market.getPriceOfGood(toSell);
            player.setCredits(player.getCredits() + goodPrice);
            int currPlayerStockOfGood = player.getInventory().getStock(toSell);
            player.getInventory().setStock(toSell, currPlayerStockOfGood - 1);
            player.getInventory().adjustTotalStock(player.getInventory().getTotalStock() - 1);

            int currMarketStockOfGood = market.getMarketInventory().getStock(toSell);
            market.getMarketInventory().setStock(toSell, currMarketStockOfGood + 1);
            market.getMarketInventory()
                    .adjustTotalStock(market.getMarketInventory().getTotalStock() - 1);
        }
    }


}
