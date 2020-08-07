package cc.eamon.open.status;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Status {

    /**
     * 状态值
     */
    private boolean result;

    /**
     * 状态码
     */
    private int status;

    /**
     * 消息
     */
    private String message;

    /**
     * 服务器时间戳
     */
    private Long timestamp = new Date().getTime();

    /**
     * 请求路径
     */
    private String path;

    /**
     * 需要让用户知道的数据
     */
    private Object data;

    /**
     * 加密验证数据（保护内部逻辑）
     */
    private Object extra;


    private Status() {
    }

    private Status(boolean result, int status, String message) {
        this.result = result;
        this.status = status;
        this.message = message;
    }

    private Status(boolean result, int status, String message, Object data, Object extra) {
        this.result = result;
        this.status = status;
        this.message = message;
        this.data = data;
        this.extra = extra;
    }

    public static Status buildSuccessStatus() {
        return new Status(
                true,
                StatusCode.getCode(200),
                StatusCode.getMessage(200));
    }

    public static Status buildFailedStatus() {
        return new Status(
                true,
                StatusCode.getCode(700),
                StatusCode.getMessage(700));
    }

    public static Status buildFailedStatus(int code) {
        return new Status(
                true,
                StatusCode.getCode(code),
                StatusCode.getMessage(code)
        );
    }

    public static Status buildFailedStatus(String errorName) {
        return new Status(
                true,
                StatusCode.getCode(errorName),
                StatusCode.getMessage(errorName)
        );
    }

    public static Status buildStatus(boolean result, int status, String message) {
        return new Status(result, status, message);
    }

    public static Status buildStatus(boolean result, int status, String message, Object dataValue, Integer totalCount, Object extra) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("value", dataValue);
        if (totalCount != null) data.put("count", totalCount);
        return new Status(result, status, message, data, extra);
    }

    public Map<String, Object> buildMap() {
        Map<String, Object> statusMap = new LinkedHashMap<>();
        statusMap.put("result", getResult());
        statusMap.put("status", getStatus());
        statusMap.put("message", getMessage());
        statusMap.put("timestamp", getTimestamp());
        if (getPath() != null) statusMap.put("path", getPath());
        if (getData() != null) statusMap.put("data", getData());
        if (getExtra() != null) statusMap.put("extra", getExtra());
        return statusMap;
    }


    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

}
