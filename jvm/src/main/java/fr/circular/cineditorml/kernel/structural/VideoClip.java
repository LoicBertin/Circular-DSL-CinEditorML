package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class VideoClip extends Clip{

    private String file;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
