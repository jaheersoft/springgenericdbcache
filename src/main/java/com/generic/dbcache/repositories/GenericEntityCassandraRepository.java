package com.generic.dbcache.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.generic.dbcache.entities.GenericCassandraEntity;

@NoRepositoryBean
public interface GenericEntityCassandraRepository<K,V> extends CassandraRepository<GenericCassandraEntity<K, V>, K>{

}
