package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteDomesticRankingHistory;

@Repository
public interface InstituteDomesticRankingHistoryRepository extends JpaRepository<InstituteDomesticRankingHistory, String>{

//	@Query(value = "SELECT a FROM InstituteDomesticRankingHistory a LEFT JOIN FETCH a.institute")
	public List<InstituteDomesticRankingHistory> findByInstituteId (String instituteId);
	
}
