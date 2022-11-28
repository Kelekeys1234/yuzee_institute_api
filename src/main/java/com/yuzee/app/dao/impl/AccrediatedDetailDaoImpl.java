package com.yuzee.app.dao.impl;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.AccrediatedDetail;
import com.yuzee.app.dao.AccrediatedDetailDao;
import com.yuzee.app.repository.AccrediatedDetailRepository;

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
	@org.springframework.transaction.annotation.Transactional
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

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void deleteAccrediationDetailById(String id) {
		accrediatedDetailRepository.deleteById(id);
	}

	@Override
	public List<AccrediatedDetail> getAllAccrediationDetails() {
		return accrediatedDetailRepository.findAll();
	}
}
