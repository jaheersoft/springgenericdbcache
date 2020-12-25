package com.generic.dbcache.repositories;

import com.generic.dbcache.exceptions.CacheException;

public interface IGenericRepositoryDatabaseCache<K,V> {
	
	V save(K key, V value) throws CacheException;
	
	V update(K key, V value) throws CacheException;
	
	V get(K key) throws CacheException;
}
