package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 11:58 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByPhoneNumber(String phone);
    boolean existsByEmail(String email);
    boolean existsByUsername(String email);
    User findByUsername(String username);

    @Query("SELECT u FROM User u")
    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
