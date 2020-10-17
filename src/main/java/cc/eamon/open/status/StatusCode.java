package cc.eamon.open.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

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
        Properties prop = new Properties();
        Resource resource = new ClassPathResource("status.properties");
        try {
            prop.load(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            prop.stringPropertyNames().forEach((e) -> {
                String cm[] = prop.getProperty(e).split("::");
                Assert.isTrue(cm.length >= 2, "status.properties 加载错误");

                StatusCode code = new StatusCode();
                code.code = Integer.parseInt(cm[0].trim());
                code.msg = cm[1].trim();

                switch (e) {
                    case "SUCCESS":
                        SUCCESS = code.code;
                        SUCCESS_MSG = code.msg;
                        break;
                    case "FAILED":
                        FAILED = code.code;
                        FAILED_MSG = code.msg;
                        break;
                    case "NO_AUTH":
                        NO_AUTH = code.code;
                        NO_AUTH_MSG = code.msg;
                    case "NO_RECOGNIZE":
                        NO_RECOGNIZE = code.code;
                        NO_RECOGNIZE_MSG = code.msg;
                        break;
                }
                errorNameToCodeMap.put(e, code);
                codeToCodeMap.put(code.code, code);
                Assert.isTrue(errorNameToCodeMap.size() == codeToCodeMap.size(), "status.properties 加载错误");
            });


        } catch (Exception e) {
            logger.error("status.properties 加载错误");
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