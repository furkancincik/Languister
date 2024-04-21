import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


    // doğru cevaplanmış soruları getir
    public List<String> getQuestionsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<String> questions = new ArrayList<>();
        String sql = "SELECT english_word FROM user_question_relationship " +
                "JOIN questions ON user_question_relationship.question_id = questions.question_id " +
                "WHERE user_questions_relationship.is_correct = true AND " +
                "user_question_relationship.answered_at BETWEEN ? AND ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(resultSet.getString("english_word"));
            }
        } catch (SQLException e) {
            System.out.println("Sorular alınırken bir hata oluştu: " + e.getMessage());
        }
        return questions;
    }

    //getirilen doğru cevaplanmmış sorular arasından rastgele seçim
    public String getRandomEnglishWordByInvertal(LocalDate lastShownDate, Period interval) {
        String sql = "SELECT english_word FROM user_question_relationship " +
                "JOIN questions ON user_question_relationship " +
                "WHERE user_question_relationship.is_correct = true AND "+
                "user_question_relationship.answered_at >= ? "+
                "AND user_question_relationship.answered_at <= ? "+
                "ORDER BY RANDOM() LIMIT 1;";
        LocalDate nextShowDate = lastShownDate.plus(interval);
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, Date.valueOf(lastShownDate));
            statement.setDate(2, Date.valueOf(nextShowDate));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("english_word");
            }
        }catch (SQLException e){
            System.out.println("İngilizce kelime alınırken bir hata oluştu: " + e.getMessage());

        }
        return null;
    }


}
