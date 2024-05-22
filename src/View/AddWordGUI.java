package View;

import Helper.Helper;
import Model.Words;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddWordGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_turkish_word;
    private JTextField fld_english_word;
    private JTextField fld_sentences;
    private JButton btn_add;
    private JLabel lbl_turkish_word;
    private JLabel lbl_english_word;
    private JLabel lbl_sentences;

    public AddWordGUI() {
        Helper.setLayout();
        setContentPane(wrapper);
        setSize(450, 300);
        setVisible(true);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btn_add.addActionListener(this::addWord);
    }

    private void addWord(ActionEvent e) {
        String turkishWord = fld_turkish_word.getText();
        String englishWord = fld_english_word.getText();
        String sentences = fld_sentences.getText();

        if (turkishWord.isEmpty() || englishWord.isEmpty() || sentences.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tüm alanların doldurulması gerekmektedir", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kelime ekleme işlemi burada yapılacak
        Words.addWord(turkishWord, englishWord, sentences);

        // Kullanıcıya başarılı bir şekilde eklendiğini bildir
        JOptionPane.showMessageDialog(this, "Kelime başarıyla eklendi", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

        // Alanları temizle
        fld_turkish_word.setText("");
        fld_english_word.setText("");
        fld_sentences.setText("");
    }


    public static void main(String[] args) {
        new AddWordGUI();
    }
}
