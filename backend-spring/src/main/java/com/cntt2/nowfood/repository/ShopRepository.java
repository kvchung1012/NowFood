package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select s from Shop s " +
            "where (1=1) " +
            "and (s.id = :#{#dto.getId()} or :#{#dto.getId()} is null) " +
            "and (s.uuid = :#{#dto.getUuid()} or :#{#dto.getUuid()} is null) " +
            "and (s.shopName LIKE %:#{#dto.getKeyword()}% or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' ) " +
            "and (s.shopPhone LIKE %:#{#dto.getKeyword()}% or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' ) " +
            "and (s.shopEmail LIKE %:#{#dto.getKeyword()}% or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' ) " +
            "and (s.shopAddress LIKE %:#{#dto.getKeyword()}% or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' ) " +
            "and (s.voided = :#{#dto.getVoided()} or :#{#dto.getVoided()} is null) "
    )
    Page<Shop> findByAdvSearch(SearchDto dto, Pageable pageable);
}
