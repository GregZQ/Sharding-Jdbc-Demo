package com.sharedata.service;

import com.sharedata.param.OrderParam;

import java.util.List;

public interface OrderService {
    boolean insert(OrderParam orderParam);

    List<OrderParam> findByUid(Long userId, int page);
}
