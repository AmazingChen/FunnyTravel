package edu.sqchen.iubao.model.entity;

/**
 * Created by Administrator on 2017/4/22.
 */

public class ResultData {

    private int error_code;

    private String reason;

    private Object result;

    public ResultData() {

    }

    public ResultData(int error_code, String reason, Object result) {
        this.error_code = error_code;
        this.reason = reason;
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
