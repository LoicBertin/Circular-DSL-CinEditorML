package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitable;
import fr.circular.cineditorml.kernel.structural.Clip;

public abstract class Instruction implements Visitable {

    private Clip clip;

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }
}
