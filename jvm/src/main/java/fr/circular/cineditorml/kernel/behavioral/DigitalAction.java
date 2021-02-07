package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.structural.SIGNAL;
import fr.circular.cineditorml.kernel.generator.Visitor;

public class DigitalAction {
    private SIGNAL signal;

    public DigitalAction(){
    }

    public SIGNAL getSignal() {
        return signal;
    }

    public void setSignal(SIGNAL signal) {
        this.signal = signal;
    }
}
