package fr.circular.cineditorml.kernel.behavioral;

public enum COLOR {
    RED("(255, 0, 0)"),
    GREEN("(0, 255, 0)"),
    BLUE("(0, 0, 255)"),
    BLACK("(0, 0, 0)"),
    WHITE("(255, 255, 255"),
    ;
    public final String color;

    COLOR(String color) {
        this.color = color;
    }
}
