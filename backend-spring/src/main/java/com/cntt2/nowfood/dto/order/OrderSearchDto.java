package com.cntt2.nowfood.dto.order;

import com.cntt2.nowfood.common.Constants;
import com.cntt2.nowfood.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/27/2021 11:33 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchDto extends SearchDto implements Serializable {
    private Integer customerId;
    private Date fromDate;
    private Date toDate;
    private Constants.OrderStatus orderStatus;
    private Constants.OrderType orderType;
    private Constants.PaymentMethod paymentMethod;
    private Constants.PaymentStatus paymentStatus;
}
