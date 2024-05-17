package Model;

import Helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;


    public User() {
    }

    public User(String password, String username, String name, int id) {
        this.password = password;
        this.username = username;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Connection connection;

    public User(Connection connection) {
        this.connection = connection;
    }

    public static boolean add(String name, String username, String password) {
        String query = "INSERT INTO users (username,password,name) VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setString(3, password);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean isLogin(String username, String password) {
        ArrayList<User> userList = getList();
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users;";
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }


    /*


    public static boolean add(String name, String username, String password, String type) {
        String sqlQuery = "INSERT INTO users (name, username, password, type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(username);
        if (findUser != null) {
            Helper.showMsg("Bu kullanıcı adı kullanılıyor.Başka bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setString(1, name);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, type);
            int result = pst.executeUpdate();

            if (result == -1) {
                Helper.showMsg("error");
            }
            return result != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

*/
    public static User getFetch(String username) {
        User name = null;

        String sqlQuery = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                name = new User();
                name.setId(rs.getInt("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }


}
