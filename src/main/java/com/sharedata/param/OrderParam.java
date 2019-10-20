package com.sharedata.param;

import lombok.Data;

import java.util.List;

@Data
public class OrderParam {

    private Long userId;//用户id
    private Long orderId;

    private List<OrderItemParam> orderItemList;
}
