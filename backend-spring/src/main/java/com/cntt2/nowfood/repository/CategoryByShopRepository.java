package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.CategoryByShop;
import com.cntt2.nowfood.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/22/2021 12:12 AM
 */
@Repository
public interface CategoryByShopRepository extends JpaRepository<CategoryByShop,Integer> {
    @Query("select c from CategoryByShop c where c.id in (:ids) and ( c.createdByShop = :shop or :shop is null)")
    List<CategoryByShop> findByIds(List<Integer> ids, Integer shop);

    @Query(value = "select c from CategoryByShop c " +
            "where (1=1) " +
            "and (c.id = :#{#dto.getId()} or :#{#dto.getId()} is null) " +
            "and (c.createdByShop = :#{#dto.getShopId()} or :#{#dto.getShopId()} is null) " +
            "and (c.uuid = :#{#dto.getUuid()} or :#{#dto.getUuid()} is null) " +
            "and (c.name LIKE %:#{#dto.getKeyword()}% or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' ) " +
            "and (c.voided = :#{#dto.getVoided()} or c.voided = false) "
    )
    Page<CategoryByShop> findByAdvSearch(SearchDto dto, Pageable pageable);

    @Query(value = "select c from CategoryByShop c " +
            "where c.createdByShop = :shopId or :shopId is null")
    Collection<CategoryByShop> findByShopId(Integer shopId);
}
