package cc.eamon.open.error;

import cc.eamon.open.status.StatusException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-06 17:29:42
 */
public class Assert extends org.springframework.util.Assert {

    public static void state(boolean expression, String errorName) {
        if (!expression) {
            throw new StatusException(errorName);
        }
    }

    public static void state(boolean expression, Supplier<String> errorName) {
        if (!expression) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void isTrue(boolean expression, String errorName) {
        if (!expression) {
            throw new StatusException(errorName);
        }
    }

    public static void isTrue(boolean expression, Supplier<String> errorName) {
        if (!expression) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void isNull(@Nullable Object object, String errorName) {
        if (object != null) {
            throw new StatusException(errorName);
        }
    }

    public static void isNull(@Nullable Object object, Supplier<String> errorName) {
        if (object != null) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void notNull(@Nullable Object object, String errorName) {
        if (object == null) {
            throw new StatusException(errorName);
        }
    }

    public static void notNull(@Nullable Object object, Supplier<String> errorName) {
        if (object == null) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void hasLength(@Nullable String text, String errorName) {
        if (!StringUtils.hasLength(text)) {
            throw new StatusException(errorName);
        }
    }

    public static void hasLength(@Nullable String text, Supplier<String> errorName) {
        if (!StringUtils.hasLength(text)) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void hasText(@Nullable String text, String errorName) {
        if (!StringUtils.hasText(text)) {
            throw new StatusException(errorName);
        }
    }

    public static void hasText(@Nullable String text, Supplier<String> errorName) {
        if (!StringUtils.hasText(text)) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, String errorName) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new StatusException(errorName);
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier<String> errorName) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void notEmpty(@Nullable Object[] array, String errorName) {
        if (ObjectUtils.isEmpty(array)) {
            throw new StatusException(errorName);
        }
    }

    public static void notEmpty(@Nullable Object[] array, Supplier<String> errorName) {
        if (ObjectUtils.isEmpty(array)) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void noNullElements(@Nullable Object[] array, String errorName) {
        if (array != null) {
            for(int i = 0; i < array.length; ++i) {
                Object element = array[i];
                if (element == null) {
                    throw new StatusException(errorName);
                }
            }
        }

    }

    public static void noNullElements(@Nullable Object[] array, Supplier<String> errorName) {
        if (array != null) {
            for(int i = 0; i < array.length; ++i) {
                Object element = array[i];
                if (element == null) {
                    throw new StatusException(nullSafeGet(errorName));
                }
            }
        }

    }

    public static void notEmpty(@Nullable Collection<?> collection, String errorName) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new StatusException(errorName);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, Supplier<String> errorName) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, String errorName) {
        if (CollectionUtils.isEmpty(map)) {
            throw new StatusException(errorName);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, Supplier<String> errorName) {
        if (CollectionUtils.isEmpty(map)) {
            throw new StatusException(nullSafeGet(errorName));
        }
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> errorNameSupplier) {
        return errorNameSupplier != null ? errorNameSupplier.get() : null;
    }


}
