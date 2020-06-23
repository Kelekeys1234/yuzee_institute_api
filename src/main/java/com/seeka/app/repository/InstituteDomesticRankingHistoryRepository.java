package com.seeka.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteDomesticRankingHistory;

@Repository
public interface InstituteDomesticRankingHistoryRepository extends JpaRepository<InstituteDomesticRankingHistory, String>{

	public InstituteDomesticRankingHistory findByInstituteId (String instituteId);
	
}
