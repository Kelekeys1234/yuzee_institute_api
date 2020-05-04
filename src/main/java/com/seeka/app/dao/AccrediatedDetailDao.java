package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.AccrediatedDetail;

public interface AccrediatedDetailDao {

	public String addAccrediatedDetail (AccrediatedDetail accrediatedDetail);
	
	public List<AccrediatedDetail> getAccrediationDetailByEntityId (String entityId);
	
	public void deleteAccrediationDetailByEntityId (String entityId);
	
	public AccrediatedDetail getAccrediationBiNameAndEntityId (String accrediationName, String entityId);
	
}
