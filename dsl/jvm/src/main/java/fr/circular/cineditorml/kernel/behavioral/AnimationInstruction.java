package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class AnimationInstruction extends Instruction {
    private ANIMATION animation;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimationInstruction() {
        this.animation = ANIMATION.VORTEX;
    }

    public AnimationInstruction(ANIMATION animation) {
        this.animation = animation;
    }

    public ANIMATION getAnimation() {
        return animation;
    }

    public void setAnimation(ANIMATION animation) {
        this.animation = animation;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
