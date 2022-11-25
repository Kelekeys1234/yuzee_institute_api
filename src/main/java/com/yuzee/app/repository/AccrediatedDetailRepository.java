package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.AccrediatedDetail;

@Repository
@Component
public interface AccrediatedDetailRepository extends JpaRepository<AccrediatedDetail, String>{

	public List<AccrediatedDetail> findByEntityId (String entityId);
	
	public AccrediatedDetail findByAccrediatedNameAndEntityId (String accrediatedName, String entityId);
	
	public void deleteByEntityId (String entityId);
	
	
}
