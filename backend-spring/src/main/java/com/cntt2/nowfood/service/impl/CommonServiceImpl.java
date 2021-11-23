package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.SystemMasterData;
import com.cntt2.nowfood.repository.SystemMasterDataRepository;
import com.cntt2.nowfood.service.SystemDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
        implements SystemDataService {
    private final SystemMasterDataRepository systemKeyRepository;

    @Override
    public List<SystemMasterData> getByParentId(Integer parentId) {
        return systemKeyRepository.findByParentId(parentId,Sort.by(Sort.Direction.ASC, "sortOrder"));
    }

    @Override
    public List<SystemMasterData> getByParentCode(String code) {
        SystemMasterData parent = systemKeyRepository.findByCode(code);
        if(parent == null) return null;
        else{
            return systemKeyRepository.findByParentId(parent.getId(),Sort.by(Sort.Direction.ASC, "sortOrder"));
        }
    }


}
