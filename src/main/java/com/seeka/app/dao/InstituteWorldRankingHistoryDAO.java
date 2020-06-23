package com.seeka.app.dao;

import com.seeka.app.bean.InstituteWorldRankingHistory;

public interface InstituteWorldRankingHistoryDAO {

	public void save(InstituteWorldRankingHistory worldRanking);

	public InstituteWorldRankingHistory getHistoryOfWorldRanking(String instituteId);

}
