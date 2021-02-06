package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.structural.SIGNAL;
import fr.circular.cineditorml.kernel.generator.Visitor;

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
