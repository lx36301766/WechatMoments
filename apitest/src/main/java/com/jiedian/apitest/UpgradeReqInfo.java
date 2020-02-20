package com.jiedian.apitest;

public class UpgradeReqInfo {

    public String device_id;
    public String app_version;
    public String firmware_version;

    public UpgradeReqInfo(String device_id, String app_version, String firmware_version) {
        this.device_id = device_id;
        this.app_version = app_version;
        this.firmware_version = firmware_version;
    }
}
