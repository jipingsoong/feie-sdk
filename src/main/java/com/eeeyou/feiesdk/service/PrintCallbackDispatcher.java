package com.eeeyou.feiesdk.service;

import com.eeeyou.feiesdk.config.FlyGooseConfig;
import com.eeeyou.feiesdk.entity.PrintCallbackRequest;
import com.eeeyou.feiesdk.entity.PrintCallbackResult;
import com.eeeyou.feiesdk.utils.PrintSignatureUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PrintCallbackDispatcher {

    private final List<PrintCallbackHandler> handlers;

    private final FlyGooseConfig config;

    /**
     * 处理回调请求
     *
     * @param request 打印回调请求实体
     * @return 打印回调业务返回
     */
    public PrintCallbackResult handleCallback(PrintCallbackRequest request) {

        // 验证飞鹅公钥配置
        if (config == null || config.getPublicKey() == null) {
            return PrintCallbackResult.error("找不到飞鹅公钥配置");
        }

        // 验证签名
        if (!validateSignature(request, config.getPublicKey())) {
            return PrintCallbackResult.error("签名验证失败");
        }

        // 分发到业务处理器
        return dispatchToHandlers(request);
    }

    /**
     * 验证签名
     *
     * @param request   打印回调请求实体
     * @param publicKey 飞鹅公钥
     * @return 验签结果
     */
    private boolean validateSignature(PrintCallbackRequest request, String publicKey) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", request.getOrderId());
        params.put("status", String.valueOf(request.getStatus()));
        params.put("stime", String.valueOf(request.getStime()));
        params.put("sign", request.getSign());
        return PrintSignatureUtils.verifySignature(params, publicKey);
    }

    /**
     * 调用业务实现类
     *
     * @param request 打印回调请求实体
     */
    private PrintCallbackResult dispatchToHandlers(PrintCallbackRequest request) {
        PrintCallbackResult finalResult = null;
        for (PrintCallbackHandler handler : handlers) {
            try {
                PrintCallbackResult handlerResult = handler.handlePrintStatus(request.getOrderId(),
                        request.getStatus(), request.getStime());
                if (handlerResult != null) {
                    finalResult = handlerResult;
                }
            } catch (Exception e) {
                log.error("业务处理器执行异常: {}", e.getMessage(), e);
            }
        }
        return (finalResult != null) ? finalResult : PrintCallbackResult.error("业务处理异常");
    }
}
