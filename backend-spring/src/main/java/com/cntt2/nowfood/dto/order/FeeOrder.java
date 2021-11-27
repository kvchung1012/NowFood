package com.cntt2.nowfood.dto.order;

import lombok.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/27/2021 12:10 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeeOrder {
    private Integer code;
    private String message;
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        private Double total;
        private Double service_fee;
        private Double insurance_fee;
        private Double pick_station_fee;
        private Double r2s_fee;
    }
}
