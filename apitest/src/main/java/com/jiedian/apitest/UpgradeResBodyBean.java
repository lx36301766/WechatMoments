package com.jiedian.apitest;

import java.io.Serializable;

public
class UpgradeResBodyBean implements Serializable {

    public UpgradeResBodyInfo app;
    public UpgradeResBodyInfo firmware;

    @Override
    public String toString() {
        return "UpgradeResBodyBean{" +
                "app=" + app +
                ", firmware=" + firmware +
                '}';
    }
}
