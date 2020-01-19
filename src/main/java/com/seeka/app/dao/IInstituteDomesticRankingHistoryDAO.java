package com.seeka.app.dao;

import java.math.BigInteger;

import com.seeka.app.bean.InstituteDomesticRankingHistory;

public interface IInstituteDomesticRankingHistoryDAO {

	void save(InstituteDomesticRankingHistory domesticRanking);

	InstituteDomesticRankingHistory getHistoryOfDomesticRanking(BigInteger instituteId);

}
