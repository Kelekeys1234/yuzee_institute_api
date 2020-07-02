package com.seeka.app.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.dao.InstituteTypeDao;
import com.seeka.app.dto.InstituteTypeDto;
import com.seeka.app.repository.InstituteTypeRepository;

@Component
public class InstituteTypeDaoImpl implements InstituteTypeDao {

    @Autowired
    private InstituteTypeRepository instituteTypeRepository;
    
    @Override
    public void save(InstituteType instituteType) {
        instituteTypeRepository.save(instituteType);
    }

    @Override
    public void update(InstituteType instituteType) {
    	instituteTypeRepository.save(instituteType);
    }

    @Override
    public InstituteType get(String id) {
        Optional<InstituteType> optionalInstituteType = instituteTypeRepository.findById(id);
        return optionalInstituteType.get();
    }

    @Override
    public List<InstituteTypeDto> getAll() {
    	List<InstituteType> instituteTypes = instituteTypeRepository.findAll();
        List<InstituteTypeDto> instituteTypeDtos = new ArrayList<>();
        instituteTypes.stream().forEach(instituteType -> {
            InstituteTypeDto obj  = new InstituteTypeDto();
            obj.setInstituteTypeId(instituteType.getId());
            obj.setName(instituteType.getName());
            obj.setCountryName(instituteType.getCountryName());
            obj.setDescription(instituteType.getDescription());
            instituteTypeDtos.add(obj);
        });
        return instituteTypeDtos;
    }

	@Override
	public List<InstituteType> getByCountryName(String countryName) {
		return instituteTypeRepository.findByCountryName(countryName);
	}

}
