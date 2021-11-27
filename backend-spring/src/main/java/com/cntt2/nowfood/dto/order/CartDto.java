package com.cntt2.nowfood.dto.order;

import com.cntt2.nowfood.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 10:49 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {
    @NotEmpty(message="Tên người nhận không được để trống !")
    @Size(max  = 256,message="Tên người nhận không được vượt quá 256 kí tự")
    private String shipName;

    @NotEmpty(message="Địa chỉ người nhận không được để trống !")
    @Size(max  = 256,message="Địa chỉ người nhận không được vượt quá 256 kí tự")
    private String shipAddress;

    @NotEmpty(message="Số điện thoại người nhận không được để trống !")
    @Size(max  = 256,message="Số điện thoại người nhận không được vượt quá 256 kí tự")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b",message="Số điện thoại người nhận không hợp lệ")
    private String shipMobile;
    private String note;
    private Constants.OrderType orderType;
    private Constants.PaymentMethod paymentMethod;
    @NotEmpty(message = "Giỏ hàng phải có ít nhất 1 sản phẩm")
    private Set<CartItemDto> cartItems;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartItemDto {
        private Integer productId;
        private Integer quantity=1;
    }
}
