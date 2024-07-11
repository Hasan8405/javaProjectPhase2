package citywars.finalproject.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/javaproject";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1384483100";

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameTaken(String username) {
        try {
            String query = "SELECT COUNT(*) FROM userdata WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertUser(String username, String password, String nickname, String email, String father, String color, String pet) {
        try {
            String query = "INSERT INTO userdata (username, password, nickname, email, father, color, pet) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, nickname);
            statement.setString(4, email);
            statement.setString(5, father);
            statement.setString(6, color);
            statement.setString(7, pet);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isValidCredentials(String username, String password) {
        String query = "SELECT * FROM userdata WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If a result is returned, credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isValidCredentials(String username, String father, String color, String pet) {
        try {
            String query = "SELECT * FROM userdata WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbFather = resultSet.getString("father");
                String dbColor = resultSet.getString("color");
                String dbPet = resultSet.getString("pet");

                return dbFather.equals(father) && dbColor.equals(color) && dbPet.equals(pet);
            }

            return false; // No user found with the given username
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUserField(String username, String fieldName, String newValue) {
        try {
            String query = "UPDATE userdata SET " + fieldName + " = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newValue);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // می‌توانید اینجا به مدیریت خطا بپردازید، مثلاً یک Exception را پرتاب کنید یا به همین شکل بگذارید
        }
    }

    public String getUserEmail(String username) {
        String email = null;
        try {
            String query = "SELECT email FROM userdata WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                email = resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }
    public String getUserNickname(String username) {
        String nickname = null;
        try {
            String query = "SELECT nickname FROM userdata WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nickname = resultSet.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nickname;
    }
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
