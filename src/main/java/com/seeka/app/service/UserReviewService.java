package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserReview;
import com.seeka.app.bean.UserReviewRating;
import com.seeka.app.dao.IUserReviewDao;
import com.seeka.app.dto.ReviewQuestionsDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.dto.UserReviewDto;
import com.seeka.app.dto.UserReviewRatingDto;
import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.enumeration.ReviewCategory;
import com.seeka.app.enumeration.StudentCategory;
import com.seeka.app.enumeration.StudentType;
import com.seeka.app.exception.ValidationException;

/**
 *
 * @author SeekADegree
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserReviewService implements IUserReviewService {

	@Autowired
	private IUserReviewDao iUserReviewDao;

	@Autowired
	private IReviewQuestionService iReviewQuestionService;
	
	@Autowired
	private IUsersService iUsersService;

	@Override
	public UserReview addUserReview(final UserReviewDto userReviewDto) throws ValidationException {
		List<UserReviewRating> ratings = userReviewDto.getRatings();
		for (UserReviewRating userReviewRating : ratings) {
			if (userReviewRating.getReviewQuestionId() == null) {
				throw new ValidationException("One of reviewQuestionId is not found");
			} else {
				if (iReviewQuestionService.getReviewQuestionOnly(userReviewRating.getReviewQuestionId()) == null) {
					throw new ValidationException("Review Question not found for id:" + userReviewRating.getReviewQuestionId());
				}
			}
			if (userReviewRating.getRating() == null) {
				throw new ValidationException("One of questions rating is not found");
			}
		}

		if (!StudentType.ALUMNI.name().equals(userReviewDto.getStudentType()) && !StudentType.CURRENT_STUDENT.name().equals(userReviewDto.getStudentType())
				&& !StudentType.BOTH.name().equals(userReviewDto.getStudentType())) {
			throw new ValidationException("Student type Should be ALUMNI, CURRENT_STUDENT Or BOTH");
		}
		if (!StudentCategory.INTERNATIONAL.name().equals(userReviewDto.getStudentCategory())
				&& !StudentCategory.DOMESTIC.name().equals(userReviewDto.getStudentCategory())) {
			throw new ValidationException("Student category Either INTERNATIONAL or DOMESTIC");
		}

		UserReview userReview = new UserReview();

		List<UserReview> userReviewList = iUserReviewDao.getUserReviewList(userReviewDto.getUserId(), userReviewDto.getEntityId(),
				userReviewDto.getEntityType(), null, null, null, null);
		if (!userReviewList.isEmpty()) {
			throw new ValidationException("User review exists for same " + userReviewDto.getEntityType());
		}
		BeanUtils.copyProperties(userReviewDto, userReview);

		if (!ReviewCategory.COURSE.name().equals(userReviewDto.getEntityType()) && !ReviewCategory.INSTITUTE.name().equals(userReviewDto.getEntityType())) {
			throw new ValidationException("entity type Either COURSE or INSTITUTE");
		}

		userReview.setCreatedBy("API");
		userReview.setCreatedOn(new Date());
		userReview.setIsActive(true);
		iUserReviewDao.save(userReview);

		for (UserReviewRating userReviewRating : userReviewDto.getRatings()) {
			userReviewRating.setUserReview(userReview);
			iUserReviewDao.saveUserReviewRating(userReviewRating);
		}

		return userReview;
	}

	@Override
	public List<UserReviewResultDto> getUserReviewList(final String userId, final Integer startIndex, final Integer pageSize) throws ValidationException {
		
		UserDto userDto = iUsersService.getUserById(userId);
		
		List<UserReview> userReviewList = iUserReviewDao.getUserReviewList(userId, null, null, startIndex, pageSize, null, null);
		List<UserReviewResultDto> userReviewResultDtoList = new ArrayList<>();
		
		userReviewList.parallelStream().forEach(userReview -> {
			UserReviewResultDto reviewResultDto = new UserReviewResultDto();
			BeanUtils.copyProperties(userReview, reviewResultDto);
			List<UserReviewRating> userReviewRatings = iUserReviewDao.getUserReviewRatings(userReview.getId());
			List<UserReviewRatingDto> userReviewRatingDtoList = new ArrayList<>();
			userReviewRatings.parallelStream().forEach(userReviewRating -> {
				UserReviewRatingDto userReviewRatingDto = new UserReviewRatingDto();
				BeanUtils.copyProperties(userReviewRating, userReviewRatingDto);
				userReviewRatingDtoList.add(userReviewRatingDto);
			});
			reviewResultDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
			reviewResultDto.setRatings(userReviewRatingDtoList);
			userReviewResultDtoList.add(reviewResultDto);
		});
		return userReviewResultDtoList;
	}

	@Override
	public UserReviewResultDto getUserReviewDetails(final String userReviewId) throws ValidationException {
		UserReview userReview = iUserReviewDao.getUserReview(userReviewId);
		UserDto userDto = iUsersService.getUserById(userReview.getUserId());
		UserReviewResultDto userReviewResultDto = new UserReviewResultDto();
		BeanUtils.copyProperties(userReview, userReviewResultDto);
		userReviewResultDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		List<UserReviewRating> userReviewRatings = iUserReviewDao.getUserReviewRatings(userReview.getId());
		List<UserReviewRatingDto> userReviewRatingDtoList = new ArrayList<>();
		
		for (UserReviewRating userReviewRating : userReviewRatings) {
			UserReviewRatingDto userReviewRatingDto = new UserReviewRatingDto();
			ReviewQuestionsDto reviewQuestionsDto = iReviewQuestionService.getReviewQuestion(userReviewRating.getReviewQuestionId());
			BeanUtils.copyProperties(reviewQuestionsDto, userReviewRatingDto);
			userReviewRatingDto.setReviewQuestionId(userReviewRating.getReviewQuestionId());
			userReviewRatingDto.setRating(userReviewRating.getRating());
			userReviewRatingDtoList.add(userReviewRatingDto);
		}
		userReviewResultDto.setRatings(userReviewRatingDtoList);
		return userReviewResultDto;
	}

	@Override
	public List<UserReviewResultDto> getUserReviewBasedOnData(final String entityId, final String entityType, final Integer startIndex,
			final Integer pageSize, final String sortByType, final String searchKeyword) throws ValidationException {
		List<UserReviewResultDto> userReviewResultDtolist = new ArrayList<>();
		
		List<UserReview> userReviewList = iUserReviewDao.getUserReviewList(null, entityId, entityType, startIndex, pageSize, sortByType, searchKeyword);
		
		Map<String, List<UserReview>> reviewsGroupByUserID = userReviewList.stream().collect(Collectors.groupingBy(UserReview::getUserId));
		
		reviewsGroupByUserID.forEach((k,v) -> {
			try {
				UserDto userDto = iUsersService.getUserById(k);	
				v.stream().forEach(userReview -> {
					UserReviewResultDto userReviewResultDto = new UserReviewResultDto();
					BeanUtils.copyProperties(userReview, userReviewResultDto);
					List<UserReviewRating> userReviewRatings = iUserReviewDao.getUserReviewRatings(userReview.getId());
					List<UserReviewRatingDto> userReviewRatingDtos = new ArrayList<>();
					for (UserReviewRating userReviewRating : userReviewRatings) {
						UserReviewRatingDto userReviewRatingDto = new UserReviewRatingDto();
						BeanUtils.copyProperties(userReviewRating, userReviewRatingDto);
						userReviewRatingDtos.add(userReviewRatingDto);
					}
					userReviewResultDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
					userReviewResultDto.setRatings(userReviewRatingDtos);
					userReviewResultDtolist.add(userReviewResultDto);
				});
				
			} catch (ValidationException e) {
			}
		});
		return userReviewResultDtolist;
	}

	@Override
	public List<UserReviewResultDto> getUserReviewList() throws ValidationException {
		List<UserReviewResultDto> userReviewResultDtoList = new ArrayList<>();
		List<UserReview> userReviewList = iUserReviewDao.getUserReviewList(null, null, null, null, null, null, null);
		
		Map<String, List<UserReview>> reviewsGroupByUserID = userReviewList.stream().collect(Collectors.groupingBy(UserReview::getUserId));
		reviewsGroupByUserID.forEach((k,v) -> {
			try {
				UserDto userDto = iUsersService.getUserById(k);	
				v.stream().forEach(userReview -> {
					UserReviewResultDto reviewResultDto = new UserReviewResultDto();
					BeanUtils.copyProperties(userReview, reviewResultDto);
					reviewResultDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
					userReviewResultDtoList.add(reviewResultDto);
				});
				
			} catch (ValidationException e) {
			}
		});
		return userReviewResultDtoList;
	}

	@Override
	public UserReviewResultDto getUserAverageReviewBasedOnData(final String entityId, final String entityType) throws ValidationException {
		List<Object> objectList = iUserReviewDao.getUserAverageReview(entityId, entityType);
		UserReviewResultDto userReviewResultDto = new UserReviewResultDto();
		userReviewResultDto.setEntityId(entityId);
		userReviewResultDto.setEntityType(entityType);
		List<UserReviewRatingDto> resultList = new ArrayList<>();
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			UserReviewRatingDto userReviewRatingDto = new UserReviewRatingDto();
			ReviewQuestionsDto reviewQuestionsDto = iReviewQuestionService.getReviewQuestion((String) obj1[0]);
			BeanUtils.copyProperties(reviewQuestionsDto, userReviewRatingDto);
			userReviewRatingDto.setReviewQuestionId((String) obj1[0]);
			userReviewRatingDto.setRating((double) Math.round((Double) obj1[1]));
			resultList.add(userReviewRatingDto);
		}
		userReviewResultDto.setRatings(resultList);
		userReviewResultDto.setReviewStar((double)Math.round((Double)iUserReviewDao.getReviewStar(entityId, entityType)));
		return userReviewResultDto;
	}

	@Override
	public Map<String, Double> getUserAverageReviewBasedOnDataList(final List<String> entityIdList, final String entityType) {
		return iUserReviewDao.getUserAverageReviewList(entityIdList, entityType);
	}

	@Override
	public void deleteUserReview(final String userReviewId) throws ValidationException {
		UserReview userReview = iUserReviewDao.getUserReview(userReviewId);
		if (userReview != null) {
			userReview.setIsActive(false);
			iUserReviewDao.save(userReview);
		} else {
			throw new ValidationException("User review is not found for id " + userReviewId);
		}
	}

	@Override
	public int getUserReviewCount(final String userId, final String entityId, final String entityType, final String searchKeyword) {
		return iUserReviewDao.getUserReviewCount(userId, entityId, entityType, searchKeyword);
	}

}
