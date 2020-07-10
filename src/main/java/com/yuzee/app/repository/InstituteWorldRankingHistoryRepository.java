package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteWorldRankingHistory;

@Repository
public interface InstituteWorldRankingHistoryRepository extends JpaRepository<InstituteWorldRankingHistory, String> {

	public List<InstituteWorldRankingHistory> findByInstituteId(String instituteId);
}
