package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.size.SizeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 6:36 PM
 */
@Repository
public interface SizeRepository extends JpaRepository<Size,Integer> {
    @Query(value = "select s from Size s where s.id in :ids and (s.createdByShop.id = :shop or :shop is null)")
    List<Size> findByIds(List<Integer> ids,Integer shop);

    @Query(value = "select s from Size s " +
            "where (1=1) " +
            "and (s.id = :#{#dto.getId()} or :#{#dto.getId()} is null) " +
            "and (s.createdByShop.id = :#{#dto.getShopId()} or :#{#dto.getShopId()} is null) " +
            "and (s.uuid = :#{#dto.getUuid()} or :#{#dto.getUuid()} is null) " +
            "and (s.name LIKE %:#{#dto.getKeyword()}% or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' ) " +
            "and (s.voided = :#{#dto.getVoided()} or :#{#dto.getVoided()} is null) "
    )
    Page<Size> findfindByAdvSearch(SearchDto dto, Pageable pageable);

    @Query(value = " select s from Size s " +
            "where s.createdByShop.id = :id "
    )
    List<Size> findByShopId(Integer id);
}
