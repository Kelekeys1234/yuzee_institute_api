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

import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.dao.IHelpDAO;
import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class HelpService implements IHelpService {

    @Autowired
    private IHelpDAO helpDAO;

    @Override
    public Map<String, Object> save(@Valid HelpDto helpDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            helpDAO.save(convertDtoToSeekaHelp(helpDto, null));
            response.put("status", HttpStatus.OK.value());
            response.put("message", IConstant.HELP_SUCCESS_MESSAGE);
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    public SeekaHelp convertDtoToSeekaHelp(HelpDto dto, BigInteger id) {
        SeekaHelp seekaHelp = null;
        if (id != null) {
            seekaHelp = helpDAO.get(id);
        } else {
            seekaHelp = new SeekaHelp();
            seekaHelp.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        }
        seekaHelp.setCategory(helpDAO.getHelpCategory(dto.getCategoryId()));
        seekaHelp.setSubCategory(helpDAO.getHelpSubCategory(dto.getSubCategoryId()));
        seekaHelp.setCreatedBy(dto.getCreatedBy());
        seekaHelp.setUpdatedBy(dto.getUpdatedBy());
        seekaHelp.setTitle(dto.getTitle());
        seekaHelp.setDescritpion(dto.getDescription());
        seekaHelp.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        seekaHelp.setIsActive(true);
        seekaHelp.setQuestioning(dto.getQuestioning());
        return seekaHelp;
    }

    @Override
    public Map<String, Object> get(BigInteger id) {
        Map<String, Object> response = new HashMap<>();
        HelpDto dto = null;
        try {
            dto = convertSeekaHelpToDto(helpDAO.get(id));
            if (dto != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.HELP_SUCCESS);
                response.put("data", dto);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.HELP_NOT_FOUND);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    public HelpDto convertSeekaHelpToDto(SeekaHelp seekaHelp) {
        HelpDto dto = new HelpDto();
        dto.setTitle(seekaHelp.getTitle());
        dto.setDescription(seekaHelp.getDescritpion());
        dto.setCategoryId(seekaHelp.getCategory().getId());
        dto.setSubCategoryId(seekaHelp.getSubCategory().getId());
        dto.setCreatedBy(seekaHelp.getCreatedBy());
        dto.setUpdatedBy(seekaHelp.getUpdatedBy());
        dto.setQuestioning(seekaHelp.getQuestioning());
        return dto;
    }

    @Override
    public Map<String, Object> update(HelpDto helpDto, BigInteger id) {
        Map<String, Object> response = new HashMap<>();
        try {
            helpDAO.update(convertDtoToSeekaHelp(helpDto, id));
            response.put("status", HttpStatus.OK.value());
            response.put("message", IConstant.HELP_UPDATE_MESSAGE);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> getAll(Integer pageNumber, Integer pageSize) {
        Map<String, Object> response = new HashMap<>();
        String status = IConstant.SUCCESS;
        List<SeekaHelp> helps = new ArrayList<>();
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = helpDAO.findTotalHelpRecord();
            int startIndex = (pageNumber - 1) * pageSize;
            paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
            helps = helpDAO.getAll(startIndex, pageSize);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", HttpStatus.OK.value());
        response.put("message", status);
        response.put("courses", helps);
        response.put("totalCount", totalCount);
        response.put("pageNumber", paginationUtilDto.getPageNumber());
        response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
        response.put("hasNextPage", paginationUtilDto.isHasNextPage());
        response.put("totalPages", paginationUtilDto.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> save(@Valid HelpCategoryDto helpCategoryDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            helpDAO.save(convertHelpCategoryDtoToBean(helpCategoryDto));
            response.put("status", HttpStatus.OK.value());
            response.put("message", IConstant.HELP_CATEGORY_SUCCESS_MESSAGE);
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
    
    public HelpCategory convertHelpCategoryDtoToBean(HelpCategoryDto helpCategoryDto){
        HelpCategory helpCategory = new HelpCategory();
        helpCategory.setName(helpCategoryDto.getName());
        helpCategory.setCreatedBy(helpCategoryDto.getCreatedBy());
        helpCategory.setUpdatedBy(helpCategoryDto.getUpdatedBy());
        helpCategory.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        helpCategory.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        helpCategory.setIsActive(true);
        return helpCategory;
        
    }

    @Override
    public Map<String, Object> save(@Valid HelpSubCategoryDto helpSubCategoryDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            helpDAO.save(convertHelpCategoryDtoToBean(helpSubCategoryDto));
            response.put("status", HttpStatus.OK.value());
            response.put("message", IConstant.HELP_SUBCATEGORY_SUCCESS_MESSAGE);
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
    
    public HelpSubCategory convertHelpCategoryDtoToBean(HelpSubCategoryDto helpSubCategoryDto){
        HelpSubCategory helpSubCategory = new HelpSubCategory();
        helpSubCategory.setName(helpSubCategoryDto.getName());
        helpSubCategory.setCreatedBy(helpSubCategoryDto.getCreatedBy());
        helpSubCategory.setUpdatedBy(helpSubCategoryDto.getUpdatedBy());
        helpSubCategory.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        helpSubCategory.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        helpSubCategory.setCategoryId(helpDAO.getHelpCategory(helpSubCategoryDto.getCategoryId()));
        helpSubCategory.setIsActive(true);
        return helpSubCategory;
    }

    @Override
    public Map<String, Object> getCategory(BigInteger id) {
        Map<String, Object> response = new HashMap<>();
        HelpCategoryDto dto = null;
        try {
            dto = convertHelpCategoryToDto(helpDAO.getHelpCategory(id));
            if (dto != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.HELP_CATEGORY_SUCCESS);
                response.put("data", dto);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.HELP_CATEGORY_NOT_FOUND);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
    
    public HelpCategoryDto convertHelpCategoryToDto(HelpCategory helpCategory){
        HelpCategoryDto helpCategoryDto = new HelpCategoryDto();
        helpCategoryDto.setCreatedBy(helpCategory.getCreatedBy());
        helpCategoryDto.setName(helpCategory.getName());
        helpCategoryDto.setUpdatedBy(helpCategory.getUpdatedBy());
        return helpCategoryDto; 
    }

    @Override
    public Map<String, Object> getSubCategory(BigInteger id) {
        Map<String, Object> response = new HashMap<>();
        HelpSubCategoryDto dto = null;
        try {
            dto = convertHelpSubCategoryToDto(helpDAO.getHelpSubCategory(id));
            if (dto != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.HELP_SUBCATEGORY_SUCCESS);
                response.put("data", dto);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.HELP_SUBCATEGORY_NOT_FOUND);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
    
    public HelpSubCategoryDto convertHelpSubCategoryToDto(HelpSubCategory helpSubCategory){
        HelpSubCategoryDto helpSubCategoryDto = new HelpSubCategoryDto();
        helpSubCategoryDto.setCategoryId(helpSubCategory.getCategoryId().getId());
        helpSubCategoryDto.setName(helpSubCategory.getName());
        helpSubCategoryDto.setCreatedBy(helpSubCategory.getCreatedBy());
        helpSubCategoryDto.setUpdatedBy(helpSubCategory.getUpdatedBy());
        return helpSubCategoryDto;
    }
    

    @Override
    public Map<String, Object> getSubCategoryByCategory(BigInteger categoryId) {
        Map<String, Object> response = new HashMap<>();
        List<HelpSubCategoryDto> subCategoryDtos = new ArrayList<HelpSubCategoryDto>();
        try {
            List<HelpSubCategory> categories = helpDAO.getSubCategoryByCategory(categoryId);
            for (HelpSubCategory helpSubCategory : categories) {
                subCategoryDtos.add(convertHelpSubCategoryToDto(helpSubCategory));
            }
            if (subCategoryDtos != null && !subCategoryDtos.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.HELP_SUBCATEGORY_SUCCESS);
                response.put("data", subCategoryDtos);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.HELP_SUBCATEGORY_NOT_FOUND);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> getHelpByCategory(BigInteger categoryId) {
        Map<String, Object> response = new HashMap<>();
        List<HelpDto> helpDtos = new ArrayList<>();
        try {
            List<SeekaHelp> seekHelps = helpDAO.getHelpByCategory(categoryId);
            for (SeekaHelp seekaHelp : seekHelps)
                helpDtos.add(convertSeekaHelpToDto(seekaHelp));
            if (helpDtos != null && !helpDtos.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.HELP_SUCCESS);
                response.put("data", helpDtos);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.HELP_NOT_FOUND);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> getSubCategoryCount() {
        Map<String, Object> response = new HashMap<>();
        List<HelpSubCategory> helpSubCategories = new ArrayList<HelpSubCategory>();
        try {
            List<HelpSubCategory> subCatgories = helpDAO.getAllHelpSubCategories();
            for (HelpSubCategory helpSubCategory : subCatgories) {
                helpSubCategory.setCount(helpDAO.findTotalHelpRecordBySubCategory(helpSubCategory.getId()));
                helpSubCategories.add(helpSubCategory);
            }
            if (helpSubCategories != null && !helpSubCategories.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.HELP_SUBCATEGORY_SUCCESS);
                response.put("data", helpSubCategories);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.HELP_SUBCATEGORY_NOT_FOUND);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
}
