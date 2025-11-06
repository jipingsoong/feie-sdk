package com.eeeyou.feiesdk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FlyGooseConfig
 * <p>
 * 飞鹅打印配置
 *
 * @author : SongJiping
 * @since : 2025/11/4 下午12:01
 */
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getuKey() {
        return uKey;
    }

    public void setuKey(String uKey) {
        this.uKey = uKey;
    }
}
