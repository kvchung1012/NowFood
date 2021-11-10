package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.SystemMasterData;
import com.cntt2.nowfood.repository.SystemMasterDataRepository;
import com.cntt2.nowfood.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 5:51 PM
 */
@Service
@RequiredArgsConstructor
public class CommonServiceImpl extends GenericServiceImpl<SystemMasterData, Integer>
        implements CommonService {
    private final SystemMasterDataRepository systemKeyRepository;

    @Override
    public List<SystemMasterData> getByParentId(Integer parentId) {
        return systemKeyRepository.findByParentId(parentId);
    }
}
