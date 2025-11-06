package com.eeeyou.feiesdk.client;

import com.eeeyou.feiesdk.config.FlyGooseConfig;
import com.eeeyou.feiesdk.utils.SignUtils;

import java.util.HashMap;


public class PrintClient {

    public PrintClient(FlyGooseConfig config, RestTemplateHttpClient httpClient) {
        this.httpClient = httpClient;
        this.config = config;
    }

    private final RestTemplateHttpClient httpClient;
    private final FlyGooseConfig config;

    public String print(String sn) {
        String content;
        content = "<CB>测试打印</CB><BR>";
        content += "名称　　　　　 单价  数量 金额<BR>";
        content += "--------------------------------<BR>";
        content += "饭1　　　　　　 1.0    1   1.0<BR>";
        content += "炒1　　　　 10.0   10  10.0<BR>";
        content += "蛋炒饭　　　　 10.0   10  100.0<BR>";
        content += "鸡蛋炒饭　　　 100.0  1   100.0<BR>";
        content += "番茄蛋炒饭　　 1000.0 1   100.0<BR>";
        content += "西红柿蛋炒饭　 1000.0 1   100.0<BR>";
        content += "西红柿鸡蛋炒饭 100.0  10  100.0<BR>";
        content += "备注：加辣<BR>";
        content += "--------------------------------<BR>";
        content += "合计：xx.0元<BR>";
        content += "送货地点：广州市南沙区xx路xx号<BR>";
        content += "联系电话：13888888888888<BR>";
        content += "订餐时间：2016-08-08 08:08:08<BR>";
        content += "<QR>http://www.dzist.com</QR>";


        String sTime = String.valueOf(System.currentTimeMillis() / 1000);

        HashMap<String, String> params = new HashMap<>();
        params.put("user", config.getUser());
        params.put("stime", sTime);
        params.put("sig", SignUtils.signature(sTime));
        params.put("apiname", "Open_printMsg");
        params.put("sn", sn);
        params.put("content", content);
        params.put("times", "1");

        return httpClient.postForm("https://api.feieyun.cn/Api/Open/", params, String.class);
    }
}
