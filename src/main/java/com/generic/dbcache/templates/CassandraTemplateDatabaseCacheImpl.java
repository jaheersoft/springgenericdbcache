package com.generic.dbcache.templates;

import org.springframework.data.cassandra.core.CassandraTemplate;
import com.generic.dbcache.AbstractCallerDatabaseCache;
import com.generic.dbcache.exceptions.CacheException;
import com.generic.dbcache.exceptions.CacheException.Database;
import com.mongodb.annotations.ThreadSafe;
import static com.generic.dbcache.functions.CassandraCacheFunctions.saveIntoCassandraUsingTemplate;
import static com.generic.dbcache.functions.CassandraCacheFunctions.updateWithinCassandraUsingTemplate;
import static com.generic.dbcache.functions.CassandraCacheFunctions.getFromCassandraUsingTemplate;

@ThreadSafe
public final class CassandraTemplateDatabaseCacheImpl extends AbstractCallerDatabaseCache<CassandraTemplate>
		implements IGenericTemplateDatabaseCache {

	CassandraTemplateDatabaseCacheImpl(CassandraTemplate caller) {
		super(caller, Database.CASSANDRA);
	}

	@Override
	public final <K, V> V save(K key, V value) throws CacheException {
		return decorateSave(key, value, caller(), saveIntoCassandraUsingTemplate());
	}

	@Override
	public final <K, V> V update(K key, V value) throws CacheException {
		return decorateUpdate(key, value, caller(), updateWithinCassandraUsingTemplate());
	}

	@Override
	public final <K, V> V get(K key) throws CacheException {
		return decorateGet(key, caller(), getFromCassandraUsingTemplate());
	}
}
