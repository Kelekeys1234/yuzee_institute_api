package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.InstituteWorldRankingHistory;

public interface InstituteWorldRankingHistoryDao {

	public void save(InstituteWorldRankingHistory worldRanking);

	public List<InstituteWorldRankingHistory> getHistoryOfWorldRanking(String instituteId);

}
