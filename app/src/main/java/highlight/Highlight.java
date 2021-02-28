package highlight;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Highlight {

    private final List<String> keywords;

    public Highlight(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean highlight(JTextPane tp) {
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

        boolean isValid = true;

        for (String line : lines) {
            List<String> list = new ArrayList<>();
            Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
            while (m.find())
                list.add(m.group(1));

            String[] words = list.toArray(new String[0]);
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
                        isValid = false;
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
                isValid = false;
            }
            write(tp, System.lineSeparator(), defaultAttr);
        }

        return isValid;
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
