package fr.circular.cineditorml.kernel.behavioral;

public class Subtitle {
    private int from;
    private int to;
    private COLOR color;
    private String txt;
    private POSITION position;

    public COLOR getColor() {
        return color;
    }

    public String getTxt() {
        return txt;
    }

    public POSITION getPosition() {
        return position;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setPosition(POSITION position) {
        this.position = position;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
