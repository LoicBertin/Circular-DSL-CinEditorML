package highlight;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Highlight {

    private final List<String> keywords;
    private String previousWord;

    public Highlight(List<String> keywords) {
        this.keywords = keywords;
    }

    public void highlight(JTextPane tp) {
        String content = tp.getText();
        if (!content.endsWith(System.lineSeparator())) {
            content += System.lineSeparator();
        }
        content = content.replace(System.lineSeparator(), " " + System.lineSeparator() + " ");
        String[] words = content.split(" ");
        tp.setText("");

        SimpleAttributeSet defaultAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultAttr, Color.BLACK);

        SimpleAttributeSet keywordAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(keywordAttr, Color.BLUE);

        SimpleAttributeSet missingAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(missingAttr, Color.RED);
        StyleConstants.setUnderline(missingAttr, true);

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            String nextWord = "", previousWord = "";
            if (i < words.length - 1) {
                nextWord = words[+1];
            }
            if (i > 0) {
                previousWord = words[i-1];
            }
            AttributeSet aset;

            if (keywords.contains(word)) {
                aset = keywordAttr;
            } else {
                aset = defaultAttr;
            }
            System.out.println("Current word : " + word);
            System.out.println("Previous word : " + previousWord);
            if (word.contains(System.lineSeparator())) {
                if (keywords.contains(previousWord)) {
                    System.out.println("YES");
                    write(tp, "value", missingAttr);
                }
                write(tp, word, defaultAttr);
            } else {
                if (nextWord.equals(System.lineSeparator())) {
                    write(tp, word, aset);
                } else {
                    write(tp, word + " ", aset);
                }
            }
            System.out.println("------------------");
        }
    }

    void write(JTextPane textPane, String content, AttributeSet attributeSet) {
        StyledDocument doc = textPane.getStyledDocument();
        int len = doc.getLength();
        try {
            doc.insertString(len, content, attributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
