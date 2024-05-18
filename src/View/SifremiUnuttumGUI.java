package View;

import Helper.*;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SifremiUnuttumGUI extends JFrame {
    private JPanel panel1;
    private JPanel wrapper;
    private JTextField fld_user_username;
    private JTextField fld_user_pass;
    private JTextField fld_user_pass2;
    private JButton button1;
    private JButton şifreYenileButton;

    public SifremiUnuttumGUI() {
        Helper.setLayout();
        setContentPane(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(350, 300);
        setVisible(true);


        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        şifreYenileButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_pass) || Helper.isFieldEmpty(fld_user_pass2)){
                Helper.showMsg("fill");
            } else {
                String username = fld_user_username.getText();
                String newPass = fld_user_pass.getText();
                String newPass2 = fld_user_pass2.getText();

                if (!newPass.equals(newPass2)) {
                    Helper.showMsg("Lütfen aynı şifreleri giriniz.");
                    return;
                }

                User user = User.getFetch(username);
                if (user != null) {
                    if (user.updatePassword(newPass)) {
                        Helper.showMsg("Şifre başarıyla güncellendi.");
                    } else {
                        Helper.showMsg("Şifre güncellenirken bir hata oluştu.");
                    }
                } else {
                    Helper.showMsg("Kullanıcı adı bulunamadı.");
                }
            }
        });




    }


}
