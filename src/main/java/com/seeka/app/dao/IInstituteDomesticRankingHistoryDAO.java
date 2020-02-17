package com.seeka.app.dao;

import com.seeka.app.bean.InstituteDomesticRankingHistory;

public interface IInstituteDomesticRankingHistoryDAO {

	void save(InstituteDomesticRankingHistory domesticRanking);

	InstituteDomesticRankingHistory getHistoryOfDomesticRanking(String instituteId);

}
