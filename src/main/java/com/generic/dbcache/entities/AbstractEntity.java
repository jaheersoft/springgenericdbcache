package com.generic.dbcache.entities;

import java.util.Date;

public abstract class AbstractEntity<K, V> {

	private V value;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String updatedBy;

	public Date getCreatedAt() {
		return createdAt;
	}

	public AbstractEntity<K, V> createdAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public AbstractEntity<K, V> updatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public AbstractEntity<K, V> createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public AbstractEntity<K, V> updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public V getValue() {
		return value;
	}

	public AbstractEntity<K, V> value(V value) {
		this.value = value;
		return this;
	}
}
