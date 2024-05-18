package View;

import Helper.*;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UyeOlGUI extends JFrame {
    private JPanel panel1;
    private JPanel wrapper;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JTextField fld_user_password;
    private JButton btn_user_add;

    public UyeOlGUI() {
        Helper.setLayout();
        setContentPane(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(350, 300);
        setVisible(true);

        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_password) || Helper.isFieldEmpty(fld_user_username)) {
                Helper.showMsg("fill");
            } else {
                boolean added = User.add(fld_user_name.getText(), fld_user_username.getText(), fld_user_password.getText());
                if (added) {
                    Helper.showMsg("done");
                    dispose(); // Üye eklendikten sonra pencereyi kapat
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}
