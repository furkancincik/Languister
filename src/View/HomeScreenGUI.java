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
        setSize(500, 400);
        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_views.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        createUIComponents();
    }

    private void createUIComponents() {
        JButton leftButton = new JButton("Quiz'e Başla");
        leftButton.setFont(new Font(leftButton.getFont().getName(), Font.PLAIN, 16));
        leftButton.setFocusPainted(false);
        leftButton.setBorderPainted(false);

        JButton rightButton = new JButton("Kelime Ekle");
        rightButton.setFont(new Font(rightButton.getFont().getName(), Font.PLAIN, 16));
        rightButton.setFocusPainted(false);
        rightButton.setBorderPainted(false);

        fld_views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftButton, rightButton);
        add(fld_views, BorderLayout.CENTER);
    }






    /*public static void main(String[] args) {
        HomeScreenGUI start = new HomeScreenGUI(new User("password","username","name",1));
    }

     */

}
