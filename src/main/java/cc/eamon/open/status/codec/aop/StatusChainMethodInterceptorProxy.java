package cc.eamon.open.status.codec.aop;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/1 20:54
 **/
public class StatusChainMethodInterceptorProxy {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        ChainContextHolder.put(StatusConstants.STATUS_CHAIN_RPC_KEY, invocation.getMethod());
        return invocation.proceed();
    }

}

