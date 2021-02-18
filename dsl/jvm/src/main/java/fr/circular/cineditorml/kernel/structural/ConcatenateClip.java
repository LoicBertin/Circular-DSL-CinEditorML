package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.behavioral.Instruction;
import fr.circular.cineditorml.kernel.generator.Visitor;

import java.util.ArrayList;

public class ConcatenateClip extends Clip {
    private ArrayList<Clip> clips = new ArrayList<Clip>();

    public ArrayList<Clip> getClips() {
        return clips;
    }

    public void setClips(ArrayList<Clip> clips) {
        this.clips = clips;
    }

    public void addClip(Clip clip){
        this.clips.add(clip);
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
