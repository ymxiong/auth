package cc.eamon.open.auth.aop.resource;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 6:05 下午
 **/
public enum ResourceRetrieveType {

    /**
     * 流程
     */
    FLOW,

    /**
     * 请求
     */
    REQUEST,

    /**
     * 回调
     */
    CALLBACK,

    /**
     * 环境
     */
    CONTEXT,

    /**
     * 表达式
     */
    EXPRESSION,

    /**
     * 角色
     */
    GROUP;
}
