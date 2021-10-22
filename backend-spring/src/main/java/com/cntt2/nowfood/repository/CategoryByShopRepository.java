package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.CategoryByShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/22/2021 12:12 AM
 */
@Repository
public interface CategoryByShopRepository extends JpaRepository<CategoryByShop,Integer> {
    @Query("select c from CategoryByShop c where c.id in (:ids) and c.createdByShop = :shop")
    List<CategoryByShop> findByIds(List<Integer> ids, Integer shop);
}
