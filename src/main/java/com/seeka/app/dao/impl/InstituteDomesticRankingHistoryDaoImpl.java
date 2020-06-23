package com.seeka.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteDomesticRankingHistory;
import com.seeka.app.dao.InstituteDomesticRankingHistoryDAO;
import com.seeka.app.repository.InstituteDomesticRankingHistoryRepository;

@Component
public class InstituteDomesticRankingHistoryDaoImpl implements InstituteDomesticRankingHistoryDAO {

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
