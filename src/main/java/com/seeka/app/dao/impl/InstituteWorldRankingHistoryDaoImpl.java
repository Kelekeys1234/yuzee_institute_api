package com.seeka.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.dao.InstituteWorldRankingHistoryDao;
import com.seeka.app.repository.InstituteWorldRankingHistoryRepository;

@Component
public class InstituteWorldRankingHistoryDaoImpl implements InstituteWorldRankingHistoryDao {

	@Autowired
	private InstituteWorldRankingHistoryRepository instituteWorldRankingHistoryRepository;

	@Override
	public void save(final InstituteWorldRankingHistory worldRanking) {
		instituteWorldRankingHistoryRepository.save(worldRanking);
	}

	@Override
	public List<InstituteWorldRankingHistory> getHistoryOfWorldRanking(final String instituteId) {
		return instituteWorldRankingHistoryRepository.findByInstituteId(instituteId);
	}

}
