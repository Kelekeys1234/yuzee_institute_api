package com.yuzee.app.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteAssociation;
import com.yuzee.app.constant.InstituteAssociationStatus;
import com.yuzee.app.constant.InstituteAssociationType;

@Repository
public interface InstituteAssociationRepository extends MongoRepository<InstituteAssociation, String> {

    @Query("SELECT ia FROM InstituteAssociation ia WHERE (ia.sourceInstituteId = :instituteOne OR ia.destinationInstituteId = :instituteOne) " + 
    		"AND (ia.sourceInstituteId = :instituteTwo or ia.destinationInstituteId = :instituteTwo) and ia.instituteAssociationType = :associationType")
	public InstituteAssociation getInstituteAssociationBasedOnAssociationType(String instituteOne , String instituteTwo, InstituteAssociationType associationType);
	
    @Query("SELECT ia FROM InstituteAssociation ia WHERE (ia.sourceInstituteId = :institute OR ia.destinationInstituteId = :institute) " + 
    		"AND ia.instituteAssociationType = :associationType And ia.instituteAssociationStatus =:status")
	public List<InstituteAssociation> getInstituteAssociationByAssociationType(String institute , InstituteAssociationType associationType, InstituteAssociationStatus status);
}
