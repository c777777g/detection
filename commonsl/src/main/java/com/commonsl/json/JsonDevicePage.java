package com.commonsl.json;

import com.commonsl.model.Device;

import java.util.List;

public class JsonDevicePage {  String opcode;
    int page;
    int limit;
    int pages;
    long total;
    List<Device> deviceList;

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}