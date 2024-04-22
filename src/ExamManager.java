import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class ExamManager {
    private Connection connection;

    public ExamManager(Connection connection) {
        this.connection = connection;
    }

    public boolean saveUserAnswer(int userID, int questionID, boolean isCorrect) {
        String sql = "INSERT INTO user_question_relationship(user_id, question_id, is_correct, answered_at) VALUES (?,?,?,CURRENT_TIMESTAMP);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            statement.setInt(2, questionID);
            statement.setBoolean(3, isCorrect);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kullanıcı cevabı başarıyla kaydedildi.");
                return true;
            } else {
                System.out.println("Kullanıcı cevabı kaydedilmedi.");
            }
        } catch (SQLException e) {
            System.out.println("Kullanıcının cevabını kaydederken bir hata oluştu: " + e.getMessage());
            return false;
        }
        return false;
    }

    public boolean checkAnswer(String englishWord, String userAnswer) {
        String correctAnswer = getTurkishTranslation(englishWord);
        if (correctAnswer != null) {
            return correctAnswer.equalsIgnoreCase(userAnswer);
        } else {
            System.out.println("Yanlış cevap! Lütfen tekrar deneyin.");
            return false;
        }
    }

    public String getRandomEnglishWord() {
        String sql = "SELECT english_word FROM words ORDER BY RANDOM() LIMIT 1;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("english_word");
            }
        } catch (SQLException e) {
            System.out.println("İngilizce kelime alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String getTurkishTranslation(String englishWord) {
        String sql = "SELECT turkish_translation FROM words WHERE english_word = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, englishWord);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("turkish_translation");
            }
        } catch (SQLException e) {
            System.out.println("Türkçe çeviri alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Sorunun doğru cevaplanma sayısını günceller
    public void updateQuestionCorrectAnswerCount(int questionID) {
        String sql = "UPDATE questions SET correct_answer_count = correct_answer_count + 1 WHERE question_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sorunun doğru cevaplanma sayısı güncellendi.");
            } else {
                System.out.println("Sorunun doğru cevaplanma sayısı güncellenemedi.");
            }
        } catch (SQLException e) {
            System.out.println("Sorunun doğru cevaplanma sayısını güncellerken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Sorunun doğru cevaplanma sayısını sıfırlar
    public void resetQuestionCorrectAnswerCount(int questionID) {
        String sql = "UPDATE questions SET correct_answer_count = 0 WHERE question_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sorunun doğru cevaplanma sayısı sıfırlandı.");
            } else {
                System.out.println("Sorunun doğru cevaplanma sayısı sıfırlanamadı.");
            }
        } catch (SQLException e) {
            System.out.println("Sorunun doğru cevaplanma sayısını sıfırlarken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Soruyu test tarihine göre belirli bir aralıkta doğru cevaplanmış soruları getirir
    public List<String> getQuestionsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<String> questions = new ArrayList<>();
        String sql = "SELECT question_text FROM questions " +
                "WHERE correct_answer_count > 0 AND created_at BETWEEN ? AND ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(resultSet.getString("question_text"));
            }
        } catch (SQLException e) {
            System.out.println("Sorular alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return questions;
    }

    // Belirli bir tarih aralığında doğru cevaplanmış rastgele bir soru getirir
    public String getRandomQuestionByInterval(LocalDate lastShownDate, Period interval) {
        String sql = "SELECT question_text FROM questions " +
                "WHERE correct_answer_count > 0 AND created_at >= ? AND created_at <= ? " +
                "ORDER BY RANDOM() LIMIT 1;";
        LocalDate nextShowDate = lastShownDate.plus(interval);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(lastShownDate));
            statement.setDate(2, Date.valueOf(nextShowDate));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("question_text");
            }
        } catch (SQLException e) {
            System.out.println("Sorular alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    // Belirli bir kullanıcının belirli bir soruyu belirli bir tarih aralığında doğru cevaplamış olup olmadığını kontrol eder
    public boolean checkQuestionAnsweredCorrectly(int userID, int questionID, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) AS count FROM user_question_relationship " +
                "WHERE user_id = ? AND question_id = ? AND is_correct = true " +
                "AND answered_at BETWEEN ? AND ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            statement.setInt(2, questionID);
            statement.setDate(3, Date.valueOf(startDate));
            statement.setDate(4, Date.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count >= 6; // Kullanıcı 6 kez doğru cevaplamış mı?
            }
        } catch (SQLException e) {
            System.out.println("Soru doğru cevaplanma kontrolü sırasında bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Belirli bir soruyu bilinen sorular havuzuna taşır
    public void moveQuestionToKnownPool(int questionID) {
        String sql = "UPDATE questions SET known_pool = true WHERE question_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Soru bilinen sorular havuzuna taşındı.");
            } else {
                System.out.println("Soru bilinen sorular havuzuna taşınamadı.");
            }
        } catch (SQLException e) {
            System.out.println("Soru bilinen sorular havuzuna taşınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
