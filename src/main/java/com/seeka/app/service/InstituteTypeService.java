package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Intake;
import com.seeka.app.dao.InstituteTypeDAO;
import com.seeka.app.dto.InstituteTypeDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class InstituteTypeService implements IInstituteTypeService {

    @Autowired
    private InstituteTypeDAO iInstituteTypeDAO;

    @Override
    public void save(InstituteTypeDto instituteTypeDto) {
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

    @Override
    public void update(InstituteTypeDto instituteType) {
        Date today = new Date();
		/*
		 * instituteType.setUpdatedOn(today); iInstituteTypeDAO.update(instituteType);
		 */
    }

    @Override
    public InstituteType get(String id) {
        return iInstituteTypeDAO.get(id);
    }

    @Override
    public List<Intake> getAllIntake() {
        return  iInstituteTypeDAO.getAllIntake();
    }

    @Override
    public List<InstituteTypeDto> getAllInstituteType() {
        return iInstituteTypeDAO.getAll();
    }

	@Override
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
				instituteTypeDto.setName(instituteType.getName());
				instituteTypeDto.setType(instituteType.getType());
				log.info("Adding instiuteType in list");
				listOfInstituteDto.add(instituteTypeDto);
			});
		}
		log.info("returning instituteType in final response");
		return listOfInstituteDto;
	}
}
