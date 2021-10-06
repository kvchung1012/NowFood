package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:09 AM
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,UUID> {
    Page<Product> findByNameContaining(String name, Pageable page);
}
