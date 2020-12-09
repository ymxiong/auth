package cc.eamon.open.chain.interceptor;

import cc.eamon.open.chain.ChainContext;
import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.chain.processor.ChainKeyProcessor;
import cc.eamon.open.chain.processor.ChainThreadCounterProcessor;
import cc.eamon.open.error.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/4 20:18
 **/
public abstract class DefaultChainContextRequestInterceptor implements RequestInterceptor{

    private void setThreadCounter() {
        int counter = 1;
        if(ChainContextHolder.get(ChainKeyEnum.THREAD_COUNTER.getKey()) != null)
            counter = Integer.valueOf((String) ChainContextHolder.get(ChainKeyEnum.THREAD_COUNTER)) + 1;
        ChainContextHolder.put(ChainKeyEnum.THREAD_COUNTER,counter + "");
    }

//    @Override
//    public void parseChainContext() {
//        Object v1 = ChainContextHolder.get(ChainKeyEnum.SPAN_ID);
//        if(v1 == null)return;
//        ChainContextHolder.put(ChainKeyEnum.PARENT_ID,v1);
//    }

    @Override
    public boolean checkChainContext() {
//        String spanId = (String)ChainContextHolder.get(ChainKeyEnum.SPAN_ID);
//        if(spanId == null || "".equals(spanId)) return false;
        return true;
    }


    //模板方法
    public void applyChainContextPreHandle(){
        this.setThreadCounter();
        parseChainContext();
        //添加链路数据
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        addChainContext(request);
        if(!checkChainContext()) Assert.notNull(null,"CHAIN_ERROR");
    }

    @Override
    public void applyChainContext() {

    }
}
