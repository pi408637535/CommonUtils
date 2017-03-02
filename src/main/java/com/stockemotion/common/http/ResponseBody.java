package com.stockemotion.common.http;

/**
 * Created by zoran on 14-9-15.
 */
public class ResponseBody<T> {

    private String result;


    private T data;

    private Long timestamp;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
