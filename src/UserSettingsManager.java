import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSettingsManager {
    private Connection connection;

    public UserSettingsManager(Connection connection){
        this.connection = connection;
    }

    public int getMaxNewWordsSetting(int userID) {
        int maxNewWords = -1;
        String sql = "SELECT max_new_words FROM user_settings WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                maxNewWords = resultSet.getInt("max_new_words");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxNewWords;
    }

    public boolean updateMaxNewWordsSetting(int userID, int maxNewWords){
        String sql = "INSERT INTO user_settings (user_id, max_new_words) VALUES (?, ?) " +
                "ON CONFLICT (user_id) DO UPDATE SET max_new_words = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            statement.setInt(2, maxNewWords);
            statement.setInt(3, maxNewWords);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
