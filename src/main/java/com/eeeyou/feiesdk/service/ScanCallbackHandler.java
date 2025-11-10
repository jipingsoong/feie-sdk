package com.eeeyou.feiesdk.service;

import com.eeeyou.feiesdk.entity.ScanCallbackResult;

public interface ScanCallbackHandler {

    ScanCallbackResult handleScanContent(String content,String deviceSn) throws Exception;
}
