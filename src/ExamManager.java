import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamManager {
    private Connection connection;

    public ExamManager(Connection connection) {
        this.connection = connection;
    }




    public boolean checkAnswer(String englishWord, String userAnswer){
        String correctAnswer = getTurkishTranslation(englishWord);
        if (correctAnswer != null){
            return correctAnswer.equalsIgnoreCase(userAnswer);
        }else {
            System.out.println("Yanlış cevap! Lütfen tekrar deneyin.");
            return false;
        }
    }

    public String getRandomEnglishWord(){
        String sql = "SELECT english_word FROM words ORDER BY RANDOM() LIMIT 1;";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("english_word");
            }
        }catch (SQLException e){
            System.out.println("İngilizce kelime alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String getTurkishTranslation(String englishWord){
        String sql = "SELECT turkish_translation FROM words WHERE english_word = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, englishWord);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("turkish_translation");
            }
        }catch (SQLException e){
            System.out.println("Türkçe çeviri alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



}
