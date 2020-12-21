package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.dto.ScholarshipLevelCountDto;

@Repository
public interface ScholarshipRepository
		extends JpaRepository<Scholarship, String>, JpaSpecificationExecutor<Scholarship> {

	public Long countByInstituteId(String instituteId);

	@Query("SELECT new com.yuzee.app.dto.ScholarshipLevelCountDto(l.id, l.code, l.name, COUNT(s.id)) "
			+ "from Scholarship s join s.level l group by s.level.id")
	public List<ScholarshipLevelCountDto> getScholarshipCountGroupByLevel();
}
