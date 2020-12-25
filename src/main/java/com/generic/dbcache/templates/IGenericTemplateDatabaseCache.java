package com.generic.dbcache.templates;

import com.generic.dbcache.exceptions.CacheException;

public interface IGenericTemplateDatabaseCache {
	
	<K, V> V save(K key, V value) throws CacheException;

	<K, V> V update(K key, V value) throws CacheException;

	<K, V> V get(K key) throws CacheException;
}
