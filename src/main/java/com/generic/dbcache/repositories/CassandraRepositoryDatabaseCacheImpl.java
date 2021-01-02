package com.generic.dbcache.repositories;

import com.generic.dbcache.exceptions.CacheException;
import com.generic.dbcache.exceptions.CacheException.Database;
import com.mongodb.annotations.ThreadSafe;
import static com.generic.dbcache.functions.CassandraCacheFunctions.saveIntoCassandraUsingRepository;
import static com.generic.dbcache.functions.CassandraCacheFunctions.updateWithinCassandraUsingRepository;
import com.generic.dbcache.AbstractCallerDatabaseCache;
import static com.generic.dbcache.functions.CassandraCacheFunctions.getFromCassandraUsingRepository;

@ThreadSafe
public final class CassandraRepositoryDatabaseCacheImpl<K, V>
		extends AbstractCallerDatabaseCache<GenericEntityCassandraRepository<K, V>>
		implements IGenericRepositoryDatabaseCache<K, V> {

	public CassandraRepositoryDatabaseCacheImpl(GenericEntityCassandraRepository<K, V> repository) {
		super(repository, Database.CASSANDRA);
	}

	@Override
	public final V save(K key, V value) throws CacheException {
		return decorateSave(key, value, caller(), saveIntoCassandraUsingRepository());
	}

	@Override
	public final V update(K key, V value) throws CacheException {
		return decorateUpdate(key, value, caller(), updateWithinCassandraUsingRepository());
	}

	@Override
	public final V get(K key) throws CacheException {
		return decorateGet(key, caller(), getFromCassandraUsingRepository());
	}
}
