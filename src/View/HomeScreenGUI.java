package View;

import Helper.*;


import javax.swing.*;

public class HomeScreenGUI extends JFrame {
    private JPanel wrapper;
    private JPanel fld_top;
    private JPanel fld_left;
    private JPanel fld_mid;
    private JPanel fld_right;

    public HomeScreenGUI(){
        add(wrapper);
        setSize(800,600);
        setVisible(true);

        setLocation(Helper.screenLoc("x",getSize()),Helper.screenLoc("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);

    }

}
