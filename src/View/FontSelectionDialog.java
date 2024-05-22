package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontSelectionDialog extends JDialog {
    private JComboBox<String> fontList;
    private JButton btnOK;
    private JButton btnCancel;

    private Font selectedFont;

    public FontSelectionDialog(JFrame parent) {
        super(parent, "Font Seçimi", true);

        initComponents();
        setLayout();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Font listesi oluştur
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList = new JComboBox<>(fontNames);

        // Onay ve İptal butonları oluştur
        btnOK = new JButton("Tamam");
        btnCancel = new JButton("İptal");

        // Butonların dinleyicilerini ekle
        btnOK.addActionListener(e -> okButtonClicked());
        btnCancel.addActionListener(e -> cancelButtonClicked());
    }

    private void setLayout() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        panel.add(fontList);
        panel.add(buttonPanel);

        add(panel);
    }

    private void okButtonClicked() {
        // Seçilen fontu al
        String selectedFontName = (String) fontList.getSelectedItem();
        selectedFont = new Font(selectedFontName, Font.PLAIN, 14);
        // Dialogu kapat
        dispose();
    }

    private void cancelButtonClicked() {
        // Dialogu kapat
        dispose();
    }

    public Font getSelectedFont() {
        return selectedFont;
    }
}
