import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("Uygulama başlatılıyor...");
            AppManager appManager = new AppManager(connection);
            appManager.start();
        } else {
            System.out.println("Uygulama başlatılamadı. Veritabanı bağlantısında bir sorun oluştu.");
        }

    }
}
