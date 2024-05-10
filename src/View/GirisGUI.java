package View;

import Helper.*;

import javax.swing.*;
import java.awt.*;

public class GirisGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField txt_username;
    private JLabel fld_user_username;
    private JLabel fld_user_password;
    private JPasswordField txt_pass;
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
            if (txt_username.getText().length() == 0 || txt_pass.getText().length() == 0){
                JOptionPane.showMessageDialog(null,"Tüm alanları doldurun","Hata",JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }


    public static void main(String[] args) {

        GirisGUI g = new GirisGUI();

    }


}
