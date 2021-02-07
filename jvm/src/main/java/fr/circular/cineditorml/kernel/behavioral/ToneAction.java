package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class ToneAction {
    private NOTE note;
    private DURATION duration;

    public ToneAction(){
    }

    public NOTE getNote() {
        return this.note;
    }

    public void setNote(NOTE note) {
        this.note = note;
    }

    public DURATION getDuration(){
        return this.duration;
    }

    public void setDuration(DURATION duration){
        this.duration = duration;
    }
}
