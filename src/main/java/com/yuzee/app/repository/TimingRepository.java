package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Timing;
import com.yuzee.app.enumeration.EntityTypeEnum;

@Repository
public interface TimingRepository extends JpaRepository<Timing, String> {
	List<Timing> findByEntityTypeAndEntityIdIn(EntityTypeEnum entityType, List<String> entityId);

	void deleteByEntityTypeAndEntityId(EntityTypeEnum entityType, String entityId);

	Timing findByEntityTypeAndEntityIdAndId(EntityTypeEnum entityType, String entityId, String id);
	
	void deleteByEntityTypeAndEntityIdAndId(EntityTypeEnum entityType, String entityId, String id);
}
