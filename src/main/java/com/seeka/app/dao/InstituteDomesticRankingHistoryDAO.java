package com.seeka.app.dao;

import com.seeka.app.bean.InstituteDomesticRankingHistory;

public interface InstituteDomesticRankingHistoryDAO {

	public void save(InstituteDomesticRankingHistory domesticRanking);

	public InstituteDomesticRankingHistory getHistoryOfDomesticRanking(String instituteId);

}
