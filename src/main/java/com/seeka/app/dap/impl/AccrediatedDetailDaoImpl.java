package com.seeka.app.dap.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.AccrediatedDetail;
import com.seeka.app.dao.AccrediatedDetailDao;
import com.seeka.app.repository.AccrediatedDetailRepository;

@Component
public class AccrediatedDetailDaoImpl implements AccrediatedDetailDao {

	@Autowired
	private AccrediatedDetailRepository accrediatedDetailRepository;
	
	@Override
	public String addAccrediatedDetail(AccrediatedDetail accrediatedDetail) {
		AccrediatedDetail accrediatedDetailFromDB = accrediatedDetailRepository.save(accrediatedDetail);
		return accrediatedDetailFromDB.getId();
	}

	@Override
	public List<AccrediatedDetail> getAccrediationDetailByEntityId(String entityId) {
		return accrediatedDetailRepository.findByEntityId(entityId);
	}

	@Override
	public void deleteAccrediationDetailByEntityId(String entityId) {
		List<AccrediatedDetail> accrediatedDetailList = accrediatedDetailRepository.findByEntityId(entityId);
		accrediatedDetailList.stream().forEach(accrediatedDetail -> {
			accrediatedDetailRepository.delete(accrediatedDetail);
		});
	}

	@Override
	public AccrediatedDetail getAccrediationBiNameAndEntityId(String accrediationName, String entityId) {
		return accrediatedDetailRepository.findByAccrediatedNameAndEntityId(accrediationName, entityId);
	}
}
