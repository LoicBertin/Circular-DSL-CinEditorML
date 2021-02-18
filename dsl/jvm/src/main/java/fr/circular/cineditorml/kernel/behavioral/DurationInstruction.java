package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitor;
import fr.circular.cineditorml.kernel.structural.Clip;

public class DurationInstruction extends Instruction{

    private int duration;

    public DurationInstruction(){
        this.duration = 5;
    }

    public DurationInstruction(int d){
        this.duration = d;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
