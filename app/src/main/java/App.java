import javax.swing.*;

public class App {
    private JTextArea textArea1;
    private JButton visualizeButton;
    private JTextArea textArea2;
    private JButton importButton;
    private JButton RUNButton;
    private JButton saveButton;
    private JPanel myPanel;

    public static void main(String [] args){
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().myPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1280,720);
        frame.setVisible(true);
    }
}
