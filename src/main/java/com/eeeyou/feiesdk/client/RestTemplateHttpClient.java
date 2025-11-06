package com.eeeyou.feiesdk.utils;

import com.eeeyou.feiesdk.exception.FlyGooseHttpException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    public <T> T postForm(String url, Map<String, String> params, Class<T> clazz) throws FlyGooseHttpException {
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
