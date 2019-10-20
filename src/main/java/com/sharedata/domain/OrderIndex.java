package com.sharedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@TableName("order_index")
public class OrderIndex {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Date createDate;
}
