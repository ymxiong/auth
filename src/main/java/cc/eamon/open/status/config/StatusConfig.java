package cc.eamon.open.status.config;

import java.util.List;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2021-04-07 14:59:51
 */
public class StatusConfig {

    /**
     * 配置类型
     */
    private String type;

    /**
     * 错误列表
     */
    private List<ErrorConfig> errors;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ErrorConfig> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorConfig> errors) {
        this.errors = errors;
    }
}
