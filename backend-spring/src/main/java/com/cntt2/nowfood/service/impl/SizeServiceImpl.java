package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.repository.SizeRepository;
import com.cntt2.nowfood.service.SizeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 6:45 PM
 */
@Service
public class SizeServiceImpl extends GenericServiceImpl<Size, Integer> implements SizeService {
    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Size> findByIds(List<Integer> ids) {
        return null;
    }
}
