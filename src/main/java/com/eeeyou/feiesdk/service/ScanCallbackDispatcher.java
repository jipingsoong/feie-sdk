package com.eeeyou.feiesdk.service;

import com.eeeyou.feiesdk.entity.ScanCallbackRequest;
import com.eeeyou.feiesdk.entity.ScanCallbackResult;
import com.eeeyou.feiesdk.utils.ScanSignatureUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ScanCallbackDispatcher {

    /**
     * 扫码回调
     *
     * @param request             请求参数
     * @param privateKey          打印机对应的私钥
     * @param scanCallbackHandler 业务处理实现逻辑
     * @return 处理返回结果
     */
    public ScanCallbackResult handleCallback(@NonNull ScanCallbackRequest request, @NonNull String privateKey,
                                             @NonNull ScanCallbackHandler scanCallbackHandler) {
        try {
            String decryptScanResult = ScanSignatureUtils.decryptScanResult(request.getSn(), privateKey);
            return scanCallbackHandler.handleScanContent(decryptScanResult, request.getSn());
        } catch (Exception e) {
            log.error("decryptScanResult error", e);
            return ScanCallbackResult.error();
        }
    }
}