package View;

import Helper.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWordGUI extends JFrame {
    private JPanel wrapper;
    private JTextField txtWord;
    private JTextField txtMeaning;
    private JTextField txtExampleSentence;
    private JTextField txtCategory;
    private JButton btnAddWord;

    public AddWordGUI() {
        Helper.setLayout();
        setContentPane(wrapper);
        setSize(650, 550);
        setVisible(true);
        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnAddWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWord();
            }
        });
    }

    private void addWord() {
        String word = txtWord.getText();
        String meaning = txtMeaning.getText();
        String exampleSentence = txtExampleSentence.getText();
        String category = txtCategory.getText();

        if (word.isEmpty() || meaning.isEmpty() || exampleSentence.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kelimenin eklenmesi işlemi burada yapılacak
        // Örneğin bir veritabanına kaydedebiliriz veya bir listeye ekleyebiliriz
        System.out.println("Word: " + word);
        System.out.println("Meaning: " + meaning);
        System.out.println("Example Sentence: " + exampleSentence);
        System.out.println("Category: " + category);

        // Kullanıcıya başarılı bir şekilde eklendiğini bildir
        JOptionPane.showMessageDialog(this, "Word added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Alanları temizle
        txtWord.setText("");
        txtMeaning.setText("");
        txtExampleSentence.setText("");
        txtCategory.setText("");
    }

    public static void main(String[] args) {
        AddWordGUI n = new AddWordGUI();
    }
}
