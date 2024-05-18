package View;

import Helper.Helper;

import javax.swing.*;

public class AddWordGUI extends JFrame {
    private JPanel panel1;
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button1;

    public AddWordGUI(){
        Helper.setLayout();
        setContentPane(wrapper);
        setSize(650, 550);
        setVisible(true);
        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        AddWordGUI n = new AddWordGUI();
    }

}
