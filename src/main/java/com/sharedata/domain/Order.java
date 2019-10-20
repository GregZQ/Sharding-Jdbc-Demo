package com.sharedata.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("order")
public class Order {
    @TableId
    private Long orderId;
    private Long userId;
    private Integer amount;
}
