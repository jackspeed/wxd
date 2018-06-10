package com.js.wxd.api.entity;

public class SocketResult {

    private String url;
    private boolean refresh;

    public SocketResult() {
    }

    public SocketResult(String url, boolean refresh) {
        this.url = url;
        this.refresh = refresh;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }
}
