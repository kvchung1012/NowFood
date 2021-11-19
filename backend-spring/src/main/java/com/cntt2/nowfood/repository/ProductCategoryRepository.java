package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/17/2021 11:29 PM
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    @Query("select pc from ProductCategory pc " +
            "left join pc.category left join pc.categoryByShop " +
            "where pc.product.id = :productId ")
    List<ProductCategory> findByProductId(Integer productId);
}
