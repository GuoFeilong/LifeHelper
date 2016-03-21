package com.effective.idea.factory;

import java.util.HashMap;

/**
 * Created by jsion on 16/3/19.
 */
public class HashMapFactory {
    private HashMapFactory() {

    }

    public static <K, V> HashMap<K, V> newInstance() {
        return new HashMap<K, V>();
    }
}
