package com.seeka.app.dao;

import com.seeka.app.bean.InstituteWorldRankingHistory;

public interface IInstituteWorldRankingHistoryDAO {

	void save(InstituteWorldRankingHistory worldRanking);

	InstituteWorldRankingHistory getHistoryOfWorldRanking(String instituteId);

}
