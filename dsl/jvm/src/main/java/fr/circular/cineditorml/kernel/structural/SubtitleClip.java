package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.behavioral.Subtitle;
import fr.circular.cineditorml.kernel.generator.Visitor;

import java.util.ArrayList;

public class SubtitleClip extends Clip {
    private ArrayList<Subtitle> subtitles = new ArrayList<Subtitle>();
    private Clip clip;

    public ArrayList<Subtitle> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(ArrayList<Subtitle> subtitles) {
        this.subtitles = subtitles;
    }

    public void addSubtitle(Subtitle subtitle) {
        this.subtitles.add(subtitle);
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
