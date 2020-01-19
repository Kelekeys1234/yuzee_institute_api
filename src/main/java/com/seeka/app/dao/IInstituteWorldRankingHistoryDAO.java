package com.seeka.app.dao;

import java.math.BigInteger;

import com.seeka.app.bean.InstituteWorldRankingHistory;

public interface IInstituteWorldRankingHistoryDAO {

	void save(InstituteWorldRankingHistory worldRanking);

	InstituteWorldRankingHistory getHistoryOfWorldRanking(BigInteger instituteId);

}
