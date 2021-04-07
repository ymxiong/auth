package cc.eamon.open.status.config;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2021-04-07 14:59:51
 */
public class ErrorConfig {

    /**
     * 错误id
     */
    private String id;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
