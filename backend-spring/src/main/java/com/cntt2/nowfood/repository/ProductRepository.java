package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:09 AM
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContaining(String name, Pageable page);

    @Query("select p from Product p where p.id in :ids and p.isMain = false and p.shop.id = :shop")
    List<Product> findOptionsByIds(List<Integer> ids,Integer shop);

    @Query("select p from Product p where p.shop.id = :id or :id is null")
    Page<Product> findByShop(Integer id,Pageable pageable);
}
