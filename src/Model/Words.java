package Model;

import Helper.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Words {
    private int id;
    private String englishWord;
    private String turkishTranslation;
    private String exampleSentences;
    private Timestamp createdAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getExampleSentences() {
        return exampleSentences;
    }

    public void setExampleSentences(String exampleSentences) {
        this.exampleSentences = exampleSentences;
    }

    public String getTurkishTranslation() {
        return turkishTranslation;
    }

    public void setTurkishTranslation(String turkishTranslation) {
        this.turkishTranslation = turkishTranslation;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    // Sınav için gerekli metotlar
    public static ArrayList<Words> getDueWords(int userId) {
        ArrayList<Words> wordList = new ArrayList<>();
        String query = "SELECT w.* FROM words w INNER JOIN user_question_relationship uqr ON w.word_id = uqr.word_id " +
                "WHERE uqr.user_id = ? AND (uqr.last_correct_date IS NULL OR " +
                "(uqr.correct_streak < 6 AND NOW() >= uqr.last_correct_date + " +
                "CASE uqr.correct_streak WHEN 0 THEN INTERVAL '1 day' " +
                "WHEN 1 THEN INTERVAL '1 week' " +
                "WHEN 2 THEN INTERVAL '1 month' " +
                "WHEN 3 THEN INTERVAL '3 months' " +
                "WHEN 4 THEN INTERVAL '6 months' " +
                "WHEN 5 THEN INTERVAL '1 year' END))";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, userId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Words word = new Words();
                word.setId(rs.getInt("word_id"));
                word.setEnglishWord(rs.getString("english_word"));
                word.setTurkishTranslation(rs.getString("turkish_translation"));
                word.setExampleSentences(rs.getString("example_sentences"));
                word.setCreatedAt(rs.getTimestamp("created_at"));
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    public static void updateCorrectStreak(int userId, int wordId, boolean correct) {
        String selectQuery = "SELECT * FROM user_question_relationship WHERE user_id = ? AND word_id = ?";
        String insertQuery = "INSERT INTO user_question_relationship (user_id, word_id, last_correct_date, correct_streak) VALUES (?, ?, ?, ?)";
        String updateQuery = "UPDATE user_question_relationship SET last_correct_date = ?, correct_streak = ? WHERE user_id = ? AND word_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(selectQuery);
            pr.setInt(1, userId);
            pr.setInt(2, wordId);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                int correctStreak = rs.getInt("correct_streak");
                if (correct) {
                    correctStreak = (correctStreak + 1) % 6;
                } else {
                    correctStreak = 0;
                }
                PreparedStatement updatePr = DBConnector.getInstance().prepareStatement(updateQuery);
                updatePr.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                updatePr.setInt(2, correctStreak);
                updatePr.setInt(3, userId);
                updatePr.setInt(4, wordId);
                updatePr.executeUpdate();
            } else {
                PreparedStatement insertPr = DBConnector.getInstance().prepareStatement(insertQuery);
                insertPr.setInt(1, userId);
                insertPr.setInt(2, wordId);
                insertPr.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                insertPr.setInt(4, correct ? 1 : 0);
                insertPr.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Words> getRandomWords(int count) {
        List<Words> allWords = getAllWords();
        Collections.shuffle(allWords);
        return allWords.subList(0, Math.min(count, allWords.size()));
    }

    public static List<Words> getAllWords() {
        List<Words> wordList = new ArrayList<>();
        String query = "SELECT * FROM words";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Words word = new Words();
                word.setId(rs.getInt("word_id"));
                word.setEnglishWord(rs.getString("english_word"));
                word.setTurkishTranslation(rs.getString("turkish_translation"));
                word.setExampleSentences(rs.getString("example_sentences"));
                word.setCreatedAt(rs.getTimestamp("created_at"));
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordList;
    }



    public static void addWord(String turkishWord, String englishWord, String exampleSentence) {
        String query = "INSERT INTO words (english_word, turkish_translation, example_sentences) VALUES (?, ?, ?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, englishWord);
            pr.setString(2, turkishWord);
            pr.setString(3, exampleSentence);
            pr.executeUpdate();
            System.out.println("Kelime başarıyla veritabanına eklendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
