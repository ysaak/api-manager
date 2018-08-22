package ysaak.apimanager.utils;

import java.util.Collection;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }
}
