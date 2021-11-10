package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Size;
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
}
