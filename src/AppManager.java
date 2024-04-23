import java.sql.Connection;
import java.util.Scanner;

public class AppManager {
    private final UserManager userManager;
    private final WordManager wordManager;
    private final ExamManager examManager;
    private final UserSettingsManager userSettingsManager;
    private final AnalysisReportManager analysisReportManager;


    public AppManager(Connection connection) {
        this.userManager = new UserManager(connection);
        this.wordManager = new WordManager(connection);
        this.examManager = new ExamManager(connection);
        this.userSettingsManager = new UserSettingsManager(connection);
        this.analysisReportManager = new AnalysisReportManager(connection);
    }


    public void start() {
        Scanner input = new Scanner(System.in);

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = input.nextInt();
            input.nextLine();

            switch (choice){
                case 1:
                    loginUser(input);
                    break;
                case 2:
                    registerUser(input);
                    break;
                case 3:
                    System.out.println("Kullanıcı ID' sini giriniz : ");
                    int userID = input.nextInt();
                    generateAnalysisReport(userID);
                    break;
                case 4:
                    addNewWord(input);
                    break;
                case 5:
                    System.out.println("Uygulamadan çıkılıyor...");
                    running = false;
                    break;
                default:
                    System.out.println("Geçersiz seçim. Lütfen tekrar deneyiniz.");
                    break;
            }
        }
        input.close();
    }

    private void displayMainMenu() {
        System.out.println("1. Kullanıcı Girişi");
        System.out.println("2. Yeni Kullanıcı Kaydı");
        System.out.println("3. Analiz Raporu Alma");
        System.out.println("4. Yeni Kelime Ekleme");
        System.out.println("5. Çıkış");
        System.out.print("Seçiminiz: ");
    }


    private void loginUser(Scanner scanner) {
        boolean loginSuccessful = false;
        while (!loginSuccessful) {
            System.out.println("E-Posta: ");
            String email = scanner.nextLine();
            System.out.println("Şifre: ");
            String password = scanner.nextLine();
            loginSuccessful = userManager.loginUser(email, password);
            if (loginSuccessful) {
                System.out.println("Giriş başarılı.");
            } else {
                System.out.println("Giriş başarısız! Lütfen tekrar deneyin.");
            }
        }
    }

    private void registerUser(Scanner scanner) {
        boolean registered = false;
        while (!registered) {
            System.out.println("Kullanıcı adı :");
            String username = scanner.nextLine();
            System.out.println("E-Posta :");
            String email = scanner.nextLine();
            System.out.println("Şifre :");
            String password = scanner.nextLine();
            registered = userManager.registerUser(username, email, password);
            if (registered){
                System.out.println("Kullanıcı başarıyla kaydedildi.");
            }else {
                System.out.println("Kullanıcı kaydedilirken bir hata oluştu. Lütfen tekrar deneyiniz.");
            }
        }
    }


    private void generateAnalysisReport(int userID) {
        int correctAnswerCount = analysisReportManager.getRecentCorrectAnswerCount(userID);
        System.out.println("Kullanıcının son 10 sorudan doğru cevapladığı soru sayısı: " + correctAnswerCount);
    }

    private void addNewWord(Scanner scanner){
        boolean added = false;
        while (!added){
            System.out.println("İngilizce kelime : ");
            String englishWord = scanner.nextLine();
            System.out.println("Türkçe karşılığı : ");
            String turkishTranslation = scanner.nextLine();
            // diğer gerekenler burada istenecek !
            added = wordManager.addWord(englishWord, turkishTranslation, null, null, null);
            if (added){
                System.out.println("Yeni kelime başarıyla eklendi.");
            }else {
                System.out.println("Kelime eklenirken bir hata oluştu. Lütfen tekrar deneyiniz.");
            }
        }
    }




}
