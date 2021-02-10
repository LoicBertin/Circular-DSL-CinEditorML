package fr.circular.cineditorml.kernel.behavioral;

public enum POSITION {
    CENTER("center"),
    EAST("East"),
    WEST("West"),
    SOUTH("South"),
    NORTH("North");

    public final String position;

    POSITION(String position) {
        this.position = position;
    }
}
