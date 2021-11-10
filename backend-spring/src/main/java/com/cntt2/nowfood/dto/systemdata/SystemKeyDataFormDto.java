package com.cntt2.nowfood.dto.systemdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 6:06 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemKeyDataFormDto implements Serializable {
    private Integer id;
    private Integer parentId;
    @NotNull
    @Size(max = 256,message="Độ dài code không được vướt quá 256 kí tự")
    private String code;
    @NotNull
    @Size(max = 256,message="Độ dài giá trị không được vướt quá 256 kí tự")
    private String value;
    private String subValue;
    private Integer sortOrder;
}
