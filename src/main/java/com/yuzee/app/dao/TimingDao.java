package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import com.yuzee.app.bean.Timing;
import com.yuzee.app.enumeration.EntityTypeEnum;

public interface TimingDao {

	public List<Timing> saveAll(List<Timing> timings);

	public List<Timing> findByEntityTypeAndEntityIdIn(EntityTypeEnum entityType, List<String> entityId);

	public void deleteAll(List<Timing> timings);

	public void deleteByEntityTypeAndEntityId(EntityTypeEnum entityType, String entityId);

	public Optional<Timing> findById(String id);

	public void deleteById(String timingId);
}
