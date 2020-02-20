package com.jiedian.apitest;

import java.io.Serializable;

/**
 * Created by ycx on 2017/11/15.
 */

public class UpgradeResInfo {

    public HeaderBean header;
    public UpgradeResBodyBean body;
    public String extra;

    public static class HeaderBean {

        public String msg_id;
        public String code;
        public String message;
    }

    @Override
    public String toString() {
        return "UpgradeResInfo{" +
                "header=" + header +
                ", body=" + body +
                ", extra='" + extra + '\'' +
                '}';
    }
}


