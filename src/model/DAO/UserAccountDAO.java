package model.DAO;

import model.DTO.UserAccount;

import java.util.List;

public interface UserAccountDAO {

    List<UserAccount> accounts();
    UserAccount getAccountById(int id);
    UserAccount getAccountByUsername(String username);
    UserAccount getAccount(String username, String password);
    boolean checkUsernameExists(String username);
    boolean insert(UserAccount userAccount);
    boolean update(UserAccount userAccount);
    boolean delete(UserAccount userAccount);
}
