package View;

import Helper.*;

import javax.swing.*;

public class SifremiUnuttumGUI extends JFrame{
    private JPanel panel1;
    private JPanel wrapper;
    private JTextField fld_user_username;
    private JTextField fld_user_pass;
    private JTextField fld_user_pass2;
    private JButton button1;
    private JButton şifreYenileButton;

    public SifremiUnuttumGUI(){
        Helper.setLayout();
        setContentPane(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(350, 300);
        setVisible(true);


        setLocation(Helper.screenLoc("x",getSize()),Helper.screenLoc("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }



}
