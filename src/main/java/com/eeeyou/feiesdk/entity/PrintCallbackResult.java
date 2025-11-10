package com.eeeyou.feiesdk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrintCallbackResult {

    private boolean success;
    private String message;
    private Object data;

    public static PrintCallbackResult success() {
        return new PrintCallbackResult(true, "SUCCESS", null);
    }

    public static PrintCallbackResult error(String message) {
        return new PrintCallbackResult(false, message, null);
    }
}
