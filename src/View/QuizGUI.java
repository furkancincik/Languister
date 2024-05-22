package View;

import Helper.*;
import Model.User;
import Model.Words;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class QuizGUI extends JFrame {
    private JPanel wrapper;
    private JTextArea txt_question;
    private JButton btn_option1;
    private JButton btn_option2;
    private JButton btn_option3;
    private JButton btn_option4;
    private List<Words> dueWords;
    private int currentWordIndex;
    private final User user;

    public QuizGUI(User user) {
        this.user = user;
        initComponents(); // GUI bileşenlerini başlatan metodu çağırın
        setContentPane(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(600, 500);
        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        dueWords = Words.getRandomWords(10); // 10 yerine kaç kelime seçmek isterseniz onu belirleyebilirsiniz
        Collections.shuffle(dueWords); // Soruları karıştır
        currentWordIndex = 0;

        // dueWords listesi boşsa quiz başlatılmadan pencere kapatılır
        if (dueWords.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cevaplanacak soru bulunmamaktadır!", "Quiz Tamamlandı", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        setVisible(true);

        loadNextQuestion();

        btn_option1.addActionListener(e -> checkAnswer(btn_option1.getText()));
        btn_option2.addActionListener(e -> checkAnswer(btn_option2.getText()));
        btn_option3.addActionListener(e -> checkAnswer(btn_option3.getText()));
        btn_option4.addActionListener(e -> checkAnswer(btn_option4.getText()));
    }

    private void initComponents() {
        wrapper = new JPanel(new BorderLayout());
        txt_question = new JTextArea();
        txt_question.setEditable(false); // Kullanıcı tarafından düzenlenemez
        txt_question.setLineWrap(true); // Satır taşması durumunda otomatik olarak satırı kaydır
        txt_question.setWrapStyleWord(true); // Kelimeleri kırparak satır taşmasını sağlar
        wrapper.add(txt_question, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btn_option1 = new JButton();
        btn_option2 = new JButton();
        btn_option3 = new JButton();
        btn_option4 = new JButton();
        buttonPanel.add(btn_option1);
        buttonPanel.add(btn_option2);
        buttonPanel.add(btn_option3);
        buttonPanel.add(btn_option4);
        wrapper.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadNextQuestion() {
        if (currentWordIndex < dueWords.size()) {
            Words currentWord = dueWords.get(currentWordIndex);
            txt_question.setText(currentWord.getEnglishWord());
            btn_option1.setText(currentWord.getTurkishTranslation());
            btn_option2.setText("Yanlış Seçenek 1");
            btn_option3.setText("Yanlış Seçenek 2");
            btn_option4.setText("Yanlış Seçenek 3");
        } else {
            JOptionPane.showMessageDialog(this, "Tüm soruları tamamladınız!", "Quiz Tamamlandı", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void checkAnswer(String answer) {
        Words currentWord = dueWords.get(currentWordIndex);
        boolean correct = answer.equals(currentWord.getTurkishTranslation());
        Words.updateCorrectStreak(user.getId(), currentWord.getId(), correct);
        if (correct) {
            Helper.showMsg("Doğru cevap!");
        } else {
            Helper.showMsg("Yanlış cevap!");
        }
        currentWordIndex++;
        loadNextQuestion();
    }

    public static void main(String[] args) {
        // Test için bir kullanıcı oluşturun veya mevcut bir kullanıcıyı kullanın
        User testUser = new User(); // Kullanıcıyı doğru şekilde oluşturduğunuzdan emin olun
        new QuizGUI(testUser);
    }
}
