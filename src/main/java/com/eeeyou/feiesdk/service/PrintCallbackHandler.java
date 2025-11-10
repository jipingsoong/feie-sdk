package com.eeeyou.feiesdk.service;

import com.eeeyou.feiesdk.entity.PrintCallbackResult;

public interface PrintCallbackHandler {

    /**
     * 处理打印状态回调
     *
     * @param orderId   订单id
     * @param status    订单状态 1打印
     * @param timestamp 订单状态变更UNIX时间戳，10位，精确到秒
     */
    PrintCallbackResult handlePrintStatus(String orderId, Integer status, Long timestamp);
}
