package com.generic.dbcache.functions;

import java.util.Date;
import java.util.function.BiFunction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.generic.dbcache.entities.GenericMongoEntity;
import com.generic.dbcache.repositories.GenericEntityMongoRepository;
import com.mongodb.annotations.ThreadSafe;

@ThreadSafe
public final class MongoDBCacheFunctions {

	private MongoDBCacheFunctions() {
	}

	@ThreadSafe
	private static final class SaveIntoMongoDBUsingTemplate<K, V> implements CallerFunction<K, V, MongoTemplate> {
		@Override
		public V apply(K key, V value, MongoTemplate template) {
			return template.insert(new GenericMongoEntity<K, V>().key(key).value(value).createdAt(new Date()))
					.getValue();
		}
	}

	@ThreadSafe
	private static final class UpdateWithinMongoDBUsingTemplate<K, V> implements CallerFunction<K, V, MongoTemplate> {
		@SuppressWarnings("unchecked")
		@Override
		public V apply(K key, V value, MongoTemplate template) {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(key));
			Update update = new Update();
			update.set("value", value);
			return (V) template.findAndModify(query, update, GenericMongoEntity.class).getValue();
		}
	}

	@ThreadSafe
	private static final class GetFromMongoDBUsingTemplate<K, V> implements BiFunction<K, MongoTemplate, V> {
		@SuppressWarnings("unchecked")
		@Override
		public V apply(K key, MongoTemplate template) {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(key));
			return (V) template.findOne(query, GenericMongoEntity.class).getValue();
		}
	}

	public static <K, V> CallerFunction<K, V, MongoTemplate> saveIntoMongoDBUsingTemplate() {
		return new SaveIntoMongoDBUsingTemplate<K, V>();
	}

	public static <K, V> CallerFunction<K, V, MongoTemplate> updateWithinMongoDBUsingTemplate() {
		return new UpdateWithinMongoDBUsingTemplate<K, V>();
	}

	public static <K, V> BiFunction<K, MongoTemplate, V> getFromMongoDBUsingTemplate() {
		return new GetFromMongoDBUsingTemplate<K, V>();
	}

	@ThreadSafe
	private static final class SaveIntoMongoDBUsingRepository<K, V>
			implements CallerFunction<K, V, GenericEntityMongoRepository<K, V>> {
		@Override
		public V apply(K key, V value, GenericEntityMongoRepository<K, V> repository) {
			GenericMongoEntity<K, V> genericMongoEntity = new GenericMongoEntity<K, V>().key(key);
			genericMongoEntity.value(value);
			return repository.insert(genericMongoEntity).getValue();
		}
	}

	@ThreadSafe
	private static final class UpdateWithinMongoDBUsingRepository<K, V>
			implements CallerFunction<K, V, GenericEntityMongoRepository<K, V>> {
		@Override
		public V apply(K key, V value, GenericEntityMongoRepository<K, V> repository) {
			if (repository.findById(key).isPresent()) {
				GenericMongoEntity<K, V> genericMongoEntity = repository.findById(key).get();
				genericMongoEntity.value(value);
				genericMongoEntity.updatedAt(new Date());
				genericMongoEntity.updatedBy("CLIENT");
				genericMongoEntity = repository.save(genericMongoEntity);
				return genericMongoEntity.getValue();
			}
			return null;
		}
	}

	@ThreadSafe
	private static final class GetFromMongoDBUsingRepository<K, V>
			implements BiFunction<K, GenericEntityMongoRepository<K, V>, V> {
		@Override
		public V apply(K key, GenericEntityMongoRepository<K, V> repository) {
			return repository.findById(key).isPresent() ? repository.findById(key).get().getValue() : null;
		}
	}

	public static <K, V> CallerFunction<K, V, GenericEntityMongoRepository<K, V>> saveIntoMongoDBUsingRepository() {
		return new SaveIntoMongoDBUsingRepository<K, V>();
	}

	public static <K, V> CallerFunction<K, V, GenericEntityMongoRepository<K, V>> updateWithinMongoDBUsingRepository() {
		return new UpdateWithinMongoDBUsingRepository<K, V>();
	}

	public static <K, V> BiFunction<K, GenericEntityMongoRepository<K, V>, V> getFromMongoDBUsingRepository() {
		return new GetFromMongoDBUsingRepository<K, V>();
	}

}
