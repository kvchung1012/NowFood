package com.cntt2.nowfood.dto.size;

import com.cntt2.nowfood.domain.ProductSize;
import com.cntt2.nowfood.domain.Shop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 3:31 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SizeFormDto implements Serializable {
    private Integer id;
    private Boolean voided=false;
    private Integer shopId;
    @NotNull
    @Size(max = 256, message="Tên kích thước không được vượt quá 256 kí tự!")
    private String name;
}
