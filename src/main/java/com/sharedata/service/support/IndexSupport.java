package com.sharedata.service.support;

import com.sharedata.domain.OrderIndex;
import com.sharedata.domain.OrderItemIndex;
import com.sharedata.mapper.OrderIndexMapper;
import com.sharedata.mapper.OrderItemIndexMapper;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IndexSupport {

    @Autowired
    private OrderIndexMapper orderIndexMapper;

    @Autowired
    private OrderItemIndexMapper orderItemIndexMapper;

    /**
     * 生成全局唯一的order id
     * @return
     */
    public Long generatorOrderIndex(){
        OrderIndex orderIndex = new OrderIndex();
        orderIndex.setCreateDate(new Date());
        this.orderIndexMapper.insert(orderIndex);
        return orderIndex.getId();
    }

    /**
     * 生成全局唯一的orer item id
     * @return
     */
    public Long generatorOrderItemIndex(){
        OrderItemIndex orderItemIndex = new OrderItemIndex();
        orderItemIndex.setCreateDate(new Date());
        this.orderItemIndexMapper.insert(orderItemIndex);
        return orderItemIndex.getId();
    }

}
