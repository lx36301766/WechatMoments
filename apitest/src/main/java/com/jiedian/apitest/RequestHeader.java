package com.jiedian.apitest;

public class RequestHeader {
    public String msg_id;
    public String service;
    public String api;

    public RequestHeader(String service, String api) {
        this.service = service;
        this.api = api;
    }

    public RequestHeader() {
    }
}

