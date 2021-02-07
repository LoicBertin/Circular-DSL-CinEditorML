package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class TextPositionInstruction extends Instruction {
    private POSITION position;

    public TextPositionInstruction() {
        this.position = POSITION.center;
    }

    public TextPositionInstruction(POSITION position) {
        this.position = position;
    }

    public POSITION getPosition() {
        return position;
    }

    public void setPosition(POSITION position) {
        this.position = position;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
