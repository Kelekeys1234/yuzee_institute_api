package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import com.yuzee.app.bean.InstituteAssociation;
import com.yuzee.app.constant.InstituteAssociationStatus;
import com.yuzee.app.constant.InstituteAssociationType;

public interface InstituteAssociationDao {

	public InstituteAssociation addInstituteAssociation (InstituteAssociation instituteAssociation) ;
	
	public InstituteAssociation getInstituteAssociationBasedOnAssociationType (String instituteOne, String instituteTwo , InstituteAssociationType associationType);
	
	public List<InstituteAssociation> getInstituteAssociation (String instituteId , InstituteAssociationType associationType,InstituteAssociationStatus status);
	
	public Optional<InstituteAssociation> getInstituteAssociationById (String instituteAssociationId);
}
