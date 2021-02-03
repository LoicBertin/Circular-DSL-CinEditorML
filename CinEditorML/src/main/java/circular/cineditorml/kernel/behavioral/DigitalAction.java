package main.java.circular.cineditorml.kernel.behavioral;


import main.java.circular.cineditorml.kernel.generator.Visitor;
import main.java.circular.cineditorml.kernel.structural.SIGNAL;

public class DigitalAction extends Action {
    private SIGNAL signal;

    public DigitalAction(){
        this.setNumberOfIteration(1);
    }

    public SIGNAL getSignal() {
        return signal;
    }

    public void setSignal(SIGNAL signal) {
        this.signal = signal;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
