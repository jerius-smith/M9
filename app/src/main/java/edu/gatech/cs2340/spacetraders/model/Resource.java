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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Resource() {
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
     * @return the number of elements in the Resource enum
     */
    public static int numElements() {
        return values().length;
    }
}
