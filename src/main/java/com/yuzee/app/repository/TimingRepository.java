package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Timing;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;

@Repository
public interface TimingRepository extends MongoRepository<Timing, String> {
	List<Timing> findByEntityTypeAndEntityIdIn(EntityTypeEnum entityType, List<String> entityIds);

	void deleteByEntityTypeAndEntityId(EntityTypeEnum entityType, String entityId);

	Timing findByEntityTypeAndEntityIdAndId(EntityTypeEnum entityType, String entityId, String id);

	void deleteByEntityTypeAndEntityIdAndId(EntityTypeEnum entityType, String entityId, String id);

	void deleteByEntityTypeAndEntityIdIn(EntityTypeEnum entityType, List<String> entityIds);
}
