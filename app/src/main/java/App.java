import highlight.Highlight;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class App extends JFrame implements DocumentListener {
    private JTextPane dslArea;
    private JButton visualizeButton;
    private JTextArea pythonArea;
    private JButton importButton;
    private JButton RUNButton;
    private JButton saveButton;
    private JPanel myPanel;
    private JLabel labelCode;
    private JLabel labelPython;
    private JButton saveDSLArea;
    private JTextArea pythonOutputArea;

    private boolean isContentValidated;

    private List<String> keyWords;

    private static enum Mode { INSERT, COMPLETION };
    private static final String COMMIT_ACTION = "commit";
    private static final String REROLL_ACTION = "reroll";
    private Mode mode = Mode.INSERT;

    public App(){
        saveDSLArea.setForeground(Color.WHITE);
        importButton.setForeground(Color.WHITE);
        RUNButton.setForeground(Color.WHITE);
        saveButton.setForeground(Color.WHITE);
        visualizeButton.setForeground(Color.WHITE);

        DefaultCaret caret = (DefaultCaret)pythonOutputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        this.setContentPane(myPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1280,720);
        this.setVisible(true);

        RUNButton.addActionListener(e -> {
            Highlight highlight = new Highlight(keyWords);
            isContentValidated = highlight.highlight(dslArea);
            PythonWorker worker = new PythonWorker();
            worker.execute();
        });

        visualizeButton.addActionListener(e -> {
            pythonOutputArea.setText("");
            Highlight highlight = new Highlight(keyWords);
            isContentValidated = highlight.highlight(dslArea);
            if (isContentValidated) {
                // Write content in the UI in a file
                File fold = new File("./src/main/resources/dslwritten.txt");
                fold.delete();
                File fnew = new File("./src/main/resources/dslwritten.txt");

                String source = dslArea.getText();
                try {
                    FileWriter f2 = new FileWriter(fnew, false);
                    f2.write(source);
                    f2.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                pythonArea.setText("");
                // Execute script
                String command = "java -jar ./src/main/resources/lib/dsl-groovy-1.0-jar-with-dependencies.jar ./src/main/resources/dslwritten.txt";

                try {
                    Process process = Runtime.getRuntime().exec(command);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        pythonArea.append(line);
                        pythonArea.append(System.lineSeparator());
                    }
                    reader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Le contenu n'est pas correct !",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        setUpAutoCompletion();

        final JFrame frame = new JFrame("Open File Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        importButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File openedFile = chooser.getSelectedFile();
                try {
                    Scanner myReader = new Scanner(openedFile);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        appendToPane(dslArea, data, Color.BLACK);
                        appendToPane(dslArea, "\n", Color.BLACK);
                    }
                    myReader.close();
                } catch (FileNotFoundException e1) {
                    System.out.println("An error occurred.");
                    e1.printStackTrace();
                }
            }
        });

        saveButton.addActionListener(e -> {
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);

            File fileName = new File(SaveAs.getSelectedFile() + ".py");
            try {
                if (fileName == null) {
                    return;
                }
                BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
                outFile.write(pythonArea.getText()); //put in textfile

                outFile.close();
            } catch (IOException ex) {

            }
        });

        saveDSLArea.addActionListener(e -> {
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);

            File fileName = new File(SaveAs.getSelectedFile() + ".groovy");
            try {
                if (fileName == null) {
                    return;
                }
                BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
                outFile.write(dslArea.getText()); //put in textfile

                outFile.close();
            } catch (IOException ex) {

            }
        });

    }

    private void appendToPane(JTextPane tp, String msg, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public void setUpAutoCompletion(){
        dslArea.getDocument().addDocumentListener(this);

        keyWords = new ArrayList<>();
        importKeyWords();

        InputMap im = dslArea.getInputMap();
        ActionMap am = dslArea.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());
    }

    public void importKeyWords(){
        try {
            File myObj = new File("./src/main/resources/keywords.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splited = data.split("\\s+");
                keyWords.addAll(Arrays.asList(splited));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent ev) {
        if (ev.getLength() != 1) {
            return;
        }

        int pos = ev.getOffset();
        String content = null;
        try {
            content = dslArea.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Find where the word starts
        int w;
        for (w = pos; w >= 0; w--) {
            if (! Character.isLetter(content.charAt(w))) {
                break;
            }
        }
        if (pos - w < 2) {
            // Too few chars
            return;
        }

        String prefix = content.substring(w + 1).toLowerCase();
        boolean founded = false;
        for(String temp : keyWords){
            if(temp.toLowerCase().startsWith(prefix.toLowerCase()) && !founded){
                System.out.println(temp);
                founded = true;
                // A completion is found
                String completion = temp.substring(pos - w);
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                SwingUtilities.invokeLater(
                        new CompletionTask(completion, pos + 1));
            }
        }
        if(!founded){
            mode = Mode.INSERT;
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    public static void main(String [] args){
        new App().setVisible(true);
    }

    private class CompletionTask implements Runnable {
        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            appendToPane(dslArea, completion, Color.BLACK);
            String[] temp = {completion};
            //dslArea.insert(completion, position);
            dslArea.setCaretPosition(position + completion.length());
            dslArea.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = dslArea.getSelectionEnd();
                appendToPane(dslArea, " ", Color.BLACK);
                //dslArea.insert(" ", pos);
                dslArea.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                dslArea.replaceSelection("\n");
            }
        }
    }

    private class PythonWorker extends SwingWorker<Void, String> {
        @Override
        protected Void doInBackground() throws Exception {
            if (isContentValidated) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Cliquez sur OUI pour créer la vidéo ! Veuillez attendre le dialogue de confirmation.",
                        "En cours", JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirmed != 0) {
                    return null;
                }

                // Write content in the UI in a file
                File fold = new File("./dslwritten.txt");
                fold.delete();
                File fnew = new File("./dslwritten.txt");

                String source = dslArea.getText();
                try {
                    FileWriter f2 = new FileWriter(fnew, false);
                    f2.write(source);
                    f2.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                pythonArea.setText("");
                // Execute script
                String command = "java -jar ./src/main/resources/lib/dsl-groovy-1.0-jar-with-dependencies.jar ./dslwritten.txt";

                try {
                    Process process = Runtime.getRuntime().exec(command);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        pythonArea.append(line);
                        pythonArea.append(System.lineSeparator());
                    }
                    reader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                fold = new File("./dslvideo.py");
                fold.delete();
                fnew = new File("./dslvideo.py");

                String python = pythonArea.getText();
                try {
                    FileWriter f2 = new FileWriter(fnew, false);
                    f2.write(python);
                    f2.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                System.out.println("python ./dslvideo.py");
                String commandPython = "python ./dslvideo.py";

                try {
                    Process process = Runtime.getRuntime().exec(commandPython);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        pythonOutputArea.append(line);
                        pythonOutputArea.append(System.lineSeparator());
                        System.out.println(line);
                        System.out.println(System.lineSeparator());
                    }
                    reader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                JOptionPane.showMessageDialog(myPanel, "La vidéo a été créée avec succès !",
                        "Création terminée", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(myPanel, "Le contenu n'est pas correct !",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }

    }
}


