package com.cntt2.nowfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 9:45 AM
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto implements Serializable {
    private Integer id;
    private Boolean voided;
    private UUID uuid;
    private Integer shopId;
    private Integer pageIndex=0;
    private Integer pageSize=5;
    private String keyword;
    private String  asc;
    private String desc="createdDate";
    public SearchDto(Integer pageSize,Integer pageIndex){
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }
}