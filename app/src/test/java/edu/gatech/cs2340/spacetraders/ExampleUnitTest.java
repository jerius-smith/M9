package edu.gatech.cs2340.spacetraders;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;
import edu.gatech.cs2340.spacetraders.entities.TravelException;
import edu.gatech.cs2340.spacetraders.entities.TravelProcessor;
import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;
import edu.gatech.cs2340.spacetraders.model.Skills;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("MagicNumber")
public class ExampleUnitTest {


    private Player player;
    private Planet currentLocation;
    private Planet destination;



    @Before
    public void setUp() {
        currentLocation =
                new Planet(GameLogistics.PLANET_NAMES[GameLogistics.PLANET_NAMES.length - 1]);
        destination = new Planet(GameLogistics.PLANET_NAMES[0]);

        Skills[] points = Skills.values();
        points[0].setPoints(2);
        points[1].setPoints(4);
        points[2].setPoints(6);
        points[3].setPoints(4);
        player = new Player("M10", Difficulty.HARD, points, currentLocation);
    }


    @Test(expected = TravelException.class)
    public void testTravelingException() throws TravelException {
        Ship currentShip = player.getShip();
        currentShip.setFuelCapacity(1);
        TravelProcessor.validateTraveling(player, destination);
    }

    @Test
    public void testExceptionPostCondition() {
        try {
            Ship currentShip = player.getShip();
            currentShip.setFuelCapacity(1);
            TravelProcessor.validateTraveling(player, destination);
        }  catch (TravelException e) {
            assertNotNull(player);
            assertNotNull(player.getLocation());
            assertNotNull(destination);
            assertEquals(currentLocation, player.getLocation());
            assertEquals(1, player.getShipFuelCapacity());
            assertEquals("M10", player.getName());
        }
    }

    @Test
    public void testTraveling() throws TravelException {
        assertNotNull(player);
        assertNotNull(currentLocation);
        assertEquals(player.getLocation(), currentLocation);
        assertNotEquals(player.getLocation(), destination);
        assertEquals(80, player.getShipFuelCapacity());

        TravelProcessor.validateTraveling(player, destination);

        assertNotNull(player);
        assertNotNull(currentLocation);
        assertNotEquals(currentLocation, player.getLocation());
        assertEquals(destination, player.getLocation());
        assertNotEquals(80, player.getShipFuelCapacity());
        assertTrue((player.getShipFuelCapacity() >= 0) && (player.getShipFuelCapacity() < 80));
    }



}
