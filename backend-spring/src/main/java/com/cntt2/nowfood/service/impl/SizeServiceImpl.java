package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.size.SizeDto;
import com.cntt2.nowfood.dto.size.SizeFormDto;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.SizeMapper;
import com.cntt2.nowfood.repository.SizeRepository;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.service.SizeService;
import com.cntt2.nowfood.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return sizeRepository.findByIds(ids,null);
    }

    @Override
    public SizeDto findById(Integer id) {
        Size entity =repository.findById(id).orElse(null);
        return sizeMapper.toDto(entity);
    }

    @Override
    public List<Size> findByShopId(Integer id) {
        return sizeRepository.findByShopId(id);
    }

    @Override
    public SizeDto saveOrUpdate(SizeFormDto form) {
        if(null == form){
            return null;
        }
        Shop owner = shopService.getOwnerLogin(true).orElse(null);
        Size entity = null;
        // Update
        if(null != form.getId()){
            entity = sizeRepository.findById(form.getId())
                    .orElseThrow(() -> new ValidException("Kích thước cập nhật không tồn tại!"));
        }
        // Add
        if(null == entity) {
            entity = new Size();
            // Nếu là admin thì lấy thông tin shop từ form, còn không thì lấy từ thông tin login
            if (null == owner) {
                Shop shop = shopService.getById(form.getShopId());
                if (null == shop) throw new ValidException("Cửa hàng không hợp lệ !");
                entity.setCreatedByShop(shop);
            } else {
                entity.setCreatedByShop(owner);
            }
        }
        entity = this.sizeMapper.toEntity(form,entity);
        entity = this.sizeRepository.save(entity);
        return sizeMapper.toDto(entity);
    }

    @Override
    public Page<SizeDto> findByAdvSearch(SearchDto dto) {
        Pageable pageable = CommonUtils.getPageRequest(dto);
        Page<Size> entities = sizeRepository.findfindByAdvSearch(dto, pageable);
        return entities.map(sizeMapper::toDto);
    }

    @Override
    @Transactional
    public Size deleteById(Integer id) {
        Shop owner = shopService.getOwnerLogin(true).orElse(null);
        Size old = this.sizeRepository.findById(id)
                .orElseThrow(() -> new ValidException("Kích thước không tồn tại"));
        if (null != owner &&
                !owner.getId().equals( old.getCreatedByShop().getId() )
        ) {
                throw new ValidException("Không thể xóa kích thước của cửa hàng khác");
        }
        this.sizeRepository.delete(old);
        return old;
    }
}
