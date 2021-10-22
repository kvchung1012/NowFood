package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 10:13 PM
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("select c from Category c where c.id in :ids")
    List<Category> findByIds(List<Integer> ids);
}
