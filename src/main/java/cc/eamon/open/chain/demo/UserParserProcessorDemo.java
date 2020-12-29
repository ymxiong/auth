package cc.eamon.open.chain.demo;

import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.processor.UserBaseChainKeyProcessor;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/29 22:20
 **/
//具体Parser Processor编写示例
//User为示例类（自定义类） 注意Parser Processor通过getUserMapKey()完成匹配
/*
public class UserParserProcessorDemo extends UserBaseChainKeyProcessor implements ChainKeyParser {
    @Override
    public String encodeChainContext(Object chainContext) {
        return chainContext.toString();
    }

    @Override
    public Object decodeChainContext(String header) {
        return new User(header.substring(0,2),null,null,null,null,null,null);
    }

    @Override
    public String getUserMapKey() {
        return "USER";
    }

    @Override
    public void handle(String key, Object value) {
        System.out.println(value);
    }

    @Override
    public void init() {

    }
}

 */
