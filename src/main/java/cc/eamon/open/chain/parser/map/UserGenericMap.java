package cc.eamon.open.chain.parser.map;

import cc.eamon.open.chain.parser.metadata.GenericChainKeyParserMetadata;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/1/1 21:40
 **/
public abstract class UserGenericMap<K, V> extends HashMap<K, V> {

    protected Class<K> K;

    protected Class<V> V;

    public UserGenericMap() {
        //UserGenericMap的子类调用该构造器时才能赋值，抛弃这种方式
//        Type[] typeArguments = (((ParameterizedType) getClass().getGenericSuperclass())).getActualTypeArguments();
//        this.K = (Class<K>) typeArguments[0];
//        this.V = (Class<V>) typeArguments[1];
        this.K = genericMetadata().getPara1();
        this.V = genericMetadata().getPara2();
    }

    public Class getK() {
        return K;
    }

    public Class getV() {
        return V;
    }

    protected abstract GenericChainKeyParserMetadata.GenericMetadata genericMetadata();
}
