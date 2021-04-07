package cc.eamon.open.status;

import cc.eamon.easyfile.FileTools;
import cc.eamon.open.status.config.StatusConfig;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.util.HashMap;

public class StatusCode {


    private static Logger logger = LoggerFactory.getLogger(StatusCode.class);

    /**
     * 请求成功
     */
    public static int SUCCESS = 200;

    /**
     * 简单请求成功消息
     */
    public static String SUCCESS_MSG = "请求成功";

    /**
     * 请求失败
     */
    public static int FAILED = 700;
    /**
     * 简单请求失败消息
     */
    public static String FAILED_MSG = "请求失败";

    /**
     * 权限不足
     */
    public static int NO_AUTH = 401;

    /**
     * 简单权限不足消息
     */
    public static String NO_AUTH_MSG = "权限不足";

    /**
     * 错误未识别
     */
    public static int NO_RECOGNIZE = 1000;
    /**
     * 错误未识别消息
     */
    public static String NO_RECOGNIZE_MSG = "错误未识别";

    private int code;

    private String msg;

    //拒绝实例化，保护结构
    private StatusCode() {
    }

    /**
     * 通过名称查找
     */
    private static HashMap<String, StatusCode> errorNameToCodeMap = new HashMap<>();

    /**
     * 通过码查找
     */
    private static HashMap<Integer, StatusCode> codeToCodeMap = new HashMap<>();

    static {
        Resource resource = new ClassPathResource("status.json");
        try {
            String statusJsonString = FileTools.inputStream2Str(resource.getInputStream());
            StatusConfig statusConfig = JSONObject.parseObject(statusJsonString, StatusConfig.class);
            Assert.isTrue("status".equals(statusConfig.getType()), "status.json 加载错误(type is not status)");
            statusConfig.getErrors().forEach((error) -> {
                StatusCode statusCode = new StatusCode();
                String id = error.getId();
                statusCode.code = error.getCode();
                statusCode.msg = error.getMessage();
                switch (id) {
                    case "SUCCESS":
                        SUCCESS = statusCode.code;
                        SUCCESS_MSG = statusCode.msg;
                        break;
                    case "FAILED":
                        FAILED = statusCode.code;
                        FAILED_MSG = statusCode.msg;
                        break;
                    case "NO_AUTH":
                        NO_AUTH = statusCode.code;
                        NO_AUTH_MSG = statusCode.msg;
                    case "NO_RECOGNIZE":
                        NO_RECOGNIZE = statusCode.code;
                        NO_RECOGNIZE_MSG = statusCode.msg;
                        break;
                }
                errorNameToCodeMap.put(id, statusCode);
                codeToCodeMap.put(statusCode.code, statusCode);
                Assert.isTrue(errorNameToCodeMap.size() == codeToCodeMap.size(), "status.json 加载错误(codes do not match error)");
            });
        } catch (Exception e) {
            logger.error("status.json 加载错误");
        }
    }

    public static Integer getCode(String errorName) {
        StatusCode statusCode = errorNameToCodeMap.get(errorName);
        if (statusCode == null) {
            return NO_RECOGNIZE;
        } else {
            return statusCode.code;
        }
    }

    public static String getMessage(String errorName) {
        StatusCode statusCode = errorNameToCodeMap.get(errorName);
        if (statusCode == null) {
            return NO_RECOGNIZE_MSG;
        } else {
            return statusCode.msg;
        }
    }

    public static Integer getCode(Integer code) {
        StatusCode statusCode = codeToCodeMap.get(code);
        if (statusCode == null) {
            return NO_RECOGNIZE;
        } else {
            return statusCode.code;
        }
    }

    public static String getMessage(Integer code) {
        StatusCode statusCode = codeToCodeMap.get(code);
        if (statusCode == null) {
            return NO_RECOGNIZE_MSG;
        } else {
            return statusCode.msg;
        }
    }

}