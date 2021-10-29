package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.domain.Size;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 6:44 PM
 */
public interface SizeService extends GenericService<Size,Integer> {
    List<Size> findByIds(List<Integer> ids);
}
