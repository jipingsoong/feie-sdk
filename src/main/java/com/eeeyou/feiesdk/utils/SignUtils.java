package com.eeeyou.feiesdk.utils;


import com.eeeyou.feiesdk.config.FlyGooseConfig;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * SignUtils
 * <p>
 * 签名工具
 *
 * @author : SongJiping
 * @since : 2025/11/3 下午4:41
 */
public class SignUtils {

    /**
     * 根据配置获取签名
     *
     * @return 签名
     */
    public static String signature(String sTime) {
        FlyGooseConfig flyGooseConfig = ConfigUtils.getFlyGooseConfig();
        return signature(flyGooseConfig.getUser(), flyGooseConfig.getuKey(), sTime);
    }

    /**
     * 签名
     *
     * @param user  用户名
     * @param uKey  密钥
     * @param sTime 时间戳
     * @return 签名
     */
    public static String signature(String user, String uKey, String sTime) {
        return DigestUtils.sha1Hex(user + uKey + sTime);
    }
}
