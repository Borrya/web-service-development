package accounts;

import dbService.DBException;
import dbService.DBService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, UserProfile> loginToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {

        try {
            DBService service = new DBService();

            service.addUser(userProfile);
            loginToProfile.put(userProfile.getLogin(), userProfile);
        } catch (DBException e) {
            System.out.println("Cannot add new user");
        }
    }

    public UserProfile getUserByLogin(String login) {
        try {
            DBService service = new DBService();
            service.getUser(login);
            return loginToProfile.get(login);
        } catch (SQLException e) {
            System.out.println("Cannot get the user by login");
        }
        return null;
    }
}
