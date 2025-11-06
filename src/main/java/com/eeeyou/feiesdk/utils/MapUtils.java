package com.eeeyou.feiesdk.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * MapUtils
 * <p>
 * 双列集合工具类
 *
 * @author : SongJiping
 * @since : 2025/11/3 下午5:22
 */
public class MapUtils {

    public static MultiValueMap<String, Object> convertToMultiValueMap(Map<String, String> params) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        params.forEach(multiValueMap::add);
        return multiValueMap;
    }
}
