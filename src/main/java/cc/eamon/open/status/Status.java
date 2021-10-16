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

    public Status(boolean result, int status, String message, String path, Object data, Object extra) {
        this.result = result;
        this.status = status;
        this.message = message;
        this.path = path;
        this.data = data;
        this.extra = extra;
    }

    public Map<String, Object> map() {
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

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(boolean result, int status, String message) {
        return new Builder(result, status, message);
    }

    public static Builder builder(boolean result, int status, String message, Object dataValue, Long totalCount, Object extra) {
        return new Builder(result, status, message).addDataValue(dataValue).addDataCount(totalCount);
    }

    public static Builder successBuilder() {
        return new Builder(true, StatusCode.getCode(200), StatusCode.getMessage(200));
    }

    public static Builder successFailedInnerBuilder(String message) {
        return new Builder(true, StatusCode.getCode(200), message);
    }


    public static Builder failedBuilder() {
        return new Builder(false, StatusCode.getCode(700), StatusCode.getMessage(700));
    }

    public static Builder failedBuilder(int code) {
        return new Builder(false, StatusCode.getCode(code), StatusCode.getMessage(code));
    }

    public static Builder failedBuilder(String errorName) {
        return new Builder(false, StatusCode.getCode(errorName), StatusCode.getMessage(errorName));
    }

    public static Builder failedBuilder(StatusException statusException) {
        return new Builder(false, statusException.getCode(), statusException.getMessage());
    }

    public static class Builder {

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
         * 请求路径
         */
        private String path;

        /**
         * 需要让用户知道的数据
         */
        private Map<String, Object> data = new LinkedHashMap<>();

        /**
         * 加密验证数据（保护内部逻辑）
         */
        private Object extra;


        private Builder() {
        }

        private Builder(boolean result, int status, String message) {
            this.result = result;
            this.status = status;
            this.message = message;
        }

        private Builder(boolean result, int status, String message, Map<String, Object> data, Object extra) {
            this.result = result;
            this.status = status;
            this.message = message;
            this.data = data;
            this.extra = extra;
        }

        public Builder addResult(boolean result) {
            this.result = result;
            return this;
        }

        public Builder addStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder addMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder addPath(String path) {
            this.path = path;
            return this;
        }

        public Builder addData(String key, Object data) {
            this.data.put(key, data);
            return this;
        }

        public Builder addDataValue(Object data) {
            return addData("value", data);
        }

        public Builder addDataCount(Long count) {
            return addData("count", count);
        }

        public Builder addExtra(Object extra) {
            this.extra = extra;
            return this;
        }

        public Status build() {
            return new Status(result, status, message, path, data, extra);
        }

        public Map<String, Object> map() {
            return new Status(result, status, message, path, data, extra).map();
        }

    }
}
