package com.eeeyou.feiesdk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FlyGooseConfig
 * <p>
 * 飞鹅打印配置
 *
 * @author : SongJiping
 * @since : 2025/11/4 下午12:01
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fg")
public class FlyGooseConfig {
    /**
     * 飞鹅云用户名
     */
    private String user;

    /**
     * 飞鹅云密钥
     */
    private String uKey;

    /**
     * 飞鹅公钥
     */
    private String publicKey;

    /**
     * 扫码回调私钥
     */
    private String scanPrivateKey;

}
