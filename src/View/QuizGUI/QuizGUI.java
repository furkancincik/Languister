package View.QuizGUI;

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
    private JLabel lbl_image;

    private List<Words> dueWords;
    private int currentWordIndex;
    private final User user;

    public QuizGUI(User user) {
        this.user = user;
        initComponents();
        setContentPane(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(400, 300); // Genişlik ve yüksekliği azalttık
        setLocation(Helper.screenLoc("x", getSize()), Helper.screenLoc("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        dueWords = Words.getRandomWords(10);
        Collections.shuffle(dueWords);
        currentWordIndex = 0;

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
        txt_question.setEditable(false);
        txt_question.setLineWrap(true);
        txt_question.setWrapStyleWord(true);
        txt_question.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        txt_question.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_question.setAlignmentY(Component.CENTER_ALIGNMENT);
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

        JPanel rightPanel = new JPanel(new BorderLayout());
        Dimension preferredSize = txt_question.getPreferredSize();
        rightPanel.setPreferredSize(new Dimension(preferredSize.width, preferredSize.height));
        lbl_image = new JLabel();
        lbl_image.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_image.setVerticalAlignment(SwingConstants.CENTER);
        Dimension imageSize = new Dimension(250, preferredSize.height); // Örneğin, genişlik 250 piksel olarak ayarladık
        lbl_image.setPreferredSize(imageSize);
        rightPanel.add(lbl_image, BorderLayout.CENTER);
        wrapper.add(rightPanel, BorderLayout.EAST);
    }


    private void loadNextQuestion() {
        if (currentWordIndex < dueWords.size()) {
            Words currentWord = dueWords.get(currentWordIndex);
            txt_question.setText(currentWord.getEnglishWord());
            byte[] imageData = currentWord.getImageData();
            if (imageData != null) {
                ImageIcon icon = new ImageIcon(imageData);
                lbl_image.setIcon(icon);
            } else {
                lbl_image.setIcon(null);
            }
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
        User testUser = new User();
        new QuizGUI(testUser);
    }
}
