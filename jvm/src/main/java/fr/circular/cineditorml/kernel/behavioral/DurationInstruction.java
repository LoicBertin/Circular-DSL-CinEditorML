package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitor;
import fr.circular.cineditorml.kernel.structural.Clip;

public class DurationInstruction extends Instruction{

    String duration;

    public DurationInstruction(){
        this.duration = "5";
    }

    public DurationInstruction(String d){
        this.duration = d;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
