package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class ColorClip extends Clip {
    private String color;

    public ColorClip() {
        this.color = "(0, 0, 0)";
    }

    public ColorClip(String backgroundColor) {
        this.color = backgroundColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
