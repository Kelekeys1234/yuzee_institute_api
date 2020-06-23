package com.seeka.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteWorldRankingHistory;

@Repository
public interface InstituteWorldRankingHistoryRepository extends JpaRepository<InstituteWorldRankingHistory, String> {

	public InstituteWorldRankingHistory findByInstituteId(String instituteId);
}
