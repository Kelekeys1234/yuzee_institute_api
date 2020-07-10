package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.InstituteAssociation;
import com.yuzee.app.constant.InstituteAssociationStatus;
import com.yuzee.app.constant.InstituteAssociationType;
import com.yuzee.app.dao.InstituteAssociationDao;
import com.yuzee.app.repository.InstituteAssociationRepository;

@Component
public class InstituteAssociationDaoImpl implements InstituteAssociationDao {
	
	@Autowired
	private InstituteAssociationRepository instituteAssociationRepository;

	@Override
	public InstituteAssociation addInstituteAssociation(InstituteAssociation instituteAssociation) {
		return instituteAssociationRepository.save(instituteAssociation);
	}

	@Override
	public InstituteAssociation getInstituteAssociationBasedOnAssociationType(String instituteOne, String instituteTwo,
			InstituteAssociationType associationType) {
		return instituteAssociationRepository.getInstituteAssociationBasedOnAssociationType(instituteOne, instituteTwo, associationType);
	}

	@Override
	public List<InstituteAssociation> getInstituteAssociation(String instituteId, InstituteAssociationType associationType, InstituteAssociationStatus status) {
		return instituteAssociationRepository.getInstituteAssociationByAssociationType(instituteId,associationType,status);
	}

	@Override
	public Optional<InstituteAssociation> getInstituteAssociationById(String instituteAssociationId) {
		return instituteAssociationRepository.findById(instituteAssociationId);
	}

}
