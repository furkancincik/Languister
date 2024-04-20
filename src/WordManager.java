import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WordManager {
    private Connection connection;

    public WordManager(Connection connection) {
        this.connection = connection;
    }

    public boolean addWord(String englishWord, String turkishTranslation, String exampleSentences, byte[] imageData, byte[] audioData) {
        String sql = "INSERT INTO words(english_word, turkish_translation, example_sentences, image_data, audio_data) VALUES (?,?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, englishWord);
            statement.setString(2, turkishTranslation);
            statement.setString(3, exampleSentences);
            statement.setBytes(4, imageData);
            statement.setBytes(5, audioData);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Kelime başarıyla eklendi.");
                return true;
            } else {
                System.out.println("Kelime eklenirken bir hata oluştu.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Kelime eklenirken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateWord(int wordID, String englishWord, String turkishTranslation, String exampleSentences, byte[] imageData, byte[] audioData) {
        String sql = "UPDATE words SET english_word = ?, turkish_translation = ?, example_sentences = ?, image_data = ?, audio_data = ? WHERE word_id = ? ;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, englishWord);
            statement.setString(2, turkishTranslation);
            statement.setString(3, exampleSentences);
            statement.setBytes(4, imageData);
            statement.setBytes(5, audioData);
            statement.setInt(6, wordID);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Kelime başarıyla güncellendi!");
                return true;
            } else {
                System.out.println("Kelime güncellenirken bir hata oluştu!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Kelime güncellenirken bir hata oluştu!");
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteWord(int wordID) {
        String sql = "DELETE FROM words WHERE word_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, wordID);
            int rowsUpdate = statement.executeUpdate();

            if (rowsUpdate > 0){
                System.out.println("Kelime başarıyla silindi!");
                return true;
            }else {
                System.out.println("Belirtilen ID'ye sahip kelime bulunamadı!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Kelime silinirken bir hata oluştu!");
            e.printStackTrace();
            return false;
        }
    }


    public List<String> searchWords(String keyword) {
        List<String> searchResults = new ArrayList<>();
        String sql = "SELECT english_word, turkish_translation FROM words WHERE english_word LIKE ? OR turkish_translation LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String englishWord = resultSet.getString("english_word");
                String turkishTranslation = resultSet.getString("turkish_translation");

                String result = "English Word: " + englishWord + ", Turkish Translation: " + turkishTranslation;
                searchResults.add(result);
            }
        } catch (SQLException e) {
            System.out.println("Kelime aranırken bir SQL hatası oluştu!");
            e.printStackTrace();
        }
        return searchResults;
    }


    public void listAllWords() {
        String sql = "SELECT * FROM words";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int wordId = resultSet.getInt("word_id");
                String englishWord = resultSet.getString("english_word");
                String turkishTranslation = resultSet.getString("turkish_translation");
                String exampleSentences = resultSet.getString("example_sentences");

                System.out.println("Word ID: " + wordId + ", English Word: " + englishWord + ", Turkish Translation: " + turkishTranslation + ", Example Sentences: " + exampleSentences);
            }
        } catch (SQLException e) {
            System.out.println("Kelime listelenirken bir hata oluştu!");
            e.printStackTrace();
        }
    }







}
