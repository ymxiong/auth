package cc.eamon.open.chain.processor;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/23 22:45
 **/
public abstract class UserBaseChainKeyProcessor extends BaseChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return null;
    }

    @Override
    public String mapKey() {
        return getUserMapKey();
    }

    /**
     * 用户自定义MapKey匹配Parser
     * @return
     */
    public abstract String getUserMapKey();
}
