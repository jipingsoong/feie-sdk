package com.eeeyou.feiesdk.client;

import com.eeeyou.feiesdk.config.FlyGooseConfig;
import com.eeeyou.feiesdk.constant.ApiEnum;
import com.eeeyou.feiesdk.exception.FlyGooseHttpException;
import com.eeeyou.feiesdk.utils.MapUtils;
import com.eeeyou.feiesdk.utils.SignUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.eeeyou.feiesdk.constant.ParamConstant.*;

/**
 * RestTemplateHttpClient
 * <p>
 * http请求客户端
 *
 * @author : SongJiping
 * @since : 2025/11/3 下午5:31
 */
public class RestTemplateHttpClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final FlyGooseConfig config;

    public RestTemplateHttpClient(FlyGooseConfig config) {
        this.config = config;
    }

    /**
     * post请求
     *
     * @param apiEnum api枚举
     * @param params  参数
     * @param clazz   返回类型
     * @return 响应结果
     */
    public <T> T postForm(ApiEnum apiEnum, Map<String, String> params, Class<T> clazz) throws FlyGooseHttpException {
        String sTime = String.valueOf(System.currentTimeMillis() / 1000);

        params.put(USER, config.getUser());
        params.put(SIG, SignUtils.signature(sTime));
        params.put(S_TIME, sTime);
        params.put(API_NAME, apiEnum.getApiName());
        return postForm(apiEnum.getUrl(), params, clazz);
    }

    /**
     * post请求
     *
     * @param url    url
     * @param params 参数
     * @param clazz  返回类型
     * @return 返回结果
     */
    private <T> T postForm(String url, Map<String, String> params, Class<T> clazz) throws FlyGooseHttpException {
        if (url != null && !url.isEmpty()) {
            try {
                MultiValueMap<String, Object> formData = MapUtils.convertToMultiValueMap(params);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formData, headers);
                ResponseEntity<T> response = this.restTemplate.postForEntity(url, request, clazz);
                if (response.getStatusCode().is2xxSuccessful()) {
                    return response.getBody();
                } else {
                    throw new FlyGooseHttpException("HTTP request failed with status: " + response.getStatusCode());
                }
            } catch (Exception e) {
                throw new FlyGooseHttpException("Failed to execute HTTP POST request to: " + url);
            }
        } else {
            throw new FlyGooseHttpException("URL cannot be null or empty");
        }
    }
}
