package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import com.cntt2.nowfood.mapper.ShopMapper;
import com.cntt2.nowfood.repository.ShopRepository;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 3:50 PM
 */
@Service
public class ShopServiceImpl extends GenericServiceImpl<Shop,Integer> implements ShopService {

    private final ShopRepository shopRepository;

    private final ShopMapper shopMapper;

    public ShopServiceImpl(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    @Override
    public ShopFormDto createShop(ShopFormDto dto) {
        // 1 mapping to entity
        Shop entity = this.shopMapper.formToEntity(dto);
        // 2 save entity
        // Todos: check duplicate value, save Owner
        entity = this.shopRepository.save(entity);
        // 3 return mapping to dto
        return this.shopMapper.toFormDto(entity);
    }

    @Override
    public ShopFormDto findById(Integer id) {
        return null;
    }

    @Override
    public Page<ShopFormDto> findByAdvSearch(SearchDto searchDto) {
        return null;
    }

    @Override
    public Optional<Shop> getOwnerLogin() {
        UserPrincipal user = SecurityUtils.getCurrentUser();
        if(null != user){
            boolean isAdmin = user.getAuthorities().contains("ADMIN");
            Optional<Shop> owner = shopRepository.findByOwner(user.getUsername());
            if(isAdmin){
                owner = Optional.empty();
            } else if (owner.isEmpty())
                throw new EntityNotFoundException("Tài khoản chưa liên kết với cửa hàng !");
            return owner;
        }
        return Optional.empty();
    }
}
