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
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipFilterDto;
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
        Map<String, Object> response = new HashMap<String, Object>();
        Scholarship scholarshipObj = iScholarshipDAO.get(id);
        if (scholarshipObj != null) {
            response.put("message", "Scholarship fetched successfully");
            response.put("status", HttpStatus.OK.value());
            response.put("data", CommonUtil.convertScholarshipBeanToScholarshipDto(scholarshipObj));
        } else {
            response.put("message", "Scholarship not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
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
            response.put("status", HttpStatus.OK.value());
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
                response.put("status", HttpStatus.OK.value());
            } else {
                status = IConstant.DELETE_FAILURE_ID_NOT_FOUND_SCHOLARSHIP;
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            status = IConstant.DELETE_FAILURE_SCHOLARSHIP;
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> updateScholarship(@Valid BigInteger id, ScholarshipDto scholarshipDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SCHOLARSHIP_UPDATE_SUCCESS;
        try {
            Scholarship scholarship = iScholarshipDAO.findById(id);
            if (scholarship != null) {
                iScholarshipDAO.updateScholarship(scholarship, scholarshipDto);
                response.put("status", HttpStatus.OK.value());
            } else {
                status = IConstant.UPDATE_FAILURE_ID_NOT_FOUND_SCHOLARSHIP;
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            status = IConstant.UPDATE_FAILURE_SCHOLARSHIP;
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getAllScholarship(Integer pageNumber, Integer pageSize) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Scholarship> scholarships = null;
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = iScholarshipDAO.findTotalCount();
            int startIndex;
            if (pageNumber > 1) {
                startIndex = ((pageNumber - 1) * pageSize) + 1;
            } else {
                startIndex = pageNumber;
            }
            paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
            scholarships = iScholarshipDAO.getAll(startIndex, pageSize);
            if (scholarships != null && !scholarships.isEmpty()) {
                response.put("message", "Scholarship fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Scholarship not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", scholarships);
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
        dto.setOfferedByCourse(scholarship.getOfferedByCourse());
        dto.setOfferedByInstitute(scholarship.getOfferedByInstitute());
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

    @Override
    public Map<String, Object> scholarshipFilter(ScholarshipFilterDto scholarshipFilterDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Scholarship> scholarshipList = new ArrayList<>();
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = iScholarshipDAO.findTotalCountOfSchoolarship(scholarshipFilterDto);
            int startIndex;
            if (scholarshipFilterDto.getPageNumber() > 1) {
                startIndex = ((scholarshipFilterDto.getPageNumber() - 1) * scholarshipFilterDto.getMaxSizePerPage()) + 1;
            } else {
                startIndex = scholarshipFilterDto.getPageNumber();
            }
            paginationUtilDto = PaginationUtil.calculatePagination(startIndex, scholarshipFilterDto.getMaxSizePerPage(), totalCount);
            List<Scholarship> scholarships = iScholarshipDAO.scholarshipFilter(startIndex, scholarshipFilterDto.getMaxSizePerPage(), scholarshipFilterDto);
            for (Scholarship scholarship : scholarships) {
                scholarshipList.add(getScholarship(scholarship));
            }
            if (scholarships != null && !scholarships.isEmpty()) {
                response.put("message", "Scholarship fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Scholarship not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", scholarshipList);
        response.put("totalCount", totalCount);
        response.put("pageNumber", paginationUtilDto.getPageNumber());
        response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
        response.put("hasNextPage", paginationUtilDto.isHasNextPage());
        response.put("totalPages", paginationUtilDto.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> autoSearch(Integer pageNumber, Integer pageSize, String searchKey) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Scholarship> scholarships = null;
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = iScholarshipDAO.findTotalCountOfScholarshipAutoSearch(searchKey);
            int startIndex = (pageNumber - 1) * pageSize;
            paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
            scholarships = iScholarshipDAO.autoSearch(startIndex, pageSize, searchKey);
            if (scholarships != null && !scholarships.isEmpty()) {
                response.put("message", "Scholarship fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Scholarship not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", scholarships);
        response.put("totalCount", totalCount);
        response.put("pageNumber", paginationUtilDto.getPageNumber());
        response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
        response.put("hasNextPage", paginationUtilDto.isHasNextPage());
        response.put("totalPages", paginationUtilDto.getTotalPages());
        return response;
    }
}
