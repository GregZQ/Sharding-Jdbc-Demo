package com.sharedata.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.sharedata.domain.Order;
import com.sharedata.domain.OrderItem;
import com.sharedata.param.OrderItemParam;
import com.sharedata.param.OrderParam;
import com.sharedata.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/add")
    public boolean add(@RequestParam("orderParam") OrderParam orderParam){
        List<OrderItemParam> itemList = orderParam.getOrderItemList();
        Preconditions.checkNotNull(orderParam);
        Preconditions.checkNotNull(orderParam.getOrderItemList());
        Preconditions.checkArgument(itemList.size() >0 );


        return this.orderService.insert(orderParam);

    }

    @GetMapping("/findByUid")
    public List<OrderParam> findByUid(@RequestParam("uid") Long uid, @RequestParam("page")int page){
        Preconditions.checkNotNull(uid);

        return this.orderService.findByUid(uid,page);
    }

    @GetMapping("/testAdd")
    public boolean testAdd(){
        Random random = new Random();
        for (int i =1 ;i <=200000 ; i++){
            OrderParam orderParam = new OrderParam();
            orderParam.setUserId(Long.valueOf(i));

            int num  = random.nextInt(3);
            List<OrderItemParam> orderItemParams = new LinkedList<>();
            for(int j = 0 ;j < num+ 1; j++){
                OrderItemParam orderItemParam = new OrderItemParam();
                orderItemParam.setPrice(BigDecimal.valueOf(random.nextInt(100)));
                orderItemParam.setCount(random.nextInt(5));
                orderItemParam.setSkuId(Long.valueOf(0));
                orderItemParams.add(orderItemParam);
            }
            orderParam.setOrderItemList(orderItemParams);


            this.orderService.insert(orderParam);
        }

        return true;
    }
}
