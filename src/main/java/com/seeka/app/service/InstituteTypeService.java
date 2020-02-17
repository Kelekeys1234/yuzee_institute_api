package com.seeka.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Intake;
import com.seeka.app.dao.IInstituteTypeDAO;

@Service
@Transactional
public class InstituteTypeService implements IInstituteTypeService {

    @Autowired
    private IInstituteTypeDAO iInstituteTypeDAO;

    @Override
    public void save(InstituteType instituteType) {
        Date today = new Date();
        instituteType.setCreatedOn(today);
        instituteType.setUpdatedOn(today);
        iInstituteTypeDAO.save(instituteType);
    }

    @Override
    public void update(InstituteType instituteType) {
        Date today = new Date();
        instituteType.setUpdatedOn(today);
        iInstituteTypeDAO.update(instituteType);
    }

    @Override
    public InstituteType get(String id) {
        return iInstituteTypeDAO.get(id);
    }

    @Override
    public Map<String, Object> getAllIntake() {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            List<Intake> intakes = iInstituteTypeDAO.getAllIntake();
            if (intakes != null && !intakes.isEmpty()) {
                response.put("DATA", intakes);
                response.put("message", "Intake retrieved successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Intake not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public List<InstituteType> getAllInstituteType() {
        return iInstituteTypeDAO.getAll();
    }
}
