package com.eeeyou.feiesdk.service;

import com.eeeyou.feiesdk.config.FlyGooseConfig;
import com.eeeyou.feiesdk.entity.ScanCallbackRequest;
import com.eeeyou.feiesdk.entity.ScanCallbackResult;
import com.eeeyou.feiesdk.utils.ScanSignatureUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ScanCallbackDispatcher {

    private final FlyGooseConfig config;

    /**
     * 扫码回调
     *
     * @param request             请求参数
     * @param scanCallbackHandler 业务处理实现逻辑
     * @return 处理返回结果
     */
    public ScanCallbackResult handleCallback(@NonNull ScanCallbackRequest request,
                                             @NonNull ScanCallbackHandler scanCallbackHandler) {
        try {
            String decryptResult = ScanSignatureUtils.decryptScanResult(request.getSn(), config.getScanPrivateKey());
            return scanCallbackHandler.handleScanContent(decryptResult, request.getSn());
        } catch (Exception e) {
            log.error("decryptScanResult error", e);
            return ScanCallbackResult.error();
        }
    }
}