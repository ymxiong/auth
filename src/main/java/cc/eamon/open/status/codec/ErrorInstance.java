package cc.eamon.open.status.codec;

import cc.eamon.open.status.StatusException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/31 15:43
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorInstance {

    String id() default "NO_RECOGNIZE_MSG";

    int statusCode() default 701;

    String message() default "rpc error";

    Class<? extends Exception> decodeException() default StatusException.class;
}
