package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Timing;
import com.yuzee.app.dao.TimingDao;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.repository.TimingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class TimingDaoImpl implements TimingDao {

	@Autowired
	private TimingRepository timingRepository;

	@Override
	public List<Timing> saveAll(List<Timing> timings) {
		return timingRepository.saveAll(timings);
	}

	@Override
	public List<Timing> findByEntityTypeAndEntityId(EntityTypeEnum entityType, String entityId) {
		return timingRepository.findByEntityTypeAndEntityId(entityType, entityId);
	}

	@Override
	public void deleteAll(List<Timing> timings) {
		timingRepository.deleteAll(timings);
	}

	@Override
	public void deleteByEntityTypeAndEntityId(EntityTypeEnum entityType, String entityId) {
		timingRepository.deleteByEntityTypeAndEntityId(entityType, entityId);
	}

}
