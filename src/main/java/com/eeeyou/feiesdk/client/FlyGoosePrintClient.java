package com.eeeyou.feiesdk.client;

import com.eeeyou.feiesdk.constant.ApiEnum;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static com.eeeyou.feiesdk.constant.ParamConstant.*;

/**
 * FlyGoosePrintClient
 * <p>
 * 飞鹅打印机客户端
 *
 * @author : SongJiping
 * @since : 2025/11/4 上午11:59
 */
@Component
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
     * 清空待打印队列
     *
     * @param sn 打印机编号
     * @return 调用结果
     */
    public void clearPrintQueue(@NonNull String sn) {
        HashMap<String, String> params = new HashMap<>();
        params.put(SN, sn);
        String result = httpClient.postForm(ApiEnum.PRINT_API, params, String.class);
    }

    /**
     * 查询订单状态
     *
     * @param orderId 订单编号
     * @return 调用结果
     */
    public void getPrinterStatus(@NonNull String orderId) {
        HashMap<String, String> params = new HashMap<>();
        params.put(ORDER_ID, orderId);
        String result = httpClient.postForm(ApiEnum.PRINTER_STATUS_API, params, String.class);
    }

    // 查询订单数
    public void getOrderInfoByDate(@NonNull String sn, @NonNull LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(formatter);
        HashMap<String, String> params = new HashMap<>();
        params.put(SN, sn);
        params.put(DATE, date);
        String result = httpClient.postForm(ApiEnum.ORDER_COUNT_API, params, String.class);
    }

}
