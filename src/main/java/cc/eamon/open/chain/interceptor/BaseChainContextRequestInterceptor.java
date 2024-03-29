package cc.eamon.open.chain.interceptor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.DefaultChainKeyParser;
import cc.eamon.open.chain.processor.ChainKeyEnum;
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
public abstract class BaseChainContextRequestInterceptor implements RequestInterceptor {

    /**
     * 模板方法
     * 1. 设置线程counter
     * 2. 应用chainContext
     * 3. 转换chainContext
     * 4. 添加chainContext
     * 5. 校验chainContext
     */
    public void applyChainContextPreProcess() {
        this.setThreadCounter();
        this.applyChainContext();
        parseChainContext();
        //添加链路数据
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        addChainContext(request);
        if (!checkChainContext()) throw new StatusException("CHAIN_ERROR");
    }

    @Override
    public boolean checkChainContext() {
        //check if begin with CHAIN-
        for (String chainKey : ChainContextHolder.get().keySet()) {
            if (!ChainKeyEnum.isChainKey(chainKey)) {
                // TODO: 2021/11/24 NEED FIX
            }
//                return false;
        }
        return true;
    }


    @Override
    public void applyChainContext() {

    }


    protected void parseChainContext(String chainKey1, Class type1, String chainKey2, Class type2, ChainKeyParser chainKeyParser) {
        if (ChainKeyEnum.isDefaultChainKey(chainKey1) || ChainKeyEnum.isDefaultChainKey(chainKey2))
            throw new StatusException(727, "can't parse default CHAIN-KEY");
        Object v1 = ChainContextHolder.get(chainKey1);
        if (v1 == null) Assert.notNull(null, "CHAIN_PARSE_ERROR");
        if (type1.getName().equals(type2.getName()) || (type1 == null && type2 == null)) {
            ChainContextHolder.put(chainKey2, v1);
            return;
        }
        ChainKeyParser parser = chainKeyParser;
        if (parser == null)
            parser = new DefaultChainKeyParser();
        String encodeChainContext = parser.encodeChainContext(v1);
        ChainContextHolder.put(chainKey2, parser.decodeChainContext(encodeChainContext));
        ChainContextHolder.get().remove(chainKey1);
    }

    protected void parseChainContext(String chainKey1, String chainKey2, Class type2, ChainKeyParser chainKeyParser) {
        Object v1 = ChainContextHolder.get(chainKey1);
        if (v1 == null) Assert.notNull(null, "CHAIN_PARSE_ERROR");
        this.parseChainContext(chainKey1, v1.getClass(), chainKey2, type2, chainKeyParser);
    }

    /**
     * 同一数据类型换名
     *
     * @param chainKey1
     * @param chainKey2
     */
    protected void parseChainContext(String chainKey1, String chainKey2) {
        this.parseChainContext(chainKey1, null, chainKey2, null, null);
    }

    public abstract void parseChainContext();

    private void setThreadCounter() {
        ChainContextHolder.put(ChainKeyEnum.THREAD_COUNTER, ((Integer) ChainContextHolder.get(ChainKeyEnum.THREAD_COUNTER)) + 1);
    }


}
