package com.generic.dbcache.functions;

import java.util.Date;
import java.util.function.BiFunction;
import org.springframework.data.cassandra.core.CassandraTemplate;
import com.generic.dbcache.entities.GenericCassandraEntity;
import com.generic.dbcache.repositories.GenericEntityCassandraRepository;
import com.mongodb.annotations.ThreadSafe;

@ThreadSafe
public final class CassandraCacheFunctions {

	private CassandraCacheFunctions() {
	}

	@ThreadSafe
	private static final class SaveIntoCassandraUsingTemplate<K, V> implements CallerFunction<K, V, CassandraTemplate> {
		@Override
		public V apply(K key, V value, CassandraTemplate template) {
			return null;
		}
	}

	@ThreadSafe
	private static final class UpdateWithinCassandraUsingTemplate<K, V>
			implements CallerFunction<K, V, CassandraTemplate> {
		@Override
		public V apply(K key, V value, CassandraTemplate template) {
			return null;
		}
	}

	@ThreadSafe
	private static final class GetFromCassandraUsingTemplate<K, V> implements BiFunction<K, CassandraTemplate, V> {
		@Override
		public V apply(K key, CassandraTemplate template) {
			return null;
		}
	}

	public static <K, V> CallerFunction<K, V, CassandraTemplate> saveIntoCassandraUsingTemplate() {
		return new SaveIntoCassandraUsingTemplate<K, V>();
	}

	public static <K, V> CallerFunction<K, V, CassandraTemplate> updateWithinCassandraUsingTemplate() {
		return new UpdateWithinCassandraUsingTemplate<K, V>();
	}

	public static <K, V> BiFunction<K, CassandraTemplate, V> getFromCassandraUsingTemplate() {
		return new GetFromCassandraUsingTemplate<K, V>();
	}

	@ThreadSafe
	private static final class SaveIntoCassandraUsingRepository<K, V>
			implements CallerFunction<K, V, GenericEntityCassandraRepository<K, V>> {
		@Override
		public V apply(K key, V value, GenericEntityCassandraRepository<K, V> repository) {
			GenericCassandraEntity<K, V> genericCassandraEntity = new GenericCassandraEntity<K, V>().key(key);
			genericCassandraEntity.value(value);
			return repository.insert(genericCassandraEntity).getValue();
		}
	}

	@ThreadSafe
	private static final class UpdateWithinCassandraUsingRepository<K, V>
			implements CallerFunction<K, V, GenericEntityCassandraRepository<K, V>> {
		@Override
		public V apply(K key, V value, GenericEntityCassandraRepository<K, V> repository) {
			if (repository.findById(key).isPresent()) {
				GenericCassandraEntity<K, V> genericCassandraEntity = repository.findById(key).get();
				genericCassandraEntity.value(value);
				genericCassandraEntity.updatedAt(new Date());
				genericCassandraEntity.updatedBy("CLIENT");
				genericCassandraEntity = repository.save(genericCassandraEntity);
				return genericCassandraEntity.getValue();
			}
			return null;
		}
	}

	@ThreadSafe
	private static final class GetFromCasandraUsingRepository<K, V>
			implements BiFunction<K, GenericEntityCassandraRepository<K, V>, V> {
		@Override
		public V apply(K key, GenericEntityCassandraRepository<K, V> repository) {
			return repository.findById(key).isPresent() ? repository.findById(key).get().getValue() : null;
		}
	}

	public static <K, V> CallerFunction<K, V, GenericEntityCassandraRepository<K, V>> saveIntoCassandraUsingRepository() {
		return new SaveIntoCassandraUsingRepository<K, V>();
	}

	public static <K, V> CallerFunction<K, V, GenericEntityCassandraRepository<K, V>> updateWithinCassandraUsingRepository() {
		return new UpdateWithinCassandraUsingRepository<K, V>();
	}

	public static <K, V> BiFunction<K, GenericEntityCassandraRepository<K, V>, V> getFromCassandraUsingRepository() {
		return new GetFromCasandraUsingRepository<K, V>();
	}
}
