# 飞鹅云辅助开发SDK
## 简介
这是飞鹅云的辅助开发SDK，用于帮助用户快速开发。  

----
## 使用方法
1. 引入SDK
2. 在引用改SDK的项目的配置文件中添加如下配置  
    2.1 fg.user 飞鹅云用户名（必填）  
    2.2 fg.uKey 飞鹅云用户密钥（必填）   
    2.3 fg.publicKey 公钥  
    2.4 fg.scanPrivateKey 扫码回调私钥  
3. 使用SDK提供的方法  
    3.1 SpringBoot项目 2.6.0以上版本 注入FlyGoosePrintClient对象  
    其他框架和使用方法待完善中....

----
## 方法概述  
### FlyGoosePrintClient
#### 1. String print(String sn, String content) 打印   
    sn: 打印机编号  
    content: 打印内容
#### 2. String batchAddDevice(List<Device> deviceList) 批量添加设备    
    deviceCode：设备编号（必填） String  
    deviceId：设备ID（必填） String  
    remark：备注 String  
    simCardNo: sim卡号 String  
    bizType: 业务类型 Integer  
    1-打印 2-扫码
#### 3. String batchDeleteDevice(List<String> snList) 批量删除设备
    snList: 设备编号
#### 4. String modifyDevice(String sn, String name, String phoneNum) 修改设备信息
    sn: 设备编号
    name: 设备名称
    phoneNum: 设备电话
#### 5. ApiBaseResponse<DeviceInfo> getDeviceInfo(String sn) 获取设备信息
    sn: 设备编号
响应数据：
```
model: 打印机型号 Integer 0-58小票机 1-80小票机 2-标签机 3-出餐宝 4-一体机
status: 设备状态 Integer 状态状态： 0：离线 1：在线，工作状态正常 2：在线，工作状态不正常 备注：工作状态不正常一般是无纸； 离线一般指打印机与服务器失去联系超过2分钟。  
printlgo: 是否开启自动打印LOGO String N：未开启 Y：已开启
scanSwitch: 扫码设备回调状态 Integer 0-未开启 1-已开启
auto_cut: 是否开启自动切纸 Integer 0-未开启 1-已开启
voice: 是否开启声音 String
v5_scan_voice: 是否开启扫码声音
imsi: 设备的IMSI
net: 设备网络状态
```
#### 6. ApiBaseResponse<String> getDeviceStatus(String sn) 获取设备状态
    sn:设备编号
#### 7. ApiBaseResponse<Boolean> clearPrintQueue(String sn) 清空待打印队列
    sn:设备编号
#### 8. ApiBaseResponse<Boolean> getPrinterStatus(String orderId) 查询订单状态
    orderId:订单编号
#### 9. ApiBaseResponse<OrderInfo> getOrderInfoByDate(String sn, LocalDate localDate) 查询订单数
    sn:设备编号
    localDate: 查询日期
响应数据：
```
print:已打印数量
waiting:待打印数量
```
#### 10. ApiBaseResponse<Boolean> printerSetScanSwitch(String sn, int switchStatus) 设置设备扫码回调
    sn:设备编号
    switchStatus:设备设置扫码路径，0-关闭，1-开启
#### 11. ApiBaseResponse<String> printLabel(String sn, String content, Integer times) 标签打印接口
    sn:设备编号
    content:打印内容
    times:打印份数