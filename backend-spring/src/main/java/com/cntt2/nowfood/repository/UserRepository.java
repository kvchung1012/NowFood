package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 11:58 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
