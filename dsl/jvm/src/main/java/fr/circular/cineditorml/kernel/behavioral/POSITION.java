package fr.circular.cineditorml.kernel.behavioral;

public enum POSITION {
    CENTER("center"),
    LEFT("left"),
    RIGHT("right"),
    BOTTOM("bottom"),
    TOP("top");

    public final String position;

    POSITION(String position) {
        this.position = position;
    }
}
