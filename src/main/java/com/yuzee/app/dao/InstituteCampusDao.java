package com.yuzee.app.dao;

import java.util.List;
import java.util.UUID;

import com.yuzee.app.bean.InstituteCampus;

public interface InstituteCampusDao {
	List<InstituteCampus> saveAll(List<InstituteCampus> entities);

	List<InstituteCampus> findInstituteCampuses(UUID instituteId);

	void deleteAll(List<InstituteCampus> courseInstitutes);
}