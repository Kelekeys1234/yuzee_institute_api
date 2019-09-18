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

import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.dao.IHelpDAO;
import com.seeka.app.dto.HelpDto;
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
}
