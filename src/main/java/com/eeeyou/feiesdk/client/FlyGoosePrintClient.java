package com.eeeyou.feiesdk.client;

import com.eeeyou.feiesdk.bulid.PrinterContentBuilder;
import com.eeeyou.feiesdk.constant.ApiEnum;
import com.eeeyou.feiesdk.constant.BaseConstant;
import com.eeeyou.feiesdk.entity.Device;
import com.eeeyou.feiesdk.entity.DeviceInfo;
import com.eeeyou.feiesdk.entity.OrderInfo;
import com.eeeyou.feiesdk.response.ApiBaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 打印
     *
     * @param sn      打印机编号
     * @param content 打印内容
     * @return 打印结果
     */
    public String print(String sn, String content) {
        Map<String, String> params = new HashMap<>();
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
        Map<String, String> params = new HashMap<>();
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
        Map<String, String> params = new HashMap<>();
        if (snList == null || snList.isEmpty()) {
            throw new IllegalArgumentException("设备编号集合不能为空");
        }
        params.put(SN_LIST, String.join(BaseConstant.MINUS, snList));
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
        Map<String, String> params = new HashMap<>();
        if (StringUtils.isEmpty(sn)) {
            throw new IllegalArgumentException("设备编号不能为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("设备名称不能为空");
        }
        params.put(SN, sn);
        params.put(NAME, name);
        if (StringUtils.isNotEmpty(phoneNum)) {
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
     *
     * 返回打印机信息。共四个：
     * 1、model机型，0：58小票机，1：80小票机，2：标签机，3：出餐宝，4：一体机
     * 2、status状态，0：离线 1：在线，工作状态正常 2：在线，工作状态不正常
     * 备注：工作状态不正常一般是无纸，离线的判断是打印机与服务器失去联系超过2分钟。
     * 3、printlogo是否开启自动打印LOGO，N：未开启、Y：已开启
     * 4、scanSwitch扫码设备回调状态，0：未开启、1：已开启
     */
    public ApiBaseResponse<DeviceInfo> getDeviceInfo(String sn) throws JsonProcessingException {
        Map<String, String> params = new HashMap<>();
        if (StringUtils.isEmpty(sn)) {
            throw new IllegalArgumentException("设备编号不能为空");
        }
        params.put(SN, sn);
        String resultObj = httpClient.postForm(ApiEnum.PRINTER_INFO, params, String.class);
        ApiBaseResponse<DeviceInfo> response = objectMapper.readValue(resultObj,
                objectMapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, DeviceInfo.class));
        if (response == null || response.getData() == null) {
            throw new NullPointerException("设备信息为空");
        }
        return response;
    }

    /**
     * 获取设备状态
     *
     * @param sn 设备编号
     * @return 返回设备状态信息。共三种：
     * 1、离线。
     * 2、在线，工作状态正常。
     * 3、在线，工作状态不正常。
     * 备注：异常一般是无纸，离线的判断是设备与服务器失去联系超过2分钟。
     */
    public ApiBaseResponse<String> getDeviceStatus(String sn) throws JsonProcessingException {
        Map<String, String> params = new HashMap<>();
        if (StringUtils.isEmpty(sn)) {
            throw new IllegalArgumentException("设备编号不能为空");
        }
        params.put(SN, sn);
        String resultObj = httpClient.postForm(ApiEnum.PRINTER_STATUS_API, params, String.class);
        ApiBaseResponse<String> response = objectMapper.readValue(resultObj,
                objectMapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, String.class));
        if (response == null || response.getData() == null) {
            throw new NullPointerException("设备信息为空");
        }
        return response;
    }

    /**
     * 标签打印接口
     *
     * @param sn      设备编号
     * @param content 打印内容（XML 标签格式）
     *                示例 String content = "<SIZE>40,30</SIZE><TEXT x=\"10\" y=\"20\" font=\"12\" w=\"1\" h=\"1\" r=\"0\">测试打印</TEXT>";
     *                纸张大小
     *                <SIZE>40,30</SIZE>
     *                设置标签纸尺寸（单位：毫米）
     *                格式：<SIZE>width,height</SIZE>
     *                表示：宽度 = 40mm 高度 = 30mm
     *                换算关系：1mm = 8dots（打印点阵） → 实际宽度 320 dots，高度 240 dots
     *                打印内容
     *                <TEXT x="10" y="20" font="12" w="1" h="1" r="0">测试打印</TEXT>
     *                x	起始横坐标	10	距离左边 10 dots
     *                y	起始纵坐标	20	距离上边 20 dots
     *                font	字体编号	12	表示“简体中文 24×24 Font (GB码)”
     *                w	宽度放大倍数	1	宽度放大 1 倍
     *                h	高度放大倍数	1	高度放大 1 倍
     *                r	旋转角度	0	不旋转（0度）
     *                内容	文本内容	测试打印	实际打印出来的文字
     *
     *                <BR> ：换行符
     *                <CUT> ：切刀指令(主动切纸,仅限切刀设备使用才有效果)
     *                <LOGO> ：打印LOGO指令(前提是预先在机器内置LOGO图片)
     *                <PLUGIN> ：钱箱
     *                <CB></CB> ：居中放大
     *                <B></B> ：放大一倍
     *                <C></C> ：居中
     *                <L></L> ：字体变高一倍
     *                <W></W> ：字体变宽一倍
     *                <QR></QR> ：二维码（单个订单，最多只能打印一个二维码）
     *                <RIGHT></RIGHT> ：右对齐
     *                <BOLD></BOLD> ：字体加粗
     * @param times   打印次数（可选，默认1）
     * @return 返回ApiBaseResponse<String>
     */
    public ApiBaseResponse<String> printLabel(String sn, String content, Integer times) throws Exception {
        if (StringUtils.isEmpty(sn)) {
            throw new IllegalArgumentException("设备编号不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw new IllegalArgumentException("打印内容不能为空");
        }
        Map<String, String> params = new HashMap<>();
        params.put(SN, sn);
        params.put("content", content);

        if (times != null && times > 1) {
            params.put("times", String.valueOf(times));
        }
        String resultObj = httpClient.postForm(ApiEnum.PRINT_ORDER_API, params, String.class);

        // 解析 JSON 成统一返回体
        ApiBaseResponse<String> response = objectMapper.readValue(resultObj,
                objectMapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, String.class));

        if (response == null || response.getData() == null) {
            throw new NullPointerException("打印返回为空");
        }
        return response;
    }

    /**
     * 清空待打印队列
     *
     * @param sn 打印机编号
     * @return 调用结果
     */
    public ApiBaseResponse<Boolean> clearPrintQueue(@NonNull String sn)
            throws JsonProcessingException {
        HashMap<String, String> params = new HashMap<>();
        params.put(SN, sn);
        String resultObj = httpClient.postForm(ApiEnum.PRINT_API, params, String.class);
        return objectMapper.readValue(resultObj,
                objectMapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, Boolean.class));
    }

    /**
     * 查询订单状态
     *
     * @param orderId 订单编号
     * @return 调用结果
     */
    public ApiBaseResponse<Boolean> getPrinterStatus(@NonNull String orderId)
            throws JsonProcessingException {
        HashMap<String, String> params = new HashMap<>();
        params.put(ORDER_ID, orderId);
        String resultObj = httpClient.postForm(ApiEnum.ORDER_STATE_API, params, String.class);
        return objectMapper.readValue(resultObj,
                objectMapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, Boolean.class));
    }

    /**
     * 查询订单数
     *
     * @param sn        打印机编号
     * @param localDate 日期
     * @return 调用结果
     */
    public ApiBaseResponse<OrderInfo> getOrderInfoByDate(@NonNull String sn, @NonNull LocalDate localDate)
            throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(formatter);
        HashMap<String, String> params = new HashMap<>();
        params.put(SN, sn);
        params.put(DATE, date);
        String resultObj = httpClient.postForm(ApiEnum.ORDER_COUNT_API, params, String.class);
        return objectMapper.readValue(resultObj,
                objectMapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, OrderInfo.class));
    }

    /**
     * 设置设备扫码回调
     *
     * @param sn           打印机编号
     * @param switchStatus 设备设置扫码路径，0：关闭，1：开启。
     * @return 调用结果
     */
    public ApiBaseResponse<Boolean> printerSetScanSwitch(@NonNull String sn, @NonNull int switchStatus)
            throws JsonProcessingException {
        HashMap<String, String> params = new HashMap<>();
        params.put(SN, sn);
        params.put(SWITCH, String.valueOf(switchStatus));
        String resultObj = httpClient.postForm(ApiEnum.ORDER_COUNT_API, params, String.class);
        return objectMapper.readValue(resultObj,
                objectMapper.getTypeFactory().constructParametricType(ApiBaseResponse.class, Boolean.class));
    }
}
