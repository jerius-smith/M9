package edu.gatech.cs2340.spacetraders.entities;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.Inventory;
import edu.gatech.cs2340.spacetraders.model.Market;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;
import edu.gatech.cs2340.spacetraders.model.Skills;

import static org.junit.Assert.*;

public class TransactionProcessorTest {
    private Player player;
    private Market market;
    private Planet planet;
    private Good toBuyExpensive;
    private Good toBuyCheap;
    private Inventory marketInventory;
    private Inventory playerInventory;
    private Ship playerShip;

    @Before
    public void setup() {
        Skills[] skillpoints = Skills.values();
        skillpoints[0].setPoints(8);
        skillpoints[1].setPoints(8);
        planet = new Planet("Vandor");
        player = new Player("John", Difficulty.EASY, skillpoints, planet);
        market = new Market(planet);
        toBuyExpensive = Good.ROBOTS;
        toBuyCheap = Good.FUR;
        playerShip = player.getShip();

        marketInventory = market.getMarketInventory();
        playerInventory = player.getInventory();

        marketInventory.setPrice(toBuyExpensive, 2000.0);
        marketInventory.setPrice(toBuyCheap, 300.0);
        marketInventory.setStock(toBuyExpensive, 1);
        marketInventory.setStock(toBuyCheap, 1);
    }

    @Test
    public void testBuyItem() {
        try {
            TransactionProcessor.buyItem(player, toBuyCheap, market);
        } catch (MarketActivityException e) {
            e.printStackTrace();
        }

        assertEquals(1, playerInventory.getStock(toBuyCheap));
        assertEquals(0, marketInventory.getStock(toBuyCheap));
        assertEquals(true, player.getCredits() == 700.0);

    }

    @Test(expected = MarketActivityException.class)
    public void testOutOfStock() throws MarketActivityException {
        TransactionProcessor.buyItem(player, toBuyCheap, market);
        TransactionProcessor.buyItem(player, toBuyCheap, market);
    }

    @Test(expected = MarketActivityException.class)
    public void testOutOfCredits() throws MarketActivityException {
        marketInventory.setStock(toBuyCheap, 10);

        for (int i = 0; i < 10; i++) {
            TransactionProcessor.buyItem(player, toBuyCheap, market);
        }
    }

    @Test(expected = MarketActivityException.class)
    public void testMaxCargo() throws MarketActivityException {
        playerInventory.adjustTotalStock(playerShip.getCargoCapacity());

        TransactionProcessor.buyItem(player,toBuyCheap,market);
    }


}