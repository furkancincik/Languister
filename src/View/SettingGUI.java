package View;

import Helper.DBConnector;
import Helper.Helper;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingGUI extends JFrame {
    private JPanel wrapper;
    private JButton btnChangeFont;
    private JButton btnDeleteUserData;
    private JButton btnGenerateReport;

    public SettingGUI(User user) {
        initComponents();
        setLayout();
        setSize(300, 200); // Buton ekledikten sonra pencere boyutunu artırdık
        setVisible(true);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnChangeFont.addActionListener(e -> changeFont());
        btnDeleteUserData.addActionListener(e -> deleteUserData(user));
        btnGenerateReport.addActionListener(e -> generateReport(user)); // Rapor butonuna işlevsellik ekledik
    }

    private void initComponents() {
        wrapper = new JPanel(new GridLayout(3, 1)); // GridLayout boyutunu 3'e artırdık

        btnChangeFont = new JButton("Font Değiştir");
        wrapper.add(btnChangeFont);

        btnDeleteUserData = new JButton("Verilerimi Sil");
        wrapper.add(btnDeleteUserData);

        btnGenerateReport = new JButton("Rapor Al"); // Yeni butonu ekledik
        wrapper.add(btnGenerateReport);

        add(wrapper);
    }

    private void setLayout() {
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void changeFont() {
        FontSelectionDialog fontDialog = new FontSelectionDialog(this);
        fontDialog.setVisible(true);

        Font selectedFont = fontDialog.getSelectedFont();
        if (selectedFont != null) {
            Helper.updateUIFont(selectedFont);
        }
    }

    private void deleteUserData(User user) {
        int userId = user.getId();
        int confirm = JOptionPane.showConfirmDialog(this, "Tüm kullanıcı verilerini silmek istediğinize emin misiniz?", "Uyarı", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String deleteRelationshipQuery = "DELETE FROM user_question_relationship WHERE user_id = ?";
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(deleteRelationshipQuery);
                pr.setInt(1, userId);
                pr.executeUpdate();

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

    private void generateReport(User user) {
        int userId = user.getId();

        // Son 10 soru ve doğru cevap sayısı için SQL sorgusu
        String last10QuestionsQuery = "SELECT correct_streak FROM user_question_relationship WHERE user_id = ? ORDER BY last_correct_date DESC LIMIT 10";

        // Doğru cevaplanan streak sayısı fazla olan ilk 5 soru için SQL sorgusu
        String top5StreakQuestionsQuery =
                "SELECT w.english_word, MAX(uqr.correct_streak) AS max_streak " +
                        "FROM user_question_relationship uqr " +
                        "JOIN words w ON uqr.word_id = w.word_id " +
                        "WHERE uqr.user_id = ? " +
                        "GROUP BY w.english_word " +
                        "ORDER BY max_streak DESC " +
                        "LIMIT 5";

        int correctCount = 0;
        StringBuilder report = new StringBuilder("Son 10 Sorudaki Doğru Cevap Sayısı: \n");

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(last10QuestionsQuery);
            pr.setInt(1, userId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                int correctStreak = rs.getInt("correct_streak");
                if (correctStreak > 0) {
                    correctCount++;
                }
            }
            report.append(correctCount).append("\n\n");

            report.append("Doğru Cevapladığınız Streak Sayısı Fazla Olan İlk 5 Kelime: \n");

            pr = DBConnector.getInstance().prepareStatement(top5StreakQuestionsQuery);
            pr.setInt(1, userId);
            rs = pr.executeQuery();

            while (rs.next()) {
                String wordEnglish = rs.getString("english_word");
                int streakCount = rs.getInt("max_streak");
                report.append("Kelime: ").append(wordEnglish).append(" - Streak: ").append(streakCount).append("\n");
            }

            JOptionPane.showMessageDialog(this, report.toString(), "Rapor", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Rapor oluşturulurken bir hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        new SettingGUI(new User());
    }
}
