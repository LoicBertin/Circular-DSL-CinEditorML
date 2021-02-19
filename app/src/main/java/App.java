import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class App extends JFrame implements DocumentListener {
    private JTextArea dslArea;
    private JButton visualizeButton;
    private JTextArea pythonArea;
    private JButton importButton;
    private JButton RUNButton;
    private JButton saveButton;
    private JPanel myPanel;
    private JLabel labelCode;
    private JLabel labelPython;

    private List<String> keyWords;
    private static enum Mode { INSERT, COMPLETION };
    private static final String COMMIT_ACTION = "commit";
    private Mode mode = Mode.INSERT;

    public App(){
        this.setContentPane(myPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1280,720);
        this.setVisible(true);

        setUpAutoCompletion();

        final JFrame frame = new JFrame("Open File Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File openedFile = chooser.getSelectedFile();
                    try {
                        Scanner myReader = new Scanner(openedFile);
                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                            dslArea.append(data);
                            dslArea.append("\n");
                        }
                        myReader.close();
                    } catch (FileNotFoundException e1) {
                        System.out.println("An error occurred.");
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

    public void setUpAutoCompletion(){
        dslArea.getDocument().addDocumentListener(this);

        keyWords = new ArrayList<String>(5);
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
            if(temp.toLowerCase().contains(prefix.toLowerCase())){
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
            dslArea.insert(completion, position);
            dslArea.setCaretPosition(position + completion.length());
            dslArea.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = dslArea.getSelectionEnd();
                dslArea.insert(" ", pos);
                dslArea.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                dslArea.replaceSelection("\n");
            }
        }
    }
}


