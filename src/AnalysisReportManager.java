import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalysisReportManager {
    private Connection connection;

    public AnalysisReportManager(Connection connection) {
        this.connection = connection;
    }

    // 10 soru üzerinden kaç doğru yaptıgını döndürecek
    public int getRecentCorrectAnswerCount(int userID) {
        int correctAnswerCount = 0;
        String sql = "SELECT COUNT(*) AS correct_count " +
                "FROM (SELECT * FROM user_question_relationship " +
                "      WHERE user_id = ? AND is_correct = true " +
                "      ORDER BY answered_at DESC LIMIT 10) AS recent_answers;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                correctAnswerCount=resultSet.getInt("correct_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correctAnswerCount;

    }



}
