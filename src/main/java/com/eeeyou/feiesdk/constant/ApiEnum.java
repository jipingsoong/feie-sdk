package com.eeeyou.feiesdk.constant;

/**
 * ApiEnum
 * <p>
 * 接口枚举
 *
 * @author : SongJiping
 * @since : 2025/11/4 下午12:01
 */
public enum ApiEnum {

    PRINT_API("Open_printMsg", "https://api.feieyun.cn/Api/Open/", "打印订单"),
    ;

    private final String apiName;
    private final String url;
    private final String apiDesc;

    ApiEnum(String apiName, String url, String apiDesc) {
        this.apiName = apiName;
        this.url = url;
        this.apiDesc = apiDesc;
    }

    public String getApiName() {
        return apiName;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public String getUrl() {
        return url;
    }
}
