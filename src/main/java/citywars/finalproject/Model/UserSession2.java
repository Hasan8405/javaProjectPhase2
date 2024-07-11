package citywars.finalproject.Model;

import citywars.finalproject.service.DatabaseManager;

public class UserSession2 {
    private static UserSession2 instance;
    private String username;
    private String password;
    private String nickname;
    private String email;

    // دیگر متغیرها
    private DatabaseManager databaseManager;

    private UserSession2() {
        this.databaseManager = new DatabaseManager();
    }
    public static UserSession2 getInstance() {
        if (instance == null) {
            instance = new UserSession2();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setUsername_updateDB(String username) {
        String currentUsername = this.username;
        this.username = username;
        // Update in the database
        databaseManager.updateUserField(currentUsername, "username" , username);
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPassword_updateDB(String password) {
        this.password = password;
        // Update in the database
        databaseManager.updateUserField(username, "password" , password);
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setNickname_updateDB(String nickname) {
        this.nickname = nickname;
        // Update in the database
        databaseManager.updateUserField(username, "nickname" , nickname);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEmail_updateDB(String email) {
        this.email = email;
        // Update in the database
        databaseManager.updateUserField(username, "email" , email);
    }

    public void logout() {
        this.username = null;
        this.password = null;
        this.nickname = null;
        this.email = null;
    }
}
