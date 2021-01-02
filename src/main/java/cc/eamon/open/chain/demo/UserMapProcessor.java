package cc.eamon.open.chain.demo;

import cc.eamon.open.chain.processor.UserBaseChainKeyProcessor;
import cc.eamon.open.chain.processor.metadata.ChainKeyProcessorMetadata;
import org.springframework.stereotype.Component;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/1/2 1:18
 **/

/**
 * 自定义UserGenericMap<String, Date>的processor
 */
//@Component
//public class UserMapProcessor extends UserBaseChainKeyProcessor {
//
//    static {
//        registry(new ChainKeyProcessorMetadata(ChainConst.CHAIN_USER_MAP, UserMapProcessor.class));
//    }
//
//    @Override
//    public String getUserParserMapTag() {
//        return ChainConst.CHAIN_USER_MAP_PARSER_TAG;
//    }
//
//    @Override
//    public void handle(String key, Object value) {
//        System.out.println(value);
//    }
//
//    @Override
//    public void init() {
//
//    }
//}
