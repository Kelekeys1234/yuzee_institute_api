package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Timing;
import com.yuzee.app.enumeration.EntityTypeEnum;

public interface TimingDao {

	public List<Timing> saveAll(List<Timing> timings);

	public List<Timing> findByEntityTypeAndEntityId(EntityTypeEnum entityType, String entityId);

	public void deleteAll(List<Timing> timings);

	public void deleteByEntityTypeAndEntityId(EntityTypeEnum entityType, String entityId);
}
