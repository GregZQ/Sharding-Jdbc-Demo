package com.sharedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ArrayListMultimap;
import com.sharedata.domain.Order;
import com.sharedata.domain.OrderItem;
import com.sharedata.mapper.OrderItemMapper;
import com.sharedata.mapper.OrderMapper;
import com.sharedata.param.OrderItemParam;
import com.sharedata.param.OrderParam;
import com.sharedata.service.OrderService;
import com.sharedata.service.support.IndexSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IndexSupport indexSupport;

    @Autowired
    private OrderMapper orderMapper;



    @Autowired
    private OrderItemMapper orderItemMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(OrderParam orderParam) {
        List<OrderItemParam> itemList = orderParam.getOrderItemList();
        Order order = Order.builder().userId(orderParam.getUserId())
                .orderId(indexSupport.generatorOrderIndex())
                .amount(itemList.size()).build();
        this.orderMapper.insert(order);
        itemList.stream().forEach(t->{
            OrderItem orderItem = OrderItem.builder().count(t.getCount())
                                                    .orderItemId(indexSupport.generatorOrderItemIndex())
                                                    .price(t.getPrice())
                                                    .skuId(t.getSkuId())
                                                    .userId(order.getUserId())
                                                    .orderId(order.getOrderId())
                                                    .build();
            this.orderItemMapper.insert(orderItem);

        });
        return true;
    }

    @Override
    public List<OrderParam> findByUid(Long userId, int page) {
        //查询orders
        List<OrderParam> orderParams = this.orderMapper.selectByUserId(userId, page * 10 , 10).stream().map(t->{
            OrderParam orderParam = new OrderParam();
            BeanUtils.copyProperties(t, orderParam);
            return orderParam;
        }).collect(Collectors.toList());

        //查询对应item
        QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("user_id", userId);
        orderItemQueryWrapper.in("order_id", orderParams.stream().map(t->t.getOrderId()).collect(Collectors.toList()));
        List<OrderItem> orderItems = this.orderItemMapper.selectList(orderItemQueryWrapper);
        ArrayListMultimap<Long, OrderItemParam> listMultimap = ArrayListMultimap.create();
        orderItems.forEach(
                orderItem -> {
                    OrderItemParam orderItemParam = new OrderItemParam();
                    BeanUtils.copyProperties(orderItem, orderItemParam);
                    listMultimap.put(orderItem.getOrderId(),orderItemParam);
                }
        );
        orderParams.stream().forEach(
                orderParam -> {
                    orderParam.setOrderItemList(listMultimap.get(orderParam.getOrderId()));
                }
        );

        return orderParams;
    }
}
