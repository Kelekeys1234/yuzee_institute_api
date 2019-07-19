package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.Scholarship;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dao.ILevelDAO;
import com.seeka.app.dao.IScholarshipDAO;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class ScholarshipService implements IScholarshipService {

    @Autowired
    private IScholarshipDAO iScholarshipDAO;

    @Autowired
    private ICountryDAO iCountryDAO;

    @Autowired
    private IInstituteDAO iInstituteDAO;

    @Autowired
    private ILevelDAO iLevelDAO;

    @Override
    public void save(Scholarship obj) {
        iScholarshipDAO.save(obj);
    }

    @Override
    public Map<String, Object> get(BigInteger id) {
        ErrorDto errorDto = null;
        Map<String, Object> response = new HashMap<String, Object>();
        Scholarship scholarshipObj = iScholarshipDAO.get(id);
        if (null == scholarshipObj) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Scholarship Not Found.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return response;

        }
        response.put("status", 1);
        response.put("message", "Success");
        response.put("scholarshipObj", scholarshipObj);
        return response;
    }

    @Override
    public Map<String, Object> save(ScholarshipDto scholarshipObj) {
        Map<String, Object> response = new HashMap<String, Object>();
        boolean status = true;
        try {
            Country country = null;
            Institute institute = null;
            Level level = null;

            if (scholarshipObj.getCountryId() != null) {
                country = iCountryDAO.get(scholarshipObj.getCountryId());
            }
            if (scholarshipObj.getInstituteId() != null) {
                institute = iInstituteDAO.get(scholarshipObj.getInstituteId());
            }
            if (scholarshipObj.getLevelId() != null) {
                level = iLevelDAO.get(scholarshipObj.getLevelId());
            }

            Scholarship scholarship = CommonUtil.convertScholarshipDTOToBean(scholarshipObj, country, institute, level);
            iScholarshipDAO.save(scholarship);

        } catch (Exception exception) {
            status = false;
            exception.printStackTrace();
        }
        if (status) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SCHOLARSHIP_SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", IConstant.FAIL);
        }
        return response;

    }

    @Override
    public Map<String, Object> deleteScholarship(BigInteger scholarshipId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SCHOLARSHIP_DELETE_SUCCESS;
        try {
            Scholarship scholarshiyById = iScholarshipDAO.findById(scholarshipId);
            if (scholarshiyById != null) {
                scholarshiyById.setIsActive(false);
                scholarshiyById.setIsDeleted(true);
                iScholarshipDAO.deleteScholarship(scholarshiyById);
            } else {
                status = IConstant.DELETE_FAILURE_ID_NOT_FOUND_SCHOLARSHIP;
            }
        } catch (Exception exception) {
            status = IConstant.DELETE_FAILURE_SCHOLARSHIP;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> updateScholarship(@Valid BigInteger id, ScholarshipDto scholarshipDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SCHOLARSHIP_UPDATE_SUCCESS;
        try {
            Scholarship scholarshiyById = iScholarshipDAO.findById(id);
            if (scholarshiyById != null) {

                iScholarshipDAO.updateScholarship(scholarshiyById, scholarshipDto);
            } else {
                status = IConstant.UPDATE_FAILURE_ID_NOT_FOUND_SCHOLARSHIP;
            }
        } catch (Exception exception) {
            status = IConstant.UPDATE_FAILURE_SCHOLARSHIP;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getAllScholarship(Integer pageNumber, Integer pageSize) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Scholarship> scholarshipList = new ArrayList<>();
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = iScholarshipDAO.findTotalCount();
            paginationUtilDto = PaginationUtil.calculatePagination(pageNumber, pageSize, totalCount);
            List<Scholarship> scholarships = iScholarshipDAO.getAll(pageNumber, pageSize);
            for (Scholarship scholarship : scholarships) {
                scholarshipList.add(getScholarship(scholarship));
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("Scholarships", scholarshipList);
        response.put("totalCount", totalCount);
        response.put("pageNumber", paginationUtilDto.getPageNumber());
        response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
        response.put("hasNextPage", paginationUtilDto.isHasNextPage());
        response.put("totalPages", paginationUtilDto.getTotalPages());
        return response;
    }

    private Scholarship getScholarship(Scholarship scholarship) {
        Scholarship dto = new Scholarship();
        dto.setId(scholarship.getId());
        dto.setCountry(scholarship.getCountry());
        dto.setInstitute(scholarship.getInstitute());
        dto.setLevel(scholarship.getLevel());
        dto.setName(scholarship.getName());
        dto.setAmount(scholarship.getAmount());
        dto.setDescription(scholarship.getDescription());
        dto.setStudent(scholarship.getStudent());
        dto.setWebsite(scholarship.getWebsite());
        dto.setScholarshipTitle(scholarship.getScholarshipTitle());
        dto.setOfferedBy(scholarship.getOfferedBy());
        dto.setBenefits(scholarship.getBenefits());
        dto.setRequirements(scholarship.getRequirements());
        dto.setEligibility(scholarship.getEligibility());
        dto.setIntake(scholarship.getIntake());
        dto.setLanguage(scholarship.getLanguage());
        dto.setValidity(scholarship.getValidity());
        dto.setGender(scholarship.getGender());
        dto.setApplicationDeadline(scholarship.getApplicationDeadline());
        dto.setScholarshipAmount(scholarship.getScholarshipAmount());
        dto.setNumberOfAvaliability(scholarship.getNumberOfAvaliability());
        dto.setHeadquaters(scholarship.getHeadquaters());
        dto.setEmail(scholarship.getEmail());
        dto.setAddress(scholarship.getAddress());
        dto.setIsActive(scholarship.getIsActive());
        dto.setIsDeleted(scholarship.getIsDeleted());
        return dto;
    }

    @Override
    public List<ScholarshipDto> getScholarshipBySearchKey(String searchKey) {
        return iScholarshipDAO.getScholarshipBySearchKey(searchKey);
    }

}
