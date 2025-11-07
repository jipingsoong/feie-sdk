package com.eeeyou.feiesdk.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderInfo {

    /**
     * 已经打印数量
     */
    private Integer print;

    /**
     * 等待答应数量
     */
    private Integer waiting;
}
