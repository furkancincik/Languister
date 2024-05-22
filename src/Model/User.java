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
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
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

    public static boolean add(String name, String username, String password) {
        String query = "INSERT INTO users (username,password,name) VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, password);
            pr.setString(3, name);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        String sqlQuery = "SELECT * FROM users";
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                User obj = new User();
                obj.setId(rs.getInt("user_id"));
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

    public static User getFetch(String username) {
        User obj = null;
        String sqlQuery = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("user_id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public boolean updatePassword(String newPassword) {
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, newPassword);
            pr.setInt(2, this.id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkPassword(String username, String password) {
        User user = getFetch(username);
        return user != null && user.getPassword().equals(password);
    }



}
