package com.eeeyou.feiesdk.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeviceInfo {
    /**
     * 打印机型号：
     * 0：58小票机
     * 1：80小票机
     * 2：标签机
     * 3：出餐宝
     * 4：一体机
     */
    private Integer model;

    /**
     * 状态状态：
     * 0：离线
     * 1：在线，工作状态正常
     * 2：在线，工作状态不正常
     * 备注：工作状态不正常一般是无纸；
     * 离线一般指打印机与服务器失去联系超过2分钟。
     */
    private Integer status;

    /**
     * 是否开启自动打印LOGO：
     * N：未开启
     * Y：已开启
     */
    private String printlogo;

    /**
     * 扫码设备回调状态：
     * 0：未开启
     * 1：已开启
     */
    private Integer scanSwitch;
}
