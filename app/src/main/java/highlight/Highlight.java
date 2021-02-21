package highlight;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Highlight {

    private List<String> keywords;

    public Highlight(List<String> keywords) {
        this.keywords = keywords;
    }

    public void highlight(JTextPane tp) {
        String content = tp.getText();
        String[] words = content.split(" ");
        System.out.println(Arrays.toString(words));
        tp.setText("");
        tp.updateUI();
        System.out.println(tp.getText());

        for (String word : words) {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset;

            if (keywords.contains(word)) {
                aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
            } else {
                aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
            }

            int len = tp.getDocument().getLength();
            tp.setCaretPosition(len);
            tp.setCharacterAttributes(aset, false);
            tp.replaceSelection(content);
        }
    }

    public void importKeywords(){
        try {
            File myObj = new File("./src/main/resources/keywords.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splited = data.split("\\s+");
                keywords.addAll(Arrays.asList(splited));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
