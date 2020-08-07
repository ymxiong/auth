package cc.eamon.open.status;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-06 21:21:31
 */
public class StatusException extends RuntimeException {

    private int code;

    private String message;

    private String detail;

    public StatusException(String errorName) {
        super(StatusCode.getMessage(errorName));
        this.code = StatusCode.getCode(errorName);
        this.message = super.getMessage();
        this.detail = "";
    }

    public StatusException(String errorName, String detail) {
        super(StatusCode.getMessage(errorName));
        this.code = StatusCode.getCode(errorName);
        this.message = super.getMessage();
        this.detail = detail;
    }

    public StatusException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.detail = "";
    }

    public StatusException(int code, String message, String detail) {
        super(message);
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
