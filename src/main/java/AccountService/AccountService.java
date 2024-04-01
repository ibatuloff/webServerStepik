package AccountService;

import java.util.Map;
import java.util.HashMap;
import database.DatabaseService;

public class AccountService {
    private final DatabaseService dbService;

    public AccountService(DatabaseService dbService) {
            this.dbService = dbService;
        }


        public void singUp(String login, String password) {
            try {
                dbService.addUser(new UserProfile(login, password));
            } catch (Exception e) {
                System.out.println("Can't sing in: " + e.getMessage());
            }

        }

        public boolean signIn(String login, String password) {
            try {
                UserProfile profile = dbService.getUser(login);
                return profile != null && profile.getLogin().equals(login) && profile.getPassword().equals(password);
            } catch (Exception e) {
                System.out.println("Can't sing in: " + e.getMessage());
                return false;
            }

        }
    }