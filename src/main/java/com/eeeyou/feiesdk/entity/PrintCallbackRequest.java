package com.eeeyou.feiesdk.entity;

import lombok.Data;

@Data
public class PrintCallbackRequest {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态：1 -> 打印成功
     */
    private Integer status;

    /**
     * 订单状态变更UNIX时间戳，10位，精确到秒。
     */
    private Long stime;

    /**
     * 数字签名
     */
    private String sign;
}
