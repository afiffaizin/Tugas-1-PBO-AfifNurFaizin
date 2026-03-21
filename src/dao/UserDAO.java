package dao;

import model.User;

/**
 * Interface khusus untuk entitas User.
 * Mewarisi semua operasi CRUD dasar dari BaseDAO, 
 * ditambah dengan metode unik untuk User (seperti otentikasi).
 */
public interface UserDAO extends BaseDAO<User> {
    
    /**
     * Tipe operasi otentikasi, di mana Object User yang bersangkutan dikembalikan 
     * jika username & password valid. Return null jika sebaliknya.
     */
    User authenticate(String username, String password);
    
    /**
     * Digunakan untuk memastikan username adalah unik sebelum insert ke database
     */
    boolean isUsernameExist(String username);
}
