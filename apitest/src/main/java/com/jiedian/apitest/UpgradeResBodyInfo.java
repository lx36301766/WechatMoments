package com.jiedian.apitest;

import java.io.Serializable;

public class UpgradeResBodyInfo implements Serializable {

    public String software_version;

    public String url;
    public String md5;
    public String incr_pkg_url;//增量升级增量包地址
    public String incr_md5;//增量升级增量文件md5

    public int upgrade_target;
    public int upgrade_model;
    public int upgrade_type;

    public int upgrade_time;

    public String trigger_time_min;
    public String trigger_time_max;

    /**
     * 刷新数据
     *
     * @param software_version
     * @param url
     * @param md5
     * @param target
     * @param model
     * @param time
     * @param type
     * @param incrPkgUrl
     * @param incrFileMd5sum
     */
    public void refreshData(String software_version, String url, String md5, int target, int model, int time, int type,String incrPkgUrl,String incrFileMd5sum) {
        this.software_version = software_version;
        this.url = url;
        this.md5 = md5;
        this.upgrade_target = target;
        this.upgrade_model = model;
        this.upgrade_time = time;
        this.upgrade_type = type;
        this.incr_pkg_url = incrPkgUrl;
        this.incr_md5 = incrFileMd5sum;
    }

    @Override
    public String toString() {
        return "UpgradeResBodyInfo{" +
                "software_version='" + software_version + '\'' +
                ", url='" + url + '\'' +
                ", md5='" + md5 + '\'' +
                ", incr_pkg_url='" + incr_pkg_url + '\'' +
                ", incr_md5='" + incr_md5 + '\'' +
                ", upgrade_target=" + upgrade_target +
                ", upgrade_model=" + upgrade_model +
                ", upgrade_type=" + upgrade_type +
                ", upgrade_time=" + upgrade_time +
                ", trigger_time_min='" + trigger_time_min + '\'' +
                ", trigger_time_max='" + trigger_time_max + '\'' +
                '}';
    }
}
