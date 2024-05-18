package View;

import Helper.*;
import Model.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeScreenGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_progress;
    private JButton btn_settings;
    private JSplitPane fld_views;
    private final User user;

    public HomeScreenGUI(User user) {
        this.user = user;

        Helper.setLayout();
        add(wrapper); 
        setResizable(false);
        setSize(500, 400);
        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
    }


}
