package edu.gatech.cs2340.spacetraders.model;


/**
 * The enum Resource.
 */
public enum Resource {
    /**
     * The Nospecialresources.
     */
    NOSPECIALRESOURCES("No special resources"),
    /**
     * The Mineral rich.
     */
    MINERAL_RICH("Mineral rich"),
    /**
     * The Mineral poor.
     */
    MINERAL_POOR("Mineral poor"),
    /**
     * Desert resource.
     */
    DESERT("Desert"),
    /**
     * The Lots of water.
     */
    LOTS_OF_WATER("Lots of water"),
    /**
     * The Rich soil.
     */
    RICH_SOIL("Rich soil"),
    /**
     * The Poor soil.
     */
    POOR_SOIL("Poor soil"),
    /**
     * The Rich fauna.
     */
    RICH_FAUNA("Rich fauna"),
    /**
     * Lifeless resource.
     */
    LIFELESS("Lifeless"),
    /**
     * The Weird mushrooms.
     */
    WEIRD_MUSHROOMS("Weird mushrooms"),
    /**
     * The Lots of herbs.
     */
    LOTS_OF_HERBS("Lots of herbs"),
    /**
     * Artistic resource.
     */
    ARTISTIC("Artistic"),
    /**
     * Warlike resource.
     */
    WARLIKE("Warlike");

// --Commented out by Inspection START (4/8/2019 2:10 PM):
//    /**
//     * Gets name.
//     *
//     * @return the name
// --Commented out by Inspection START (4/8/2019 2:10 PM):
////     */
////    public String getName() {
////        return name;
////    }
//// --Commented out by Inspection STOP (4/8/2019 2:10 PM)
//
//    /**
// --Commented out by Inspection STOP (4/8/2019 2:10 PM)
//     * Sets name.
//     *
//     * @param name the name
//     */
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    Resource(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Gets the number of elements
     *
     * @return the number of elements in the Resource enum
     */
    public static int numElements() {
        return values().length;
    }
}
