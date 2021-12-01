package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.shop.ShopDetailDto;
import com.cntt2.nowfood.dto.shop.ShopDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.ShopMapper;
import com.cntt2.nowfood.repository.ShopRepository;
import com.cntt2.nowfood.repository.UserRepository;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.CommonUtils;
import com.cntt2.nowfood.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 3:50 PM
 */
@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends GenericServiceImpl<Shop,Integer> implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopMapper shopMapper;


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
        Shop entity = this.shopRepository.findById(id).orElse(null);
        return this.shopMapper.toFormDto(entity);
    }

    @Override
    public Page<ShopDto> findByAdvSearch(SearchDto dto) {
        Pageable pageable = CommonUtils.getPageRequest(dto);
        Page<Shop> entities = shopRepository.findByAdvSearch(dto,pageable);
        return entities.map(shopMapper::toDto);
    }

    @Override
    public Optional<Shop> findByUser(String username) {
        return shopRepository.findByOwner(username);
    }

    @Override
    public Optional<Shop> getOwnerLogin(boolean isRequired) {
        UserPrincipal user = SecurityUtils.getCurrentUser();
        if(null != user){
            boolean isAdmin = user.getAuthorities().contains("ADMIN");
            Optional<Shop> owner = shopRepository.findByOwner (user.getUsername());
            if(isAdmin && !isRequired){
                owner = Optional.empty();
            } else if (owner.isEmpty() && isRequired)
                throw new ValidException("Tài khoản chưa liên kết với cửa hàng !");
            return owner;
        }else if(isRequired){
            throw new ValidException("Bạn chưa đăng nhập!");
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Shop> getShopLogin(boolean isRequired) {
        UserPrincipal userLogin = SecurityUtils.getCurrentUser();
        if (userLogin!= null){
            boolean isAdmin = userLogin.getAuthorities().contains("ADMIN");
            User user = userRepository.findByUsername(userLogin.getUsername());
            if(user.getShop() == null && !isAdmin)
                throw new ValidException("Tài khoản chưa được liên kết với cửa hàng");
            else if(user.getShop() == null && isAdmin){
                return Optional.empty();
            }
            return Optional.ofNullable(user.getShop());
        }else if(isRequired){
            throw new ValidException("Bạn chưa đăng nhập");
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ShopDetailDto findDetailByShopId(Integer id) {
        Shop shop = this.shopRepository.findById(id).orElse(null);
        return this.shopMapper.toDetailDto(shop);
    }
}
