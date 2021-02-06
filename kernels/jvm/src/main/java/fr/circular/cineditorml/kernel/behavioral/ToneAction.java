package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class ToneAction extends Action {
    private NOTE note;
    private DURATION duration;

    public ToneAction(){
        this.setNumberOfIteration(1);
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
