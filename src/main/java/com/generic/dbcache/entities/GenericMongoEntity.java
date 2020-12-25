package com.generic.dbcache.entities;

import org.springframework.data.annotation.Id;

public class GenericMongoEntity<K, V> extends AbstractEntity<K, V> {

	@Id
	private K key;

	public K getKey() {
		return key;
	}

	public GenericMongoEntity<K, V> key(K key) {
		this.key = key;
		return this;
	}
}
