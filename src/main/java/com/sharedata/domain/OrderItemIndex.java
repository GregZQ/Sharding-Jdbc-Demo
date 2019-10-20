package com.sharedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("order_item_index")
public class OrderItemIndex {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Date createDate;
}
