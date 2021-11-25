package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.CategoryByShop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopFormDto;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/11/2021 12:59 AM
 */
public interface CategoryByShopService extends GenericService<CategoryByShop,Integer> {
    List<CategoryByShopDto> findAll();

    CategoryByShopDto findById(Integer id);

    Page<CategoryByShopDto> findByAdvSearch(SearchDto dto);

    CategoryByShop saveOrUpdate(CategoryByShopFormDto form);

    CategoryByShop deleteById(Integer id);

    Collection<CategoryByShop> findByShopId(Integer shopId);
}
