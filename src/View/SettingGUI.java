package View;

import Helper.DBConnector;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SettingGUI extends JFrame {
    private JPanel wrapper;
    private JButton btnChangeFont;
    private JButton btnDeleteUserData;

    public SettingGUI(User user) {
        initComponents();
        setLayout();
        setSize(300, 150);
        setVisible(true);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnChangeFont.addActionListener(e -> changeFont());
        btnDeleteUserData.addActionListener(e -> deleteUserData(user));
    }

    private void initComponents() {
        wrapper = new JPanel(new GridLayout(2, 1));

        btnChangeFont = new JButton("Font Değiştir");
        wrapper.add(btnChangeFont);

        btnDeleteUserData = new JButton("Verilerimi Sil");
        wrapper.add(btnDeleteUserData);

        add(wrapper);
    }

    private void setLayout() {
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void changeFont() {
        // Font değiştirme işlevselliği burada eklenecek
        // Örnek olarak, tüm bileşenlerin fontunu değiştirebilirsiniz
        FontSelectionDialog fontDialog = new FontSelectionDialog(this);
        fontDialog.setVisible(true);

        // Kullanıcı seçimi onayladıysa, uygulama fontunu güncelle
        Font selectedFont = fontDialog.getSelectedFont();
        if (selectedFont != null) {
            UIManager.put("Button.font", selectedFont);
            UIManager.put("Label.font", selectedFont);
            UIManager.put("TextField.font", selectedFont);
            UIManager.put("TextArea.font", selectedFont);
            // Diğer bileşenler için aynı şekilde devam edebilirsiniz...

            // UI'nin güncellenmesi
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    private void deleteUserData(User user) {
        int userId = user.getId();
        int confirm = JOptionPane.showConfirmDialog(this, "Tüm kullanıcı verilerini silmek istediğinize emin misiniz?", "Uyarı", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Kullanıcıya ait tüm verileri "user_question_relationship" tablosundan sil
                String deleteRelationshipQuery = "DELETE FROM user_question_relationship WHERE user_id = ?";
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(deleteRelationshipQuery);
                pr.setInt(1, userId);
                pr.executeUpdate();

                // Kullanıcıyı "users" tablosundan sil
                String deleteUserQuery = "DELETE FROM users WHERE user_id = ?";
                pr = DBConnector.getInstance().prepareStatement(deleteUserQuery);
                pr.setInt(1, userId);
                pr.executeUpdate();

                JOptionPane.showMessageDialog(this, "Kullanıcı verileri başarıyla silindi.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Kullanıcı verilerini silerken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static void main(String[] args) {
        new SettingGUI(new User());
    }
}
