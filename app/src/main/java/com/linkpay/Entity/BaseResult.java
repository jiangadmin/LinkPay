package com.linkpay.Entity;

public class BaseResult {
    /**
     * resultCode : 0
     */

    private int resultCode;

    public BaseResult() {
        super();
    }

    private String message;
    private String error;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
