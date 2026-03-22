package dao;

import model.User;

/**
 * Interface khusus untuk entitas User.
 */
public interface UserDAO extends BaseDAO<User> {
    
    User authenticate(String username, String password);
    
    boolean isUsernameExist(String username);
}
