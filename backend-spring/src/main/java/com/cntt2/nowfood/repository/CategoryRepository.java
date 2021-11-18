package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 10:13 PM
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from Category c where c.id in :ids")
    List<Category> findByIds(List<Integer> ids);

    Optional<Category> findById(Integer id);

    @Query("select pc.category from ProductCategory pc left join pc.product " +
            "where pc.product.id = :id")
    List<Category> findByProductId(Integer id);

    @Query(value = "select c from Category c " +
            "where (1=1) " +
            "and (c.id = :#{#dto.getId()} or :#{#dto.getId()} is null) " +
            "and (c.uuid = :#{#dto.getUuid()} or :#{#dto.getUuid()} is null) " +
            "and (c.name LIKE %:#{#dto.getKeyword()}% " +
            "or c.code LIKE %:#{#dto.getKeyword()}% " +
            "or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' ) "
    )
    Page<Category> findByAdvSearch(SearchDto dto, Pageable pageable);
}
