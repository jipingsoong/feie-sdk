package com.eeeyou.feiesdk.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
public class PrintSignatureUtils {

    /**
     * 验证飞鹅云回调签名
     *
     * @param params    验签参数
     * @param publicKey 公钥
     */
    public static boolean verifySignature(@NonNull Map<String, String> params, @NonNull String publicKey) {
        try {
            String signValue = params.get("sign");
            if (StringUtils.isBlank(signValue)) {
                log.error("签名参数sign不能为空");
                return false;
            }

            String dataToVerify = buildSignString(params);
            log.debug("待验签字符串: {}", dataToVerify);

            byte[] signatureBytes = Base64.getDecoder().decode(signValue);
            return verifySHA256WithRSA(dataToVerify.getBytes(StandardCharsets.UTF_8), signatureBytes, publicKey);
        } catch (Exception e) {
            log.error("验签过程发生异常: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 构造待验签字符串
     *
     * @param params 验签参数
     */
    private static String buildSignString(Map<String, String> params) {
        // 使用TreeMap自动按ASCII码排序
        Map<String, String> sortedParams = new TreeMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 剔除sign参数和空值参数
            if (!"sign".equals(key) && StringUtils.isNotBlank(value)) {
                sortedParams.put(key, value);
            }
        }

        // 拼接成 k1=v1&k2=v2 格式
        return sortedParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    /**
     * SHA256WithRSA验签
     */
    private static boolean verifySHA256WithRSA(byte[] data, byte[] signature, String publicKey)
            throws Exception {
        String publicKeyPEM = publicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(pubKey);
        sig.update(data);
        return sig.verify(signature);
    }
}
