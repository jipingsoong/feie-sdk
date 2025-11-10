package com.eeeyou.feiesdk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScanCallbackResult {

    /**
     * SUCCESS 为上报成功，其余为失败，并默认播报上报失败语音
     */
    private String res;

    /**
     * 上报成功时需要播报的语音，枚举如下：
     * 0：滴
     * 40：出餐成功
     * 41：美团出餐成功
     * 42：饿了么出餐成功
     * 45：出餐失败
     * 46：美团出餐失败
     * 47：饿了么出餐失败
     * 53：上报成功
     * 54：上报失败
     * 55：开始核销
     * 62：核销成功
     * 72：核销失败
     */
    private Integer audio;

    public static ScanCallbackResult success(Integer audioCode) {
        return new ScanCallbackResult("SUCCESS", audioCode);
    }

    public static ScanCallbackResult error() {
        return new ScanCallbackResult("FAIL", 54); // 默认上报失败语音
    }
}
