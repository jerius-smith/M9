package edu.gatech.cs2340.spacetraders.model;


/**
 * The enum Tech level.
 */
public enum TechLevel {
    /**
     * Pre agriculture tech level.
     */
    PRE_AGRICULTURE("Pre-Agriculture"),
    /**
     * Agriculture tech level.
     */
    AGRICULTURE("Agriculture"),
    /**
     * Medieval tech level.
     */
    MEDIEVAL("Medieval"),
    /**
     * Renaissance tech level.
     */
    RENAISSANCE("Renaissance"),
    /**
     * The Early industrial.
     */
    EARLY_INDUSTRIAL("Early Industrial"),
    /**
     * Industrial tech level.
     */
    INDUSTRIAL("Industrial"),
    /**
     * Post industrial tech level.
     */
    POST_INDUSTRIAL("Post-Industrial"),
    /**
     * Hi tech tech level.
     */
    HI_TECH("Hi-Tech");

//    public String getName() {
//        return name;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }


    private final String name;

    TechLevel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Gets the number of elements in the TechLevel enum
     * @return the number of elements
     */
    public static int numElements() {
        return values().length;
    }
}
