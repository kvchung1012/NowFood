package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.SystemMasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 5:49 PM
 */
@Repository
public interface SystemMasterDataRepository extends JpaRepository<SystemMasterData,Integer> {
    List<SystemMasterData> findByParentId(Integer id);
}
