package com.eeeyou.feiesdk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScanCallbackRequest {

    /**
     * RSA 算法加密 Base64编码的扫码内容，接收后使用私钥解密出原始扫码内容
     */
    private String result;

    /**
     * 打印机编号，如：121234
     */
    private String sn;
}
