package View;

import Helper.*;
import Model.User;
import Model.Words;

import javax.swing.*;
import java.util.ArrayList;

public class QuizGUI extends JFrame {
    private JPanel wrapper;
    private JTextArea txt_question;
    private JButton btn_option1;
    private JButton btn_option2;
    private JButton btn_option3;
    private JButton btn_option4;
    private ArrayList<Words> dueWords;
    private int currentWordIndex;
    private final User user;

    public QuizGUI(User user) {
        this.user = user;
        dueWords = Words.getDueWords(user.getId());
        currentWordIndex = 0;

        setContentPane(wrapper); // Form dosyasındaki wrapper paneli kullan
        setTitle(Config.PROJECT_TITLE);
        setSize(400, 300);
        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        loadNextQuestion();

        btn_option1.addActionListener(e -> checkAnswer(btn_option1.getText()));
        btn_option2.addActionListener(e -> checkAnswer(btn_option2.getText()));
        btn_option3.addActionListener(e -> checkAnswer(btn_option3.getText()));
        btn_option4.addActionListener(e -> checkAnswer(btn_option4.getText()));
    }

    private void loadNextQuestion() {
        if (currentWordIndex < dueWords.size()) {
            Words currentWord = dueWords.get(currentWordIndex);
            txt_question.setText(currentWord.getEnglishWord());
            // Burada seçenekleri rastgele ayarlamalısınız.
            btn_option1.setText(currentWord.getTurkishTranslation()); // Doğru cevabı burada yerleştiriyoruz
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
}
