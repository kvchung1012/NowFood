package com.cntt2.nowfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class SearchDto {
    private Integer id;
    private UUID uuid;
    private Integer pageIndex;
    private Integer pageSize;
    private String keyword;
    public SearchDto(Integer pageSize,Integer pageIndex){
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }
}