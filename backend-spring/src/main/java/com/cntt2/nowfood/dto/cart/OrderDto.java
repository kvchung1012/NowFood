package com.cntt2.nowfood.dto.cart;

import com.cntt2.nowfood.common.Constants;
import com.cntt2.nowfood.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 10:44 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto{
    private Integer id;
    private String shareCode;
    private String shipName;
    private String shipAddress;
    private String shipMobile;
    private Double shipPrice;
    private Double fee;
    private Double totalPrice;
    private DateTime confirmedAt;
    private Constants.OrderStatus orderStatus;
    private String confirmedBy;
    private Constants.OrderType orderType;
    private Constants.PaymentMethod paymentMethod;
    private Constants.PaymentStatus paymentStatus;
    private String shopName;
    private String shopAddress;
    private Set<OrderDetailDto> orderDetails;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailDto{
        private ProductDto product;
        private Integer quantity=1;
        private Double price;
        private String note;
    }
}
