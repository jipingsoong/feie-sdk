package com.eeeyou.feiesdk.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ScanSignatureUtils {

    /**
     * 解码参数
     *
     * @param encryptedBase64Data Base64参数
     * @param uKey                私钥
     * @return 解码内容
     * @throws Exception 异常
     */
    public static String decryptScanResult(String encryptedBase64Data, String uKey) throws Exception {
        PrivateKey privateKey = loadPrivateKey(uKey);
        // 1. Base64解码
        byte[] encryptedData = Base64.getDecoder().decode(encryptedBase64Data);

        // 2. RSA解密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        // 3. 返回原始扫码内容
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /**
     * 加载私钥
     */
    private static PrivateKey loadPrivateKey(String uKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(uKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
