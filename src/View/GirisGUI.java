package View;

import Helper.*;
import Model.User;

import javax.swing.*;
import java.awt.*;

public class GirisGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_username;
    private JLabel Jlabeusrnme;
    private JLabel jlabelpass;
    private JPasswordField fld_user_pass;
    private JButton btn_login;
    private JButton btn_sifremiunuttum;
    private JButton btn_uyeol;

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
                if (User.isLogin(fld_user_username.getText(), new String(fld_user_pass.getPassword()))) {
                    User user = User.getFetch(fld_user_username.getText());
                    if (user != null) {
                        Font currentFont = UIManager.getFont("Label.font");
                        Helper.updateUIFont(currentFont);

                        HomeScreenGUI homeScreen = new HomeScreenGUI(user);
                        dispose();
                    } else {
                        Helper.showMsg("error");
                    }
                } else {
                    Helper.showMsg("Geçersiz giriş bilgileri.Tekrar Deneyiniz.");
                }
            }
        });

        btn_uyeol.addActionListener(e -> {
            UyeOlGUI yeniUye = new UyeOlGUI();
        });

        btn_sifremiunuttum.addActionListener(e -> {
            SifremiUnuttumGUI sifreYenile = new SifremiUnuttumGUI();
        });
    }

    public static void main(String[] args) {
        GirisGUI g = new GirisGUI();
    }
}
