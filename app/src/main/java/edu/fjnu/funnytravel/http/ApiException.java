package edu.sqchen.iubao.http;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ApiException extends RuntimeException {

    public static final int USER_CODE_EXIST = 100;

    public static final int WRONG_PASSWORD = 101;

    private static String message;

    public ApiException(int code) {
        this(getApiExceptionMessage(code));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 根据错误码进行异常信息处理
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        switch(code) {
            case USER_CODE_EXIST:
                message = "该用户已存在！";
                break;
            case WRONG_PASSWORD:
                message = "密码错误！";
                break;
            default:
                message = "未知错误！";
                break;
        }
        return message;
    }

}
