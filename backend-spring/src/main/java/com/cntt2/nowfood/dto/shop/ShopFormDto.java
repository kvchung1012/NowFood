package com.cntt2.nowfood.dto.shop;

import com.cntt2.nowfood.domain.BaseEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 3:54 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopFormDto implements Serializable {

    private Integer id;

    @NotEmpty(message = "Tên cửa hàng không được để trống")
    private String shopName;

    private String shopImage;

    @NotEmpty(message = "Số điện thoại liên hệ không được để trống")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    private String shopPhone;

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String shopEmail;

    @NotEmpty(message = "Địa chỉ cửa hàng không được để trống")
    private String shopAddress;

    private Date openTime;

    private Date closeTime;
}
