package com.eeeyou.feiesdk.response;

import lombok.Data;

/**
 * 通用接口返回结构
 */
@Data
public class ApiBaseResponse<T> {

    /** 返回信息 */
    private String msg;

    /** 返回码，0表示成功 */
    private Integer ret;

    /** 数据体 */
    private T data;

    /** 服务端执行时间（毫秒） */
    private Long serverExecutedTime;
}
