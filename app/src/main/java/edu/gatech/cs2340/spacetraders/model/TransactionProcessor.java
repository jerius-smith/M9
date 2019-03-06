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

    public static boolean validateSellingSpecificGood(Player player, Good toSell) {
        return player.getInventory().getStock(toSell) > 0;
    }

    // Checks whether the market has enough of a good the player is trying to
    // buy or sell
    public static boolean validateMarketCapacity(Market market, Good toCheck) {
        return market.getMarketInventory().getStock(toCheck) > 0;
    }

    public static boolean buyItem(Player player, Good toBuy, Market market) {
        double goodPrice = market.getPriceOfGood(toBuy);
        boolean result =
                validateCargoCapacity(player, player.getShip()) && validatePlayerMoney(player,
                                                                                       goodPrice)
                && validateMarketCapacity(market, toBuy);
        if (result == false) {
            return false;
        } else {
            player.setCredits(player.getCredits() - goodPrice);
            int currPlayerStockOfGood = player.getInventory().getStock(toBuy);
            player.getInventory().setStock(toBuy, currPlayerStockOfGood + 1);
            player.getInventory().adjustTotalStock(player.getInventory().getTotalStock() + 1);

            int currMarketStockOfGood = market.getMarketInventory().getStock(toBuy);
            market.getMarketInventory().setStock(toBuy, currMarketStockOfGood - 1);
            market.getMarketInventory()
                    .adjustTotalStock(market.getMarketInventory().getTotalStock() + 1);
            return true;
        }
    }

    public static boolean sellItem(Player player, Good toSell, Market market) {
        boolean result = validateSellingGoods(player) && validateSellingSpecificGood(player,
                                                                                     toSell);
        if (result == false) {
            return false;
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
            return true;
        }
    }


}
