package com.eeeyou.feiesdk.client;

import com.eeeyou.feiesdk.bulid.PrinterContentBuilder;
import com.eeeyou.feiesdk.constant.ApiEnum;
import com.eeeyou.feiesdk.entity.Device;

import java.util.HashMap;
import java.util.List;

import static com.eeeyou.feiesdk.constant.ParamConstant.*;

/**
 * FlyGoosePrintClient
 * <p>
 * 飞鹅打印机客户端
 *
 * @author : SongJiping
 * @since : 2025/11/4 上午11:59
 */
public class FlyGoosePrintClient {

    public FlyGoosePrintClient(RestTemplateHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private final RestTemplateHttpClient httpClient;

    /**
     * 打印
     *
     * @param sn      打印机编号
     * @param content 打印内容
     * @return 打印结果
     */
    public String print(String sn, String content) {
        HashMap<String, String> params = new HashMap<>();
        params.put(SN, sn);
        params.put(CONTENT, content);
        params.put(TIMES, "1");
        return httpClient.postForm(ApiEnum.PRINT_API, params, String.class);
    }

    /**
     * 批量添加设备
     *
     * @param deviceList 设备信息
     * @return 批量添加设备结果
     */
    public String batchAddDevice(List<Device> deviceList) {
        HashMap<String, String> params = new HashMap<>();
        if (deviceList == null || deviceList.isEmpty()) {
            throw new IllegalArgumentException("设备列表不能为空");
        }
        params.put(PRINTER_CONTENT, PrinterContentBuilder.build(deviceList));
        return httpClient.postForm(ApiEnum.BATCH_ADD_DEVICE_API, params, String.class);
    }
}
