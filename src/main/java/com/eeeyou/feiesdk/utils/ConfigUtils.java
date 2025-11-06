package com.eeeyou.feiesdk.utils;

import com.eeeyou.feiesdk.config.FlyGooseConfig;

/**
 * ConfigUtils
 * <p>
 * 配置工具类
 *
 * @author : SongJiping
 * @since : 2025/11/4 下午12:02
 */
public class ConfigUtils {

    private static FlyGooseConfig flyGooseConfig;

    public ConfigUtils(FlyGooseConfig flyGooseConfig) {
        ConfigUtils.flyGooseConfig = flyGooseConfig;
    }

    public static FlyGooseConfig getFlyGooseConfig() {
        return flyGooseConfig;
    }
}
