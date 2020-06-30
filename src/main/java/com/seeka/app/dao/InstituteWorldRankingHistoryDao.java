package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteWorldRankingHistory;

public interface InstituteWorldRankingHistoryDao {

	public void save(InstituteWorldRankingHistory worldRanking);

	public List<InstituteWorldRankingHistory> getHistoryOfWorldRanking(String instituteId);

}
