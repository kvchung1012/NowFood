package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.dto.size.SizeDto;
import com.cntt2.nowfood.dto.size.SizeFormDto;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.SizeMapper;
import com.cntt2.nowfood.repository.SizeRepository;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 6:45 PM
 */
@Service
@RequiredArgsConstructor
public class SizeServiceImpl extends GenericServiceImpl<Size, Integer> implements SizeService {
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;
    private final ShopService shopService;


    @Override
    public List<Size> findByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public SizeDto findById(Integer id) {
        Size entity =repository.findById(id).orElse(null);
        SizeDto dto = sizeMapper.toDto(entity);
        return dto;
    }

    @Override
    public SizeDto saveOrUpdate(SizeFormDto form) {
        if(null == form){
            return null;
        }
        Size entity = null;
        if(null != form.getId()){
            entity = sizeRepository.findById(form.getId())
                    .orElseThrow(() -> new ValidException("Kích thước cập nhật không tồn tại!"));
        }
        if(null == entity){
            entity = new Size();
            Shop shop = shopService.getOwnerLogin(true).orElse(null);
            entity.setCreatedByShop(shop);
        }
        entity = this.sizeMapper.toEntity(form,entity);
        entity = this.sizeRepository.save(entity);
        return sizeMapper.toDto(entity);
    }
}
