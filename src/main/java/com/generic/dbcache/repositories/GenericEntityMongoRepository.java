package com.generic.dbcache.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.generic.dbcache.entities.GenericMongoEntity;

@NoRepositoryBean
public interface GenericEntityMongoRepository<K,V> extends MongoRepository<GenericMongoEntity<K, V>, K>{

}
