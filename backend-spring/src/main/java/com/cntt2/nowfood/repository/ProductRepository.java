package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.dto.product.ProductSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:09 AM
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContaining(String name, Pageable page);

    @Query("select p from Product p where p.id in :ids")
    Set<Product> findByIds(Set<Integer> ids);

    @Query("select p from Product p where p.id in :ids and p.isMain = false and (p.shop.id = :shop or :shop is null)")
    List<Product> findOptionsByIds(List<Integer> ids, Integer shop);

    @Query("select p from Product p where (p.shop.id = :id or :id is null) and p.voided = false ")
    Page<Product> findByShop(Integer id, Pageable pageable);

    @Query("select p from Product p where (p.shop.id = :id or :id is null) and p.voided = false " +
            " and (p.isMain = :isMain or :isMain is null) ")
    List<Product> findByShop(Integer id,Boolean isMain);

    @Query(value = "select distinct p from Product p " +
            "left join p.productCategories pc " +
            "left join p.shop s " +
            "where (1=1) " +
            "and (p.shop.id = :#{#dto.getShopId()} or :#{#dto.getShopId()} is null) " +
            "and (p.id = :#{#dto.getId()} or :#{#dto.getId()} is null) " +
            "and (p.uuid = :#{#dto.getUuid()} or :#{#dto.getUuid()} is null) " +
            "and ( " +
            "p.name LIKE %:#{#dto.getKeyword()}% " +
            "or s.shopName LIKE %:#{#dto.getKeyword()}% " +
            "or s.shopAddress LIKE %:#{#dto.getKeyword()}% " +
            "or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' " +
            ") " +
            "and (p.isMain = :#{#dto.getIsMain()} or :#{#dto.getIsMain()} is null ) " +
            "and (p.rate = :#{#dto.getRate()} or :#{#dto.getRate()} is null) " +
            "and (pc.category.id in (:#{#dto.getCategoryId()}) or :#{#dto.getCategoryId().size()} < 1 ) "+
            "and (pc.categoryByShop.id in (:#{#dto.getCategoryShopId()}) or :#{#dto.getCategoryShopId().size()} <1) " +
            "and (p.voided = :#{#dto.getVoided()} or p.voided = false ) "
    )
    Page<Product> findAdvSearch(ProductSearchDto dto,Pageable pageable);
}
