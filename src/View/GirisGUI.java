package View;

import Helper.*;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GirisGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_username;
    private JLabel Jlabeusrnme;
    private JLabel jlabelpass;
    private JPasswordField fld_user_pass;
    private JButton btn_login;
    private JButton şifremiUnuttumButton;
    private JButton üyeOlButton;


    public GirisGUI() {

        Helper.setLayout();
        setContentPane(wrapper);
        setSize(350, 300);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        setLocation(Helper.screenLoc("x",getSize()),Helper.screenLoc("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        btn_login.addActionListener(e -> {
            if (fld_user_username.getText().length() == 0 || fld_user_pass.getText().length() == 0){
                Helper.showMsg("fill");
            }else {
                HomeScreenGUI homeScreen = new HomeScreenGUI(new User("password", "username", "name", 1));
                dispose();
            }
        });


        üyeOlButton.addActionListener(e -> {
            UyeOlGUI yeniUye = new UyeOlGUI();
        });
    }


    public static void main(String[] args) {

        GirisGUI g = new GirisGUI();

    }


}
