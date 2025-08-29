package com.garland.backend_service.model;

import com.google.gson.annotations.SerializedName;

public class ResponseBody {

    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("data")
    private Object data;
    @SerializedName("message")
    private String message;

    public ResponseBody() {
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
