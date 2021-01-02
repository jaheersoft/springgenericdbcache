package com.generic.dbcache.repositories;

import com.generic.dbcache.exceptions.CacheException;
import com.generic.dbcache.exceptions.CacheException.Database;
import com.mongodb.annotations.ThreadSafe;
import com.generic.dbcache.AbstractCallerDatabaseCache;
import static com.generic.dbcache.functions.MongoDBCacheFunctions.saveIntoMongoDBUsingRepository;
import static com.generic.dbcache.functions.MongoDBCacheFunctions.updateWithinMongoDBUsingRepository;
import static com.generic.dbcache.functions.MongoDBCacheFunctions.getFromMongoDBUsingRepository;

@ThreadSafe
public final class MongoRepositoryDatabaseCacheImpl<K, V>
		extends AbstractCallerDatabaseCache<GenericEntityMongoRepository<K, V>>
		implements IGenericRepositoryDatabaseCache<K, V> {

	public MongoRepositoryDatabaseCacheImpl(GenericEntityMongoRepository<K, V> repository) {
		super(repository, Database.MONGODB);
	}

	@Override
	public final V save(K key, V value) throws CacheException {
		return decorateSave(key, value, caller(), saveIntoMongoDBUsingRepository());
	}

	@Override
	public final V update(K key, V value) throws CacheException {
		return decorateUpdate(key, value, caller(), updateWithinMongoDBUsingRepository());
	}

	@Override
	public final V get(K key) throws CacheException {
		return decorateGet(key, caller(), getFromMongoDBUsingRepository());
	}
}
