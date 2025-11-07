package com.eeeyou.feiesdk.bulid;

import com.eeeyou.feiesdk.entity.Device;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PrinterContentBuilder {

    private static final String SPLIT = "#";
    private static final String LINE = "\\n";

    public static String build(List<Device> devices) {
        if (devices == null || devices.isEmpty()) {
            throw new IllegalArgumentException("设备列表不能为空");
        }

        return devices.stream()
                .map(PrinterContentBuilder::buildLine)
                .collect(Collectors.joining(LINE));
    }

    private static String buildLine(Device d) {
        if (ObjectUtils.isNotEmpty(d) && StringUtils.isBlank(d.getDeviceCode()) || StringUtils.isBlank(d.getDeviceId())) {
            throw new IllegalArgumentException("设备编号和设备识别码必填");
        }

        return String.join(SPLIT,
                d.getDeviceCode(),
                d.getDeviceId(),
                d.getRemark() == null ? "" : d.getRemark(),
                d.getSimCardNo() == null ? "" : d.getSimCardNo(),
                d.getBizType() == null ? "" : d.getBizType().toString()
        );
    }
}
