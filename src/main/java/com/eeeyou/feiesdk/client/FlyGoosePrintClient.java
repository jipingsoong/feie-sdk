package com.eeeyou.feiesdk.client;

import com.eeeyou.feiesdk.bulid.PrinterContentBuilder;
import com.eeeyou.feiesdk.constant.ApiEnum;
import com.eeeyou.feiesdk.constant.BaseConstant;
import com.eeeyou.feiesdk.entity.Device;
import com.eeeyou.feiesdk.entity.DeviceInfo;
import com.eeeyou.feiesdk.response.ApiBaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 批量删除设备
     *
     * @param snList 设备编号集合
     * @return 批量删除设备结果
     */
    public String batchDeleteDevice(List<String> snList) {
        HashMap<String, String> params = new HashMap<>();
        if (snList == null || snList.isEmpty()) {
            throw new IllegalArgumentException("设备编号集合不能为空");
        }
        params.put(SN, String.join(BaseConstant.MINUS, snList));
        return httpClient.postForm(ApiEnum.DELETE_DEVICE_API, params, String.class);
    }

    /**
     * 修改设备信息
     *
     * @param sn       设备信息
     * @param name     设备名称
     * @param phoneNum 流量卡号码
     * @return 批量修改设备结果
     */
    public String modifyDevice(String sn, String name, String phoneNum) {
        HashMap<String, String> params = new HashMap<>();
        if (StringUtils.isEmpty(sn)) {
            throw new IllegalArgumentException("设备编号不能为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("设备名称不能为空");
        }
        params.put(SN, sn);
        params.put(NAME, name);
        if (StringUtils.isNotEmpty(phoneNum)){
            params.put(PHONE_NUM, phoneNum);
        }
        params.put(PHONE_NUM, phoneNum);
        return httpClient.postForm(ApiEnum.MODIFY_DEVICE_API, params, String.class);
    }

    /**
     * 获取设备信息
     *
     * @param sn 设备编号
     * @return 批量修改设备结果
     */
    public ApiBaseResponse<DeviceInfo> getDeviceInfo(String sn) throws JsonProcessingException {
        HashMap<String, String> params = new HashMap<>();
        if (StringUtils.isEmpty(sn)) {
            throw new IllegalArgumentException("设备编号不能为空");
        }
        params.put(SN, sn);
        String resultObj = httpClient.postForm(ApiEnum.PRINTER_INFO, params, String.class);
        ObjectMapper mapper = new ObjectMapper();

        ApiBaseResponse<DeviceInfo> response = mapper.readValue(resultObj,
                        mapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, DeviceInfo.class));

        if (response == null || response.getData() == null){
            throw new NullPointerException("设备信息为空");
        }
        return response;
    }
}
