package cc.eamon.open.auth.advice.strategy;

import org.springframework.util.StringUtils;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2021-03-01 18:05:21
 */
public enum ContextValueStrategyEnums {

    STRATEGY_HEADER("STRATEGY_HEADER", new HeaderContextValueStrategy()),

    STRATEGY_COOKIE("STRATEGY_COOKIE", new CookieContextValueStrategy()),

    STRATEGY_CHAIN("STRATEGY_CHAIN", new ChainContextValueStrategy());

    private String name;

    private ContextValueStrategy strategy;

    ContextValueStrategyEnums(String name, ContextValueStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public ContextValueStrategy getStrategy() {
        return strategy;
    }

    public static ContextValueStrategyEnums getContextValueStrategy(String valueName) {
        if (StringUtils.isEmpty(valueName)) return STRATEGY_HEADER;
        for (ContextValueStrategyEnums value : ContextValueStrategyEnums.values()) {
            if (valueName.contains(value.getStrategy().identifier() + "$")) {
                return value;
            }
        }
        return STRATEGY_HEADER;
    }


}
