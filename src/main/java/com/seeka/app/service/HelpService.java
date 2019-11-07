package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.HelpAnswer;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.dao.IHelpDAO;
import com.seeka.app.dto.HelpAnswerDto;
import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.HelpEnum;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class HelpService implements IHelpService {

	@Autowired
	private IHelpDAO helpDAO;

	@Autowired
	private IUsersService iUsersService;

	@Autowired
	private IMediaService iMediaService;

	@Autowired
	private IStorageService iStorageService;

	@Override
	public Map<String, Object> save(@Valid final HelpDto helpDto, final BigInteger userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			helpDAO.save(convertDtoToSeekaHelp(helpDto, null, userId));
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.HELP_SUCCESS_MESSAGE);
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public SeekaHelp convertDtoToSeekaHelp(final HelpDto dto, final BigInteger id, final BigInteger userId) {
		SeekaHelp seekaHelp = null;
		if (id != null) {
			seekaHelp = helpDAO.get(id);
		} else {
			seekaHelp = new SeekaHelp();
			seekaHelp.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			seekaHelp.setStatus(HelpEnum.NOTASSIGNED.toString());
		}
		seekaHelp.setUserId(userId);
		seekaHelp.setCategory(helpDAO.getHelpCategory(dto.getCategoryId()));
		seekaHelp.setSubCategory(helpDAO.getHelpSubCategory(dto.getSubCategoryId()));
		seekaHelp.setCreatedBy(dto.getCreatedBy());
		seekaHelp.setUpdatedBy(dto.getUpdatedBy());
		seekaHelp.setTitle(dto.getTitle());
		seekaHelp.setDescritpion(dto.getDescription());
		seekaHelp.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		seekaHelp.setIsActive(true);
		seekaHelp.setIsQuestioning(dto.getIsQuestioning());
		return seekaHelp;
	}

	@Override
	public Map<String, Object> get(final BigInteger id) {
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

	public HelpDto convertSeekaHelpToDto(final SeekaHelp seekaHelp) {
		HelpDto dto = new HelpDto();

		dto.setId(seekaHelp.getId());
		dto.setTitle(seekaHelp.getTitle());
		dto.setDescription(seekaHelp.getDescritpion());
		dto.setCategoryId(seekaHelp.getCategory().getId());
		dto.setSubCategoryId(seekaHelp.getSubCategory().getId());
		dto.setCreatedBy(seekaHelp.getCreatedBy());
		dto.setUpdatedBy(seekaHelp.getUpdatedBy());
		dto.setIsQuestioning(seekaHelp.getIsQuestioning());
		dto.setStatus(seekaHelp.getStatus());
		dto.setIsArchive(seekaHelp.getIsArchive());
		if (seekaHelp.getUserId() != null) {
			try {
				UserDto userDto = iUsersService.getUserById(seekaHelp.getUserId());
				if (userDto != null) {
					if (userDto.getFirstName() != null) {
						dto.setCreatedUser(userDto.getFirstName());
					}
					if (userDto.getFirstName() != null && userDto.getLastName() != null) {
						dto.setCreatedUser(userDto.getFirstName() + " " + userDto.getLastName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (seekaHelp.getCreatedOn() != null) {
			dto.setCreatedOn(DateUtil.convertDateToString(seekaHelp.getCreatedOn()));
		}
		return dto;
	}

	@Override
	public Map<String, Object> update(final HelpDto helpDto, final BigInteger id, final BigInteger userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			helpDAO.update(convertDtoToSeekaHelp(helpDto, id, userId));
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
	public Map<String, Object> getAll(final Integer pageNumber, final Integer pageSize) {
		Map<String, Object> response = new HashMap<>();
		String status = IConstant.SUCCESS;
		List<SeekaHelp> helps = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = helpDAO.findTotalHelpRecord(null, null);
			int startIndex = (pageNumber - 1) * pageSize;
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			helps = helpDAO.getAll(startIndex, pageSize, null, null);
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
	public Map<String, Object> save(@Valid final HelpCategoryDto helpCategoryDto) {
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

	public HelpCategory convertHelpCategoryDtoToBean(final HelpCategoryDto helpCategoryDto) {
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
	public Map<String, Object> save(@Valid final HelpSubCategoryDto helpSubCategoryDto) {
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

	public HelpSubCategory convertHelpCategoryDtoToBean(final HelpSubCategoryDto helpSubCategoryDto) {
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
	public Map<String, Object> getCategory(final BigInteger id) {
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

	public HelpCategoryDto convertHelpCategoryToDto(final HelpCategory helpCategory) {
		HelpCategoryDto helpCategoryDto = new HelpCategoryDto();
		helpCategoryDto.setCreatedBy(helpCategory.getCreatedBy());
		helpCategoryDto.setName(helpCategory.getName());
		helpCategoryDto.setUpdatedBy(helpCategory.getUpdatedBy());
		helpCategoryDto.setId(helpCategory.getId());
		return helpCategoryDto;
	}

	@Override
	public Map<String, Object> getSubCategory(final BigInteger id) {
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

	public HelpSubCategoryDto convertHelpSubCategoryToDto(final HelpSubCategory helpSubCategory) {
		HelpSubCategoryDto helpSubCategoryDto = new HelpSubCategoryDto();
		helpSubCategoryDto.setCategoryId(helpSubCategory.getCategoryId().getId());
		helpSubCategoryDto.setName(helpSubCategory.getName());
		helpSubCategoryDto.setCreatedBy(helpSubCategory.getCreatedBy());
		helpSubCategoryDto.setUpdatedBy(helpSubCategory.getUpdatedBy());
		helpSubCategoryDto.setId(helpSubCategory.getId());
		helpSubCategoryDto.setHelpCount(helpSubCategory.getHelpCount());
		return helpSubCategoryDto;
	}

	@Override
	public List<HelpSubCategoryDto> getSubCategoryByCategory(final BigInteger categoryId, final Integer startIndex, final Integer pageSize) {
		List<HelpSubCategoryDto> subCategoryDtos = new ArrayList<>();
		List<HelpSubCategory> categories = helpDAO.getSubCategoryByCategory(categoryId, startIndex, pageSize);
		for (HelpSubCategory helpSubCategory : categories) {
			helpSubCategory.setHelpCount(helpDAO.findTotalHelpRecordBySubCategory(helpSubCategory.getId()));
			subCategoryDtos.add(convertHelpSubCategoryToDto(helpSubCategory));
		}
		return subCategoryDtos;
	}

	@Override
	public Map<String, Object> getHelpByCategory(final BigInteger categoryId) {
		Map<String, Object> response = new HashMap<>();
		List<HelpDto> helpDtos = new ArrayList<>();
		try {
			List<SeekaHelp> seekHelps = helpDAO.getHelpByCategory(categoryId);
			try {
				for (SeekaHelp seekaHelp : seekHelps) {
					helpDtos.add(convertSeekaHelpToDto(seekaHelp));
				}
			} catch (Exception exception) {
			}
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
		List<HelpSubCategory> helpSubCategories = new ArrayList<>();
		try {
			List<HelpSubCategory> subCatgories = helpDAO.getAllHelpSubCategories();
			for (HelpSubCategory helpSubCategory : subCatgories) {
				helpSubCategory.setHelpCount(helpDAO.findTotalHelpRecordBySubCategory(helpSubCategory.getId()));
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

	@Override
	public Map<String, Object> saveAnswer(@Valid final HelpAnswerDto helpAnswerDto, final MultipartFile file) {
		Map<String, Object> response = new HashMap<>();
		try {
			HelpAnswer helpAnswer = helpDAO.save(convertDtoToHelpAnswerBeans(helpAnswerDto));
			if (helpAnswer != null && file != null) {
				String logoName = iMediaService.uploadImage(file, helpAnswer.getId(), ImageCategory.HELP_SUPPORT.name(), null);
				System.out.println("Help answer media upload for id - >" + helpAnswer.getId() + " and Image  name :" + logoName);
				if (logoName != null && !logoName.isEmpty() && !logoName.equals("null")) {
					helpAnswer.setFileName(logoName);
					helpDAO.updateAnwser(helpAnswer);
				}
			}
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.HELP_ANSWER_SUCCESS_MESSAGE);
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpAnswer convertDtoToHelpAnswerBeans(final HelpAnswerDto answerDto) {
		HelpAnswer helpAnswer = new HelpAnswer();
		helpAnswer.setAnswer(answerDto.getAnswer());
		helpAnswer.setSeekaHelp(helpDAO.get(answerDto.getHelpId()));
		helpAnswer.setUser(answerDto.getUserId());
		helpAnswer.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		helpAnswer.setCreatedBy(answerDto.getCreatedBy());
		helpAnswer.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		helpAnswer.setUpdatedBy(answerDto.getUpdatedBy());
		helpAnswer.setIsDeleted(false);
		return helpAnswer;
	}

	@Override
	public Map<String, Object> getAnswerByHelpId(final BigInteger helpId) {
		Map<String, Object> response = new HashMap<>();
		List<HelpAnswerDto> dtos = new ArrayList<>();
		try {
			List<HelpAnswer> helpAnswers = helpDAO.getAnswerByHelpId(helpId);
			for (HelpAnswer helpAnswer : helpAnswers) {
				dtos.add(convertBeanToHelpAnswerDto(helpAnswer));
			}
			if (dtos != null && !dtos.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_ANSWER_SUCCESS);
				response.put("data", dtos);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_ANSWER_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpAnswerDto convertBeanToHelpAnswerDto(final HelpAnswer helpAnswer) {
		HelpAnswerDto helpAnswerDto = new HelpAnswerDto();
		helpAnswerDto.setAnswer(helpAnswer.getAnswer());
		helpAnswerDto.setUserId(helpAnswer.getUser());
		helpAnswerDto.setHelpId(helpAnswer.getSeekaHelp().getId());
		helpAnswerDto.setCreatedBy(helpAnswer.getCreatedBy());
		helpAnswerDto.setUpdatedBy(helpAnswer.getUpdatedBy());
		if (helpAnswer.getCreatedOn() != null) {
			helpAnswerDto.setCreatedOn(DateUtil.convertDateToString(helpAnswer.getCreatedOn()));
		}
		if (helpAnswer.getFileName() != null) {
			try {
				List<StorageDto> storageDTOList = iStorageService.getStorageInformation(helpAnswer.getId(), ImageCategory.HELP_SUPPORT.toString(), null, "en");
				if (storageDTOList != null && !storageDTOList.isEmpty()) {
					StorageDto storageDto = storageDTOList.get(0);
					if (storageDto != null) {
						helpAnswerDto.setFileUrl(storageDto.getImageURL());
					}
				}
			} catch (ValidationException e) {
				e.printStackTrace();
			}
		}
		return helpAnswerDto;
	}

	@Override
	public List<HelpCategoryDto> getCategory(final Integer startIndex, final Integer pageSize) {
		List<HelpCategoryDto> dtos = new ArrayList<>();
		List<HelpCategory> categories = helpDAO.getAllCategory(startIndex, pageSize);
		for (HelpCategory category : categories) {
			dtos.add(convertHelpCategoryToDto(category));
		}
		return dtos;
	}

	@Override
	public Map<String, Object> delete(@Valid final BigInteger id) {
		Map<String, Object> response = new HashMap<>();
		try {
			SeekaHelp help = helpDAO.get(id);
			if (help != null) {
				help.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
				help.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
				help.setIsActive(false);
				helpDAO.update(help);
				response.put("status", HttpStatus.OK.value());
				response.put("message", "Help deleted successfully");
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
	public Map<String, Object> updateStatus(final BigInteger id, final BigInteger userId, final String status) {
		Map<String, Object> response = new HashMap<>();
		try {
			SeekaHelp help = helpDAO.get(id);
			if (help != null) {
				help.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
				help.setStatus(status);
				if (userId != null) {
					help.setAssignedUserId(userId);
				}
				helpDAO.update(help);
				response.put("status", HttpStatus.OK.value());
				response.put("message", "Help deleted successfully");
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
	public Map<String, Object> filter(final String status, final String mostRecent, final BigInteger categoryId) {
		Map<String, Object> response = new HashMap<>();
		List<HelpDto> helpDtos = new ArrayList<>();
		try {
			List<SeekaHelp> seekaHelps = new ArrayList<>();
			if (status != null && !status.isEmpty()) {
				seekaHelps = helpDAO.findByStatus(status, categoryId);
			}
			if (mostRecent != null && !mostRecent.isEmpty()) {
				seekaHelps = helpDAO.findByMostRecent(mostRecent, categoryId);
			}
			if (status != null && !status.isEmpty() && mostRecent != null && !mostRecent.isEmpty()) {
				seekaHelps = helpDAO.findByStatusAndMostRecent(status, mostRecent, categoryId);
			}
			if (status == null && mostRecent == null) {
				seekaHelps = helpDAO.getHelpByCategory(categoryId);
			}
			for (SeekaHelp seekaHelp : seekaHelps) {
				helpDtos.add(convertSeekaHelpToDto(seekaHelp));
			}
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
	public List<SeekaHelp> getUserHelpList(final BigInteger userId, final int startIndex, final Integer pageSize, final Boolean isArchive) {
		return helpDAO.getAll(startIndex, pageSize, userId, isArchive);
	}

	@Override
	public int getUserHelpCount(final BigInteger userId, final Boolean isArchive) {
		return helpDAO.findTotalHelpRecord(userId, isArchive);
	}

	@Override
	public void setIsFavouriteFlag(final BigInteger id, final boolean isFavourite) throws NotFoundException {
		helpDAO.setIsFavouriteFlag(id, isFavourite);
	}

	@Override
	public int getCategoryCount() {
		return helpDAO.getCategoryCount();
	}

	@Override
	public int getSubCategoryCount(final BigInteger categoryId) {
		return helpDAO.getSubCategoryCount(categoryId);
	}

	@Override
	public void archiveHelpSupport(final BigInteger entityId, final boolean isArchive) {
		SeekaHelp seekaHelp = helpDAO.get(entityId);
		seekaHelp.setIsArchive(isArchive);
		seekaHelp.setUpdatedBy("API");
		seekaHelp.setUpdatedOn(new Date());
		helpDAO.update(seekaHelp);
	}

}
