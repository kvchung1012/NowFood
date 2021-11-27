package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.domain.SystemMasterData;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 5:50 PM
 */
public interface SystemDataService extends GenericService<SystemMasterData, Integer> {
    List<SystemMasterData> getByParentId(Integer parentId);
    List<SystemMasterData> getByParentCode(String code);
}
