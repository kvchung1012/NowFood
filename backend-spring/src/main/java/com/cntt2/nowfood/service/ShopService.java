package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.shop.ShopDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 3:50 PM
 */

public interface ShopService extends GenericService<Shop,Integer> {
    ShopFormDto createShop(ShopFormDto shop);
    ShopFormDto findById(Integer id);
    Page<ShopFormDto> findByAdvSearch(SearchDto searchDto);
    Optional<Shop> findByUser(String username);
    Optional<Shop> getOwnerLogin(boolean isRequired);
    ShopDto findDetailByShopId(Integer id);
}
