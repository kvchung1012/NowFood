package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/26/2021 10:45 PM
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleKey(String key);
}
