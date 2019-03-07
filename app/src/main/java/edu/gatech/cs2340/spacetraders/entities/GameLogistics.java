package edu.gatech.cs2340.spacetraders.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Game logistics.
 */
public final class GameLogistics {

    /**
     * The constant MAX_WIDTH.
     */
    public static final int MAX_WIDTH = 500;
    /**
     * The constant MAX_HEIGHT.
     */
    public static final int MAX_HEIGHT = 500;

    public static final int MAX_DISTANCE =
            (int) Math.sqrt(MAX_WIDTH * MAX_WIDTH + MAX_HEIGHT * MAX_HEIGHT);
    /**
     * The constant MAX_MERCENARIES.
     */
    public static final int MAX_MERCENARIES = 1;
    /**
     * The constant MAX_SOLAR_SYSTEMS.
     */
    public static final int MAX_SOLAR_SYSTEMS = 3;
    /**
     * The constant SOLAR_SYSTEM_NAMES.
     */
    public static final String[] SOLAR_SYSTEM_NAMES =
            new String[]{"Sombrero", "Cygnus", "Andromeda"};


    // index 0 : weapons, 1 : gadget, 2 : cargo, 3 : fuel capacity
    public static final HashMap<String, Integer[]> MAX_CAPACITIES =
            new HashMap<String, Integer[]>() {
                {
                    put("Gnat", new Integer[]{1, 1, 15, 80});
                }
            };


    /**
     * The constant PLANET_NAMES.
     */
    public static final String[] PLANET_NAMES = new String[]{"Acamar", "Adahn",
            // The alternate personality for The Nameless One in "Planescape: Torment"
            "Aldea", "Andevian", "Antedi", "Balosnee", "Baratas", "Brax",
            // One of the heroes in Master of Magic
            "Bretel",        // This is a Dutch device for keeping your pants up.
            "Calondia", "Campor", "Capelle",
            // The city I lived in while programming this game
            "Carzon", "Castor",        // A Greek demi-god
            "Cestus", "Cheron", "Daled", "Damast", "Davlos", "Deneb", "Deneva", "Devidia",
            "Draylon", "Drema", "Endor", "Esmee",
            // One of the witches in Pratchett's Discworld
            "Exo", "Ferris",        // Iron
            "Festen",        // A great Scandinavian movie
            "Fourmi",        // An ant, in French
            "Frolix",        // A solar system in one of Philip K. Dick's novels
            "Gemulon", "Guinifer",        // One way of writing the name of king Arthur's wife
            "Hades",        // The underworld
            "Hamlet",        // From Shakespeare
            "Helena",        // Of Troy
            "Hulst",        // A Dutch plant
            "Iodine",        // An element
            "Iralius", "Janus",        // A seldom encountered Dutch boy's name
            "Japori", "Jarada", "Jason",        // A Greek hero
            "Kaylon", "Khefka", "Kira",            // My dog's name
            "Klaatu",        // From a classic SF movie
            "Klaestron", "Korma",        // An Indian sauce
            "Kravat",        // Interesting spelling of the French word for "tie"
            "Krios", "Laertes",        // A king in a Greek tragedy
            "Largo", "Lave",            // The starting system in Elite
            "Ligon", "Lowry",        // The name of the "hero" in Terry Gilliam's "Brazil"
            "Magrat",        // The second of the witches in Pratchett's Discworld
            "Malcoria", "Melina", "Mentar",        // The Psilon home system in Master of Orion
            "Merik", "Mintaka", "Montor",        // A city in Ultima III and Ultima VII part 2
            "Mordan", "Myrthe",        // The name of my daughter
            "Nelvana", "Nix",
            // An interesting spelling of a word meaning "nothing" in Dutch
            "Nyle",            // An interesting spelling of the great river
            "Odet", "Og",            // The last of the witches in Pratchett's Discworld
            "Omega",        // The end of it all
            "Omphalos",        // Greek for navel
            "Orias", "Othello",        // From Shakespeare
            "Parade",        // This word means the same in Dutch and in English
            "Penthara", "Picard",        // The enigmatic captain from ST:TNG
            "Pollux",        // Brother of Castor
            "Quator", "Rakhar", "Ran",            // A film by Akira Kurosawa
            "Regulas", "Relva", "Rhymus", "Rochani", "Rubicum",
            // The river Ceasar crossed to get into Rome
            "Rutia", "Sarpeidon", "Sefalla", "Seltrice", "Sigma", "Sol",
            // That's our own solar system
            "Somari", "Stakoron", "Styris", "Talani", "Tamus", "Tantalos",
            // A king from a Greek tragedy
            "Tanuga", "Tarchannen", "Terosa", "Thera",
            // A seldom encountered Dutch girl's name
            "Titan",        // The largest moon of Jupiter
            "Torin",        // A hero from Master of Magic
            "Triacus", "Turkana", "Tyrus", "Umberlee",
            // A god from AD&D, which has a prominent role in Baldur's Gate
            "Utopia",        // The ultimate goal
            "Vadera", "Vagra", "Vandor", "Ventax", "Xenon", "Xerxes",        // A Greek hero
            "Yew",            // A city which is in almost all of the Ultima games
            "Yojimbo",        // A film by Akira Kurosawa
            "Zalkon", "Zuul"            // From the first Ghostbusters movie
    };

}
