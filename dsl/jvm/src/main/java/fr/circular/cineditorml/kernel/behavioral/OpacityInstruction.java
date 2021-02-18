package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class OpacityInstruction extends Instruction {
    private float opacity;

    public OpacityInstruction(float opacity) {
        this.opacity = opacity;
    }

    public OpacityInstruction() {
        this.opacity = 5;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
