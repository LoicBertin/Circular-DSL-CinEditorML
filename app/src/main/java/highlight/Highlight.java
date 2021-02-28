package highlight;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Highlight {

    private final List<String> keywords;
    private String previousWord;

    public Highlight(List<String> keywords) {
        this.keywords = keywords;
    }

    public void highlight(JTextPane tp) {
        String content = tp.getText();
        List<String> lines = new ArrayList<>(Arrays.asList(content.split(System.lineSeparator())));
        tp.setText("");

        SimpleAttributeSet defaultAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultAttr, Color.BLACK);

        SimpleAttributeSet keywordAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(keywordAttr, Color.BLUE);

        SimpleAttributeSet missingAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(missingAttr, Color.RED);
        StyleConstants.setUnderline(missingAttr, true);

        for (String line : lines) {
            String[] words = line.split("\\s+");
            int countWrong = 0;

            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                String nextWord = "", previousWord = "";
                if (i < words.length - 1) {
                    nextWord = words[+1];
                }
                if (i > 0) {
                    previousWord = words[i - 1];
                }

                AttributeSet aset;

                if (keywords.contains(word)) {
                    aset = keywordAttr;
                } else {
                    if (keywords.contains(previousWord)) {
                        aset = defaultAttr;
                    } else {
                        countWrong++;
                        aset = missingAttr;
                    }
                }
                if (!nextWord.equals("")) {
                    write(tp, word + " ", aset);
                } else {
                    write(tp, word, aset);
                }
            }

            if ((words.length - countWrong) % 2 != 0) {
                write(tp, " missingValue", missingAttr);
            }
            write(tp, System.lineSeparator(), defaultAttr);
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
