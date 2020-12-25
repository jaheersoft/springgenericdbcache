package com.generic.dbcache.entities;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class GenericCassandraEntity<K, V> extends AbstractEntity<K, V> {

	@PrimaryKey
	private K key;

	public K getKey() {
		return key;
	}

	public GenericCassandraEntity<K, V> key(K key) {
		this.key = key;
		return this;
	}
}