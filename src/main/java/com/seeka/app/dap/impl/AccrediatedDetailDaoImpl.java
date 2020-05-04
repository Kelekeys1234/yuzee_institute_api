package com.seeka.app.dap.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
	public AccrediatedDetail addAccrediatedDetail(AccrediatedDetail accrediatedDetail) {
		return accrediatedDetailRepository.save(accrediatedDetail);
	}

	@Override
	public List<AccrediatedDetail> getAccrediationDetailByEntityId(String entityId) {
		return accrediatedDetailRepository.findByEntityId(entityId);
	}

	@Override
	@Transactional
	public void deleteAccrediationDetailByEntityId(String entityId) {
		accrediatedDetailRepository.deleteByEntityId(entityId);
	}

	@Override
	public AccrediatedDetail getAccrediationBiNameAndEntityId(String accrediationName, String entityId) {
		return accrediatedDetailRepository.findByAccrediatedNameAndEntityId(accrediationName, entityId);
	}

	@Override
	public Optional<AccrediatedDetail> getAccrediatedDetailById(String id) {
		return accrediatedDetailRepository.findById(id);
	}
}
