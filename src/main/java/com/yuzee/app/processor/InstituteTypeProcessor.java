package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.InstituteType;
import com.yuzee.app.dao.InstituteTypeDao;
import com.yuzee.app.dto.InstituteTypeDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class InstituteTypeProcessor {

    @Autowired
    private InstituteTypeDao iInstituteTypeDAO;

    public void addUpdateInstituteType(InstituteTypeDto instituteTypeDto) {
    	log.debug("Inside getInstituteTypeByCountryName() method");
    	InstituteType instituteType = new InstituteType();
    	log.info("Copying bean class to DTO class");
    	BeanUtils.copyProperties(instituteTypeDto, instituteType);
        Date today = new Date();
        instituteType.setCreatedOn(today);
        instituteType.setUpdatedOn(today);
        instituteType.setCreatedBy("API");
        instituteType.setUpdatedBy("API");
        instituteType.setIsActive(true);
        log.info("Calling DAO layer to save instituteType in DB");
        iInstituteTypeDAO.save(instituteType);
    }

    public InstituteType getInstituteType(String id) {
        return iInstituteTypeDAO.get(id);
    }

    public List<InstituteTypeDto> getAllInstituteType() {
        return iInstituteTypeDAO.getAll();
    }

	public List<InstituteTypeDto> getInstituteTypeByCountryName(String countryName) {
		log.debug("Inside getInstituteTypeByCountryName() method");
		List<InstituteTypeDto> listOfInstituteDto = new ArrayList<InstituteTypeDto>();
		log.info("Fetching InstituteType from DB having countryName = "+countryName);
		List<InstituteType> listOfInstituteType = iInstituteTypeDAO.getByCountryName(countryName);
		if (!CollectionUtils.isEmpty(listOfInstituteType)) {
			log.info("InstituteType is not null then start iterating instituteType");
			listOfInstituteType.stream().forEach(instituteType -> {
				InstituteTypeDto instituteTypeDto = new InstituteTypeDto();
				instituteTypeDto.setInstituteTypeId(instituteType.getId());
				instituteTypeDto.setCountryName(instituteType.getCountryName());
				instituteTypeDto.setDescription(instituteType.getDescription());
				instituteTypeDto.setInstituteTypeName(instituteType.getName());
				log.info("Adding instiuteType in list");
				listOfInstituteDto.add(instituteTypeDto);
			});
		}
		log.info("returning instituteType in final response");
		return listOfInstituteDto;
	}

}
