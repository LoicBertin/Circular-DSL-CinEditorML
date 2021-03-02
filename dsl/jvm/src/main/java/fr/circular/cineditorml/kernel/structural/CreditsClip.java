package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.behavioral.SPEED;
import fr.circular.cineditorml.kernel.generator.Visitor;

public class CreditsClip extends Clip{

    private String text;
    private SPEED speed;

    public CreditsClip(){
        this.speed = SPEED.NORMAL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SPEED getSpeed() {
        return speed;
    }

    public void setSpeed(SPEED speed) {
        this.speed = speed;
    }

    public CreditsClip(String text, SPEED speed) {
        this.text = text;
        this.speed = speed;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
