package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.dto.size.SizeDto;
import com.cntt2.nowfood.dto.size.SizeFormDto;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 6:44 PM
 */
public interface SizeService extends GenericService<Size,Integer> {
    List<Size> findByIds(List<Integer> ids);

    SizeDto findById(Integer id);

    SizeDto saveOrUpdate(SizeFormDto form);
}
