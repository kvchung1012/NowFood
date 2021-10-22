package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 3:49 PM
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer> {
    boolean existsByShopPhone(String phone);
    boolean existsByShopEmail(String email);
    @Query("select s from Shop s join fetch s.owner where s.owner.username = :username")
    Optional<Shop> findByOwner(String username);
}
