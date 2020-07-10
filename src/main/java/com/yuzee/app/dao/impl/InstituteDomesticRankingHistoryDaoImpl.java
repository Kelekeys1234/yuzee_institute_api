package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.InstituteDomesticRankingHistory;
import com.yuzee.app.dao.InstituteDomesticRankingHistoryDao;
import com.yuzee.app.repository.InstituteDomesticRankingHistoryRepository;

@Component
public class InstituteDomesticRankingHistoryDaoImpl implements InstituteDomesticRankingHistoryDao {

	@Autowired
	private InstituteDomesticRankingHistoryRepository instituteDomesticRankingHistoryRepository;

	@Override
	public void save(final InstituteDomesticRankingHistory domesticRanking) {
		instituteDomesticRankingHistoryRepository.save(domesticRanking);
	}

	@Override
	public List<InstituteDomesticRankingHistory> getHistoryOfDomesticRanking(final String instituteId) {
		return instituteDomesticRankingHistoryRepository.findByInstituteId(instituteId);
	}

}
