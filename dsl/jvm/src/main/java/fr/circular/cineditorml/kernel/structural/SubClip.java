package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class SubClip extends Clip {
    private int from, to;
    private String clipName;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getClipName() {
        return clipName;
    }

    public void setClipName(String clipName) {
        this.clipName = clipName;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
