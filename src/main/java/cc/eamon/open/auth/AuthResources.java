package cc.eamon.open.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 6:01 下午
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthResources {

    /**
     * 资源集合名称
     *
     * @return 资源集合名称
     */
    String value() default "";

    /**
     * 鉴权规则
     * 用于鉴别分组规则
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

    /**
     * 具体资源
     *
     * @return
     */
    AuthResource[] resources();

}
