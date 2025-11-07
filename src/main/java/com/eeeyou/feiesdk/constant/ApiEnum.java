package com.eeeyou.feiesdk.constant;

import lombok.Getter;

/**
 * ApiEnum
 * <p>
 * 接口枚举
 *
 * @author : SongJiping
 * @since : 2025/11/4 下午12:01
 */
@Getter
public enum ApiEnum {

    PRINT_API("Open_printMsg", "https://api.feieyun.cn/Api/Open/", "打印订单"),
    CLEAR_PRINT_QUEUE_API("Open_delPrinterSqs", "https://api.feieyun.cn/Api/Open/", "清空待打印队列"),
    ORDER_STATE_API("Open_queryOrderState", "https://api.feieyun.cn/Api/Open/",
            "查询订单是否打印成功"),
    ORDER_COUNT_API("Open_queryOrderInfoByDate", "https://api.feieyun.cn/Api/Open/",
            "查询指定设备某天的订单统计数"),
    PRINTER_STATUS_API("Open_queryPrinterStatus", "https://api.feieyun.cn/Api/Open/",
            "获取某台设备状态"),
    PRINTER_INFO("Open_printerInfo", "https://api.feieyun.cn/Api/Open/", "获取某台设备信息"),
    PRINTER_SET_SCAN_SWITCH("Open_printerSetScanSwitch", "https://api.feieyun.cn/Api/Open/",
            "设置设备扫码回调"),
    BATCH_ADD_DEVICE_API("Open_printerAddlist", "https://api.feieyun.cn/Api/Open/", "批量新增设备"),
    DELETE_DEVICE_API("Open_printerDelList", "https://api.feieyun.cn/Api/Open/", "批量删除设备"),
    MODIFY_DEVICE_API("Open_printerEdit", "https://api.feieyun.cn/Api/Open/", "修改设备信息"),
    ;


    private final String apiName;
    private final String url;
    private final String apiDesc;

    ApiEnum(String apiName, String url, String apiDesc) {
        this.apiName = apiName;
        this.url = url;
        this.apiDesc = apiDesc;
    }
}
