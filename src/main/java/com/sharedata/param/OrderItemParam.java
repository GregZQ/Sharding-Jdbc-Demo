package com.sharedata.param;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemParam {

    private Long skuId;
    private int count;
    private BigDecimal price;
}
