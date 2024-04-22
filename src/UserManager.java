import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private Connection connection;

    public UserManager(Connection connection) {
        this.connection = connection;
    }

    public boolean registerUser(String username, String email, String password){
        String sql = "INSERT INTO users(username,email,password) VALUES (?,?,?);";
        try (PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1,username);
            statement.setString(2,email);
            statement.setString(3,password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Kayıt Başarılı !");
                return true;
            } else {
                System.out.println("Kayıt Başarısız !");
                return false;
            }        }catch (SQLException e){
            System.out.println("Kayıt Başarısız !");
            e.printStackTrace();
            return false;
        }
    }


    public boolean loginUser(String email, String password){
        String sql = "SELECT * FROM users WHERE email =? AND password = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Giriş Başarılı !");
                return true;
            } else {
                System.out.println("Giriş Başarısız !");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Giriş Başarısız !");
            e.printStackTrace();
            return false;
        }
    }

    public boolean resetPassword(String email, String newPassword){
        String sql = "UPDATE users SET password = ? WHERE email = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,newPassword);
            statement.setString(2,email);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Şifreniz başarılı bir şekilde değiştirildi !");
                return true;
            } else {
                System.out.println("Şifre değiştirme işlemi başarısız !");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Şifre değiştirme işlemi başarısız !");
            e.printStackTrace();
            return false;
        }
    }



    public boolean isEmailRegistered(String email){
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                return count > 0 ;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() <= 16;
    }

}
