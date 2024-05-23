package View;

import Helper.Helper;
import Model.Words;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AddWordGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_turkish_word;
    private JTextField fld_english_word;
    private JTextField fld_sentences;
    private JButton btn_add;
    private JLabel lbl_turkish_word;
    private JLabel lbl_english_word;
    private JLabel lbl_sentences;
    private JButton btn_gorsel;
    private JLabel lbl_gorsel;

    public AddWordGUI() {
        Helper.setLayout();
        setContentPane(wrapper);
        setSize(450, 300);
        setVisible(true);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btn_add.addActionListener(this::addWord);

        btn_gorsel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String imagePath = selectedFile.getAbsolutePath();

                // Dosya var mı ve JPEG mi kontrol et
                try {
                    String contentType = Files.probeContentType(selectedFile.toPath());
                    if (contentType == null || !contentType.equals("image/jpeg")) {
                        JOptionPane.showMessageDialog(this, "Lütfen bir JPEG resim dosyası seçin", "Hata", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Dosya okunurken bir hata oluştu", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Görseli lbl_gorsel JLabel'ine yazabilirsiniz
                lbl_gorsel.setText(imagePath);
            }
        });

    }

    private void addWord(ActionEvent e) {
        String turkishWord = fld_turkish_word.getText();
        String englishWord = fld_english_word.getText();
        String sentences = fld_sentences.getText();
        String imagePath = lbl_gorsel.getText(); // Seçilen görselin dosya yolu

        if (turkishWord.isEmpty() || englishWord.isEmpty() || sentences.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tüm alanların doldurulması gerekmektedir", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Görselin byte dizisine dönüştürülmesi
        byte[] imageData = null;
        try {
            File imageFile = new File(imagePath);
            imageData = Files.readAllBytes(imageFile.toPath());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Görsel okunurken bir hata oluştu", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kelime ve görselin veritabanına eklenmesi
        Words.addWord(turkishWord, englishWord, sentences, imageData);

        JOptionPane.showMessageDialog(this, "Kelime başarıyla eklendi", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

        fld_turkish_word.setText("");
        fld_english_word.setText("");
        fld_sentences.setText("");
        lbl_gorsel.setText(""); // Görsel etiketini sıfırlama
    }



    public static void main(String[] args) {
        new AddWordGUI();
    }
}
