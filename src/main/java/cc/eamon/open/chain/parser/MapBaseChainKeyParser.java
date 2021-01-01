package cc.eamon.open.chain.parser;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/29 20:48
 **/
public abstract class MapBaseChainKeyParser<K, V> implements ChainKeyParser<Map<K, V>> {

    protected Class K;

    protected Class V;

    public MapBaseChainKeyParser() {
    }

    public MapBaseChainKeyParser(Class k, Class v) {
        this.K = k;
        this.V = v;
    }

    public Class getK() {
        return K;
    }

    public Class getV() {
        return V;
    }

    /**
     * 按照key1:value1;key2:value2格式编码
     *
     * @param chainContext
     * @return
     */
    @Override
    public String encodeChainContext(Map<K, V> chainContext) {
        Map<String, String> map = encodeMapChainContext(chainContext);
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key)
                    .append(":")
                    .append(map.get(key))
                    .append(";");
        }
        return sb.toString();
    }

    /**
     * 按照key1:value1;key2:value2格式解码
     *
     * @param header
     * @return
     */
    @Override
    public Map<K, V> decodeChainContext(String header) {
        String[] keyValues = header.split(";");
        Map<String, String> map = new LinkedHashMap<>();
        for (String keyValue : keyValues) {
            String[] split = keyValue.split(":");
            map.put(split[0], split[1]);
        }
        return decodeMapChainContext(map);
    }

    /**
     * 供自定义任意类型Map转为Map<String, String>，基类只提供Map<String, String>与String的相互转化
     *
     * @param map
     * @return
     */
    public abstract Map<String, String> encodeMapChainContext(Map<K, V> map);

    /**
     * 供自定义任意类型Map<String, String>转为Map，基类只提供Map<String, String>与String的相互转化
     *
     * @param stringMap
     * @return
     */
    public abstract Map<K, V> decodeMapChainContext(Map<String, String> stringMap);
}
