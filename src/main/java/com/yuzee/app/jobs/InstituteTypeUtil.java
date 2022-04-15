package com.yuzee.app.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.InstituteType;

@Component
public class InstituteTypeUtil implements InitializingBean {

	@Autowired
	InstituteTypeDao instituteTypeDao;

	private static List<InstituteType> instituteTypeMap = new ArrayList<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		refreshCache();
	}

	public static List<InstituteType> getAllInstituteTypes() {
		return instituteTypeMap;
	}
	
	public static void addInstituteType(InstituteType type) {
		instituteTypeMap.add(type);
	}

	public void refreshCache() {
		List<InstituteType> instituteType = instituteTypeDao.findAll();
		instituteTypeMap.clear();
		instituteTypeMap.addAll(instituteType);
	}
}
