package cc.eamon.open.chain.interceptor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.DefaultChainKeyParser;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.chain.processor.ChainKeyProcessor;
import cc.eamon.open.error.Assert;
import cc.eamon.open.status.StatusException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

    @Override
    public boolean checkChainContext() {
        //check if begin with CHAIN-
        for (String chainKey : ChainContextHolder.get().keySet()) {
            if(!ChainKeyEnum.isChainKey(chainKey))
                return false;
        }
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

//    //被调用服务调用该方法
//    protected void parseChainContextHandle(String key1, Class type1, String key2, Class type2, ChainKeyParser chainKeyParser, ChainKeyProcessor chainKeyProcessor){
//        this.parseChainContext(key1, type1, key2, type2, chainKeyParser);
//
//    }

    protected void parseChainContext(String key1, Class type1, String key2, Class type2, ChainKeyParser chainKeyParser){
        if(ChainKeyEnum.isDefaultChainKey(key1) || ChainKeyEnum.isDefaultChainKey(key2))
            throw new StatusException(727, "can't parse default CHAIN-KEY");
        Object v1 = ChainContextHolder.get(key1);
        if(v1 == null) Assert.notNull(null, "CHAIN_PARSE_ERROR");
        if(type1.getName().equals(type2.getName()) || (type1 == null && type2 == null)){
            ChainContextHolder.put(key2, v1);
            return;
        }
        ChainKeyParser parser = chainKeyParser;
        if(parser == null) {
            try {
                parser = DefaultChainKeyParser.class.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                Assert.notNull(null, "CHAIN_ERROR");
            }
        }
        String encodeChainContext = parser.encodeChainContext(v1);
        ChainContextHolder.put(key2, parser.decodeChainContext(encodeChainContext));
        ChainContextHolder.get().remove(key1);
    }

    protected void parseChainContext(String key1, String key2, Class type2, ChainKeyParser chainKeyParser){
        Object v1 = ChainContextHolder.get(key1);
        if(v1 == null) Assert.notNull(null, "CHAIN_PARSE_ERROR");
        this.parseChainContext(key1, v1.getClass(), key2, type2, chainKeyParser);
    }

    //同一数据类型换名
    protected void parseChainContext(String key1, String key2){
        this.parseChainContext(key1, null, key2, null, null);
    }

    public abstract void parseChainContext();


}
