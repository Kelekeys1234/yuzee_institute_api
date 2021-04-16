package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.dto.CourseDTOElasticSearch;
import com.yuzee.app.dto.FundingResponseDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.dto.UserInitialInfoDto;
import com.yuzee.app.dto.ViewTransactionDto;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.handler.ElasticHandler;
import com.yuzee.app.handler.EligibilityHandler;
import com.yuzee.app.handler.UserHandler;
import com.yuzee.app.handler.ViewTransactionHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommonProcessor {
	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private EligibilityHandler eligibilityHandler;

	@Autowired
	private UserHandler userHandler;

	@Autowired
	private ElasticHandler elasticHandler;
	
	@Autowired
	private ViewTransactionHandler viewTransactionHandler;
	
	@Autowired
	private NotificationProcessor notificationProcessor;

	@Autowired
	private ConversionProcessor conversionProcessor;

	public void notifyCourseUpdates(String notificationType, List<Course> courses) {
		courses.stream().forEach(c -> {
			if (!ObjectUtils.isEmpty(c)) {
				log.info("Notify course information changed");
				try {
					List<ViewTransactionDto> favoriteTransactionList = viewTransactionHandler.getAllUsersWhoMarkCourseFavorite(c.getUpdatedBy(), c.getId());
					notificationProcessor.notifyCourseChanged(c, favoriteTransactionList, notificationType);
				} catch (InvokeException e) {
					log.error("error retriving users who marked course as favorite",e);
				}
			}				
		});
	}
	
	public boolean checkIfPriceChanged(List<CourseDeliveryModes> beforeUpdate, List<CourseDeliveryModes> afterUpdate) {
		Map<String, CourseDeliveryModes> afterUpdateMap = afterUpdate.stream().collect(Collectors.toMap(CourseDeliveryModes::getId, e -> e));
		return beforeUpdate.stream().anyMatch(e -> afterUpdateMap.containsKey(e.getId()) && 
				!(e.getDomesticFee().equals(afterUpdateMap.get(e.getId()).getDomesticFee()) 
						&& e.getInternationalFee().equals(afterUpdateMap.get(e.getId()).getInternationalFee())));
	}
	
	public List<StorageDto> getEntityGallery(String entityType, String entityId)
			throws NotFoundException, InternalServerException {
		if (EntityTypeEnum.COURSE.name().equals(entityType)) {
			return courseProcessor.getCourseGallery(entityId);
		} else if (EntityTypeEnum.INSTITUTE.name().equals(entityType)) {
			return instituteProcessor.getInstituteGallery(entityId);
		}
		return new ArrayList<>();
	}

	public Map<String, FundingResponseDto> validateAndGetFundingsByFundingNameIds(List<String> fundingNameIds)
			throws NotFoundException, InvokeException {
		log.info("inside getFundingMapByFundingNameIds");
		Map<String, FundingResponseDto> map = new HashMap<>();
		if (!CollectionUtils.isEmpty(fundingNameIds)) {
			List<FundingResponseDto> dtos = eligibilityHandler.getFundingByFundingNameId(fundingNameIds);
			if (!CollectionUtils.isEmpty(dtos)) {
				map = dtos.stream().collect(Collectors.toMap(FundingResponseDto::getFundingNameId, e -> e));
			}
			if (map.size() != fundingNameIds.size()) {
				log.error("one or more funding_name_ids not found");
				throw new NotFoundException("one or more funding_name_ids not found");
			}
		}
		return map;
	}

	public Map<String, UserInitialInfoDto> validateAndGetUsersByUserIds(List<String> userIds)
			throws NotFoundException, InvokeException {
		log.info("inside getFundingMapByFundingNameIds");
		Map<String, UserInitialInfoDto> map = new HashMap<>();
		if (!CollectionUtils.isEmpty(userIds)) {
			List<UserInitialInfoDto> dtos = userHandler.getUserByIds(userIds);
			if (!CollectionUtils.isEmpty(dtos)) {
				map = dtos.stream().collect(Collectors.toMap(UserInitialInfoDto::getUserId, e -> e));
			}
			if (map.size() != userIds.size()) {
				log.error("one or more user_ids not found");
				throw new NotFoundException("one or more user_ids not found");
			}
		}
		return map;
	}

	public void saveElasticCourses(List<Course> courses) {
		log.info("Calling elastic service to save/update courses on elastic index ");
		if (!CollectionUtils.isEmpty(courses)) {
			List<CourseDTOElasticSearch> courseElasticDtos = courses.stream()
					.map(e -> conversionProcessor.convertToCourseDTOElasticSearchEntity(e)).collect(Collectors.toList());
			elasticHandler.saveUpdateData(courseElasticDtos);
		}
	}
}
