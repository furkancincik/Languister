package View;

import Helper.*;

import javax.swing.*;

public class SifremiUnuttumGUI extends JFrame{
    private JPanel panel1;
    private JTextArea fld_user_username;
    private JTextField fld_user_password;
    private JTextField fld_user_password2;
    private JPanel wrapper;

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
