package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class TextClip extends Clip{

    private String text;
    private String fontsize;
    private String color;

    public TextClip(){
        this.fontsize = "70";
        this.color = "white";
    }

    public TextClip(String t, String f, String c){
        this.text = t;
        this.fontsize = f;
        this.color = c;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
