package JSONResponse;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public class Response {
    private static final Integer SUCCEED_CODE = 200;
    private static final Integer FAILURE_CODE = 400;

    private static final String SUCCEED_MESSAGE = "操作成功";
    private static final String FAILURE_MESSAGE = "操作失败";

    private String message;
    private Integer code;
    private Object data;

    public String JSONString() {
        return JSON.toJSON(this).toString();
    }

    public static Response SucceedResponse(Object data) {
        return new Response(SUCCEED_MESSAGE, SUCCEED_CODE, data);
    }

    public static Response FailedResponse(Object data) {
        return new Response(FAILURE_MESSAGE, FAILURE_CODE, data);
    }

    public static Response FailedResponse() {
        return new Response(FAILURE_MESSAGE, FAILURE_CODE, new HashMap<>());
    }

    private Response(String message, Integer code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
