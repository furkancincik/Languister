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

        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_pass)) {
                Helper.showMsg("fill");
            } else {
                String username = fld_user_username.getText();
                String password = fld_user_pass.getText(); // getToolTipText() yerine getText() kullanın
                if (User.isLogin(username, password)) {
                    User user = User.getFetch(username);
                    if (user != null) {
                        HomeScreenGUI homeScreen = new HomeScreenGUI(user);
                        dispose();
                    } else {
                        Helper.showMsg("User not found");
                    }
                } else {
                    Helper.showMsg("Invalid login credentials");
                }
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
