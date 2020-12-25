package com.generic.dbcache.functions;

@FunctionalInterface
public interface CallerFunction<K, V, C> {
	V apply(K key, V value, C caller);
}
