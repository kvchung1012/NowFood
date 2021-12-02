package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.CategoryByShop;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopFormDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.CategoryByShopMapper;
import com.cntt2.nowfood.repository.CategoryByShopRepository;
import com.cntt2.nowfood.service.CategoryByShopService;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/11/2021 12:59 AM
 */
@Service
@RequiredArgsConstructor
public class CategoryByShopServiceImpl extends GenericServiceImpl<CategoryByShop, Integer> implements CategoryByShopService {

    private final CategoryByShopRepository categoryByShopRepository;
    private final ShopService shopService;
    private final CategoryByShopMapper categoryByShopMapper;

    @Override
    public List<CategoryByShopDto> findAll() {
        List<CategoryByShop> entities = categoryByShopRepository.findAll();
        return entities.stream()
                .map(categoryByShopMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryByShopDto findById(Integer id) {
        CategoryByShop entity = categoryByShopRepository.findById(id).orElse(null);
        return categoryByShopMapper.toDto(entity);
    }

    @Override
    public Page<CategoryByShopDto> findByAdvSearch(SearchDto dto) {
        Pageable pageable = CommonUtils.getPageRequest(dto);
        Page<CategoryByShop> entities = categoryByShopRepository.findByAdvSearch(dto,pageable);
        return entities.map(categoryByShopMapper::toDto);
    }

    @Override
    public CategoryByShop saveOrUpdate(CategoryByShopFormDto form) {
        CategoryByShop entity = null;
        // get  shop login, if admin = null, else = shopId
        Shop owner = shopService.getShopLogin(true).orElse(null);
        if(null == form) return null;
        if (null != form.getId()) {
            entity = categoryByShopRepository.findById(form.getId()).orElse(null);
        }
        if (null == entity) {
            entity = new CategoryByShop();
            // ADMIN get shop id by form
            if (owner == null) {
                if (null == form.getShopId()) throw new ValidException("Cửa hàng không được để trống !");
                ShopFormDto shop = shopService.findById(form.getShopId());
                if (null == shop)
                    throw new ValidException("Cửa hàng không hợp lệ !");
                entity.setCreatedByShop(form.getShopId());
            } else {
                entity.setCreatedByShop(owner.getId());
            }
        }
        entity = this.categoryByShopMapper.toEntity(form, entity);
        return this.categoryByShopRepository.save(entity);
    }

    @Override
    public CategoryByShop deleteById(Integer id) {
        Shop owner = shopService.getShopLogin(true).orElse(null);
        CategoryByShop old = this.categoryByShopRepository.findById(id)
                .orElseThrow(() -> new ValidException("Danh mục không tồn tại"));
        if(null != owner){
            if(!owner.getId().equals(old.getCreatedByShop()))
                throw new ValidException("Không thể xóa danh mục của cửa hàng khác");
        }
        this.categoryByShopRepository.delete(old);
        return old;
    }

    @Override
    public Collection<CategoryByShop> findByShopId(Integer shopId) {
        return this.categoryByShopRepository.findByShopId(shopId);
    }
}
