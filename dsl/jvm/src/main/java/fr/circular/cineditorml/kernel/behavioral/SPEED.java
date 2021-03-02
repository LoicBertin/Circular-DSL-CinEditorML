package fr.circular.cineditorml.kernel.behavioral;

public enum SPEED {
    SLOW(100),
    NORMAL(200),
    FAST(300);

    public final int speed;

    SPEED(int speed) {
        this.speed = speed;
    }
}
