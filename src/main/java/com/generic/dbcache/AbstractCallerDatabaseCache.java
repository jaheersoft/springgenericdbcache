package com.generic.dbcache;

import java.util.function.BiFunction;

import com.generic.dbcache.exceptions.CacheException;
import com.generic.dbcache.exceptions.CacheException.CacheOperations;
import com.generic.dbcache.exceptions.CacheException.Database;
import com.generic.dbcache.functions.CallerFunction;

public abstract class AbstractCallerDatabaseCache<C> {

	private C caller;
	private Database database;

	public AbstractCallerDatabaseCache(C caller, Database database) {
		this.caller = caller;
		this.database = database;
	}

	public Database getDatabase() {
		return database;
	}

	public C caller() {
		return caller;
	}

	public final <K, V> V decorateSave(K key, V value, C caller, CallerFunction<K, V, C> saveFunction)
			throws CacheException {
		try {
			return saveFunction.apply(key, value, caller);
		} catch (Exception e) {
			throw new CacheException.Builder<K, V>(e).inDatabase(database).whilePerforming(CacheOperations.INSERT)
					.forKey(key).withValue(value).build();
		}
	}

	public final <K, V> V decorateUpdate(K key, V value, C caller, CallerFunction<K, V, C> updateFunction)
			throws CacheException {
		try {
			return updateFunction.apply(key, value, caller);
		} catch (Exception e) {
			throw new CacheException.Builder<K, V>(e).inDatabase(database).whilePerforming(CacheOperations.UPDATE)
					.forKey(key).withValue(value).build();
		}
	}

	public final <K, V> V decorateGet(K key, C caller, BiFunction<K, C, V> getFunction) throws CacheException {
		try {
			return getFunction.apply(key, caller);
		} catch (Exception e) {
			throw new CacheException.Builder<K, V>(e).inDatabase(database).whilePerforming(CacheOperations.GET)
					.forKey(key).build();
		}
	}
}
