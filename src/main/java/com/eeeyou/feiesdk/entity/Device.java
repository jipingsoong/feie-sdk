package com.eeeyou.feiesdk.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Device {
    //设备编号（必填）
    private String deviceCode;
    //设备识别码（必填）
    private String deviceId;
    //备注名称（选填）
    private String remark;
    //流量卡号（选填）
    private String simCardNo;
    //设备业务类型（选填）：1=打印，2=扫码
    private Integer bizType;
}
