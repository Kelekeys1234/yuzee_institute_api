package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteDomesticRankingHistory;

public interface InstituteDomesticRankingHistoryDAO {

	public void save(InstituteDomesticRankingHistory domesticRanking);

	public List<InstituteDomesticRankingHistory> getHistoryOfDomesticRanking(String instituteId);

}
