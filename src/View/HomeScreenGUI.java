package View;

import Helper.*;
import Model.User;

import javax.swing.*;
import java.awt.*;

public class HomeScreenGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_progress;
    private JButton btn_settings;
    private JLabel lbl_welcome;
    private JButton btn_quiz;
    private JButton btn_add_word;
    private JButton çıkışButton;
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

        lbl_welcome.setText("Hoşgeldin\t" + user.getName());
        Font existingFont = lbl_welcome.getFont();
        Font newFont = new Font(existingFont.getName(), Font.BOLD, existingFont.getSize() + 2);
        lbl_welcome.setFont(newFont);

        String welcomeMessage = Helper.welcomeUser(user);
        Helper.showMsg(welcomeMessage);

        btn_quiz.addActionListener(e -> {
            new QuizGUI(user);
        });

        btn_add_word.addActionListener(e -> {
            new AddWordGUI();
        });

        btn_settings.addActionListener(e -> {
            new SettingGUI(user);
        });

        çıkışButton.addActionListener(e -> {
            dispose();
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    public static void main(String[] args) {
        new HomeScreenGUI(new User());
    }
}
