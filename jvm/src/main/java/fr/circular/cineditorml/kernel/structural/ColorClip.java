package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.behavioral.COLOR;
import fr.circular.cineditorml.kernel.generator.Visitor;

public class ColorClip extends Clip {
    private COLOR color;

    public ColorClip() {
        this.color = COLOR.BLACK;
    }

    public ColorClip(COLOR backgroundColor) {
        this.color = backgroundColor;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
