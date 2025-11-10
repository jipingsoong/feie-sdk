package com.eeeyou.feiesdk.service;

import com.eeeyou.feiesdk.config.FlyGooseConfig;
import com.eeeyou.feiesdk.entity.ScanCallbackRequest;
import com.eeeyou.feiesdk.entity.ScanCallbackResult;
import com.eeeyou.feiesdk.utils.ScanSignatureUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ScanCallbackDispatcher {

    private final List<ScanCallbackHandler> handlers;

    private final FlyGooseConfig config;

    public ScanCallbackResult handleCallback(ScanCallbackRequest request) {
        // 验证飞鹅公钥配置
        if (config == null || config.getuKey() == null) {
            log.error("Private key or private key is null");
            return ScanCallbackResult.error();
        }

        try {
            String decryptScanResult = ScanSignatureUtils
                    .decryptScanResult(request.getSn(), config.getuKey());
            return dispatchToHandlers(decryptScanResult, request.getSn());
        } catch (Exception e) {
            log.error("decryptScanResult error", e);
            return ScanCallbackResult.error();
        }
    }

    /**
     * 调用业务实现类
     *
     * @param decryptedContent 解析内容
     * @param deviceSn         设备sn
     */
    private ScanCallbackResult dispatchToHandlers(String decryptedContent, String deviceSn) {
        ScanCallbackResult finalResult = null;

        for (ScanCallbackHandler handler : handlers) {
            try {
                ScanCallbackResult handlerResult = handler.handleScanContent(
                        decryptedContent, deviceSn
                );
                // 如果处理器返回成功结果，优先采用
                if (handlerResult != null) {
                    finalResult = handlerResult;
                    log.debug("处理器 {} 成功处理设备 {} 的请求",
                            handler.getClass().getSimpleName(), deviceSn);
                }
            } catch (Exception e) {
                log.error("处理器 {} 执行异常，设备SN: {}",
                        handler.getClass().getSimpleName(), deviceSn, e);
            }
        }
        return (finalResult != null) ? finalResult : ScanCallbackResult.error();
    }
}