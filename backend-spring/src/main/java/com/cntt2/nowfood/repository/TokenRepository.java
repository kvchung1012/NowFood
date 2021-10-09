package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 9:45 AM
 */
@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    Token findByToken(String token);
}
