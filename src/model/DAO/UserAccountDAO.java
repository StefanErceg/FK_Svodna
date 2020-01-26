package model.DAO;

import model.DTO.UserAccount;

import java.util.List;

public interface UserAccountDAO {

    List<UserAccount> accounts();
    UserAccount getAccountById(int id);
    boolean insert(UserAccount userAccount);
    boolean update(UserAccount userAccount);
    boolean delete(UserAccount userAccount);
}
