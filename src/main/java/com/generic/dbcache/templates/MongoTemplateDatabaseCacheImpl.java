package com.generic.dbcache.templates;

import org.springframework.data.mongodb.core.MongoTemplate;
import com.generic.dbcache.AbstractCallerDatabaseCache;
import com.generic.dbcache.exceptions.CacheException;
import com.generic.dbcache.exceptions.CacheException.Database;
import com.mongodb.annotations.ThreadSafe;
import static com.generic.dbcache.functions.MongoDBCacheFunctions.saveIntoMongoDBUsingTemplate;
import static com.generic.dbcache.functions.MongoDBCacheFunctions.updateWithinMongoDBUsingTemplate;
import static com.generic.dbcache.functions.MongoDBCacheFunctions.getFromMongoDBUsingTemplate;

@ThreadSafe
public final class MongoTemplateDatabaseCacheImpl extends AbstractCallerDatabaseCache<MongoTemplate>
		implements IGenericTemplateDatabaseCache {

	MongoTemplateDatabaseCacheImpl(MongoTemplate caller) {
		super(caller, Database.MONGODB);
	}

	@Override
	public final <K, V> V save(K key, V value) throws CacheException {
		return decorateSave(key, value, caller(), saveIntoMongoDBUsingTemplate());
	}

	@Override
	public final <K, V> V update(K key, V value) throws CacheException {
		return decorateUpdate(key, value, caller(), updateWithinMongoDBUsingTemplate());
	}

	@Override
	public final <K, V> V get(K key) throws CacheException {
		return decorateGet(key, caller(), getFromMongoDBUsingTemplate());
	}
}
