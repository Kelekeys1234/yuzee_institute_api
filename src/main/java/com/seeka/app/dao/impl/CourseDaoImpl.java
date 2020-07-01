package com.seeka.app.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseIntake;
import com.seeka.app.bean.CourseLanguage;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.dao.CourseDAO;
import com.seeka.app.dao.CurrencyRateDAO;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.dao.LevelDAO;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseDeliveryModesDto;
import com.seeka.app.dto.CourseDTOElasticSearch;
import com.seeka.app.dto.CourseDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.FacultyDto;
import com.seeka.app.dto.GlobalFilterSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.LevelDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.CourseSortBy;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.repository.CourseRepository;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.ConvertionUtil;
import com.seeka.app.util.PaginationUtil;

@Component
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class CourseDaoImpl implements CourseDAO {

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private LevelDAO levelDAO;

	@Autowired
	private IFacultyDAO dao;

	@Autowired
	private CurrencyRateDAO currencyRateDao;
	
	@Value("${s3.url}")
	private String s3URL;
	
	Function<String,String> addQuotes =  s -> "\'" + s + "\'";

	@Override
	public void save(final Course course) {
		courseRepository.save(course);
	}

	@Override
	public void update(final Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.update(course);
	}

	@Override
	public Course get(final String courseId) {
		return courseRepository.findById(courseId).get();
	}

	@Override
	public List<Course> getAll() {
		return courseRepository.findAll();
	}

	@Override
	public int getCountforNormalCourse(final CourseSearchDto courseSearchDto, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select count(*) from course crs inner join institute inst "
				+ " on crs.institute_id = inst.id"
				+ " where 1=1 and crs.is_active=1 and crs.id not in (select umc.course_id from user_my_course umc where umc.user_id='"
				+ courseSearchDto.getUserId() + "')";
		if (null != courseSearchDto.getInstituteId()) {
			sqlQuery += " and inst.id ='" + courseSearchDto.getInstituteId() + "'";
		}

		if (null != courseSearchDto.getCountryNames() && !courseSearchDto.getCountryNames().isEmpty()) {
			sqlQuery += " and inst.country_name in ('" + courseSearchDto.getCountryNames().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getCityNames() && !courseSearchDto.getCityNames().isEmpty()) {
			sqlQuery += " and inst.city_name in ('" + courseSearchDto.getCityNames().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and crs.level_id in ('" + courseSearchDto.getLevelIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in ('" + courseSearchDto.getFacultyIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getCourseKeys() && !courseSearchDto.getCourseKeys().isEmpty()) {
			sqlQuery += " and crs.name in (" + courseSearchDto.getCourseKeys().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}
		if (null != courseSearchDto.getCourseName() && !courseSearchDto.getCourseName().isEmpty()) {
			sqlQuery += " and crs.name like '%" + courseSearchDto.getCourseName().trim() + "%'";
		}

		if (searchKeyword != null) {
			sqlQuery += " and ( inst.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or inst.country_name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or crs.name like '%" + searchKeyword.trim() + "%' )";
		}
		Query query = session.createSQLQuery(sqlQuery);
		return ((Number) query.getSingleResult()).intValue();
	}

	@Override
	public List<CourseResponseDto> getAllCoursesByFilter(final CourseSearchDto courseSearchDto, final String searchKeyword, final List<String> courseIds,
			final Integer startIndex, final boolean uniqueCourseName) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select distinct crs.id as courseId, crs.name as courseName, inst.id as instId, inst.name as instName, crs.cost_range,"
				+ " crs.currency, cai.duration, cai.duration_time, crs.world_ranking, crs.stars, crs.recognition,"
				+ " cai.domestic_fee, cai.international_fee, crs.remarks, cai.usd_domestic_fee, cai.usd_international_fee,"
				+ " crs.updated_on, crs.is_active ,inst.latitude as latitude,inst.longitude as longitute,inst.country_name as countryName,"
				+ " inst.city_name as cityName, cai.delivery_type, cai.study_mode from course crs inner join institute inst  on crs.institute_id = inst.id "
				+ " left join course_delivery_modes cai on cai.course_id = crs.id"
				+ " where 1=1 and crs.is_active=1 and crs.id not in (select umc.course_id from user_my_course umc where umc.user_id='"
				+ courseSearchDto.getUserId() + "')";

		boolean showIntlCost = false;
		if (null != courseSearchDto.getInstituteId()) {
			sqlQuery += " and inst.id ='" + courseSearchDto.getInstituteId() +"'";
		}

		if (null != courseSearchDto.getCountryNames() && !courseSearchDto.getCountryNames().isEmpty()) {
			sqlQuery += " and inst.country_name in (" + courseSearchDto.getCountryNames().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}

		if (null != courseSearchDto.getCityNames() && !courseSearchDto.getCityNames().isEmpty()) {
			sqlQuery += " and inst.city_name in (" + courseSearchDto.getCityNames().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and crs.level_id in (" + courseSearchDto.getLevelIds().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in (" + courseSearchDto.getFacultyIds().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}

		if (null != courseSearchDto.getCourseName() && !courseSearchDto.getCourseName().isEmpty()) {
			sqlQuery += " and crs.name like '%" + courseSearchDto.getCourseName().trim() + "%'";
		}

		if (courseIds != null) {
			sqlQuery += " and crs.id in (" + courseIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}

		if (searchKeyword != null) {
			sqlQuery += " and ( inst.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or inst.country_name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or crs.name like '%" + searchKeyword.trim() + "%' )";
		}

		if (uniqueCourseName) {
			sqlQuery += " group by crs.name ";
		}

		sqlQuery += " ";
		String sortingQuery = "";
		String sortTypeValue = "ASC";
		if (!courseSearchDto.getSortAsscending()) {
			sortTypeValue = "DESC";
		}
		if (courseSearchDto.getSortBy() != null && !courseSearchDto.getSortBy().isEmpty()) {
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.DURATION.toString())) {
				sortingQuery = sortingQuery + " ORDER BY cai.duration " + sortTypeValue + " ";
			} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.RECOGNITION.toString())) {
				sortingQuery = sortingQuery + " ORDER BY crs.recognition " + sortTypeValue + " ";
			} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.LOCATION.toString())) {
				sortingQuery = sortingQuery + " ORDER BY inst.country_name " + sortTypeValue + " ";
			} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.PRICE.toString())) {
				sortingQuery = sortingQuery + " ORDER BY IF(crs.currency='" + courseSearchDto.getCurrencyCode()
						+ "', cai.usd_domestic_fee, cai.usd_international_fee) " + sortTypeValue + " ";
			} else if (courseSearchDto.getSortBy().equalsIgnoreCase("instituteName")) {
				sortingQuery = " order by inst.name " + sortTypeValue.toLowerCase();
			} else if (courseSearchDto.getSortBy().equalsIgnoreCase("countryName")) {
				sortingQuery = " order by inst.country_name " + sortTypeValue.toLowerCase();
			} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.NAME.name())) {
				sortingQuery = " order by crs.name " + sortTypeValue.toLowerCase();
			}
		} else {
			sortingQuery = " order by cai.international_fee " + sortTypeValue.toLowerCase();

		}

		if (startIndex != null && courseSearchDto.getMaxSizePerPage() != null) {
			sqlQuery += sortingQuery + " LIMIT " + startIndex + " ," + courseSearchDto.getMaxSizePerPage();
		} else {
			sqlQuery += sortingQuery;
		}
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();

		List<CourseResponseDto> list = new ArrayList<>();
		List<CourseDeliveryModesDto> additionalInfoDtos = new ArrayList<>(); 
		CourseResponseDto courseResponseDto = null;
		Long localFees = 0l, intlFees = 0l;
		String newCurrencyCode = "";
		for (Object[] row : rows) {
			try {
				CourseDeliveryModesDto additionalInfoDto = new CourseDeliveryModesDto();
				Double localFeesD = null;
				Double intlFeesD = null;
				if (row[11] != null) {
					localFeesD = Double.valueOf(String.valueOf(row[11]));
				}
				if (row[12] != null) {
					intlFeesD = Double.valueOf(String.valueOf(row[12]));
				}
				newCurrencyCode = String.valueOf(row[5]);
				if (localFeesD != null) {
					localFees = ConvertionUtil.roundOffToUpper(localFeesD);
				}
				if (intlFeesD != null) {
					intlFees = ConvertionUtil.roundOffToUpper(intlFeesD);
				}
				courseResponseDto = new CourseResponseDto();
				if (showIntlCost) {
					courseResponseDto.setCost(intlFees + " " + newCurrencyCode);
				} else {
					courseResponseDto.setCost(localFees + " " + newCurrencyCode);
				}
				courseResponseDto.setLatitude((Double) row[18]);
				courseResponseDto.setLongitude((Double) row[19]);
				courseResponseDto.setCountryName(String.valueOf(row[20]));
				courseResponseDto.setCityName(String.valueOf(row[21]));
				courseResponseDto.setId(String.valueOf(row[0]));
				courseResponseDto.setName(String.valueOf(row[1]));
				courseResponseDto.setInstituteId(String.valueOf(row[2]));
				courseResponseDto.setInstituteName(String.valueOf(row[3]));
				additionalInfoDto.setDuration(Integer.valueOf(String.valueOf(row[6])));
				additionalInfoDto.setDurationTime(String.valueOf(row[7]));
				courseResponseDto.setLocation(String.valueOf(row[21]) + ", " + String.valueOf(row[20]));

				Integer worldRanking = 0;
				if (null != row[8]) {
					worldRanking = Double.valueOf(String.valueOf(row[8])).intValue();
				}
				courseResponseDto.setCourseRanking(worldRanking);
				courseResponseDto.setStars(Double.valueOf(String.valueOf(row[9])));
				if (courseSearchDto.getCurrencyCode() != null && !courseSearchDto.getCurrencyCode().isEmpty()) {
					courseResponseDto.setCurrencyCode(courseSearchDto.getCurrencyCode());
					if (row[14] != null) {
						CurrencyRate currencyRate = currencyRateDao.getCurrencyRateByCurrencyCode(courseSearchDto.getCurrencyCode());
						Double amt = Double.valueOf(row[14].toString());
						Double convertedRate = amt * currencyRate.getConversionRate();
						if (convertedRate != null) {
							additionalInfoDto.setDomesticFee(CommonUtil.foundOff2Digit(convertedRate));
						}
					}
					if (row[15] != null) {
						CurrencyRate currencyRate = currencyRateDao.getCurrencyRateByCurrencyCode(courseSearchDto.getCurrencyCode());
						Double amt = Double.valueOf(row[15].toString());
						Double convertedRate = amt * currencyRate.getConversionRate();
						if (convertedRate != null) {
							additionalInfoDto.setInternationalFee(CommonUtil.foundOff2Digit(convertedRate));
						}
					}
				} else {
					if (row[14] != null) {
						additionalInfoDto.setDomesticFee(CommonUtil.foundOff2Digit(Double.valueOf(row[14].toString())));
					}
					if (row[15] != null) {
						additionalInfoDto.setInternationalFee(CommonUtil.foundOff2Digit(Double.valueOf(row[15].toString())));
					}
				}
				if (row[5] != null) {
					courseResponseDto.setCurrencyCode(row[5].toString());
				}
				courseResponseDto.setUpdatedOn((Date) row[16]);
				if (String.valueOf(row[17]) != null && String.valueOf(row[17]).equals("1")) {
					courseResponseDto.setIsActive(true);
				} else {
					courseResponseDto.setIsActive(false);
				}
				additionalInfoDto.setDeliveryType(row[22].toString());
				additionalInfoDto.setStudyMode(row[23].toString());
				additionalInfoDto.setCourseId(String.valueOf(row[0]));
				additionalInfoDtos.add(additionalInfoDto);
				courseResponseDto.setCourseAdditionalInfo(additionalInfoDtos);
				list.add(courseResponseDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<CourseResponseDto> getAllCoursesByInstitute(final String instituteId, final CourseSearchDto courseSearchDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ " inst.id as instId,inst.name as instName,"
				+ " crs.cost_range, crs.currency, inst.city_name as cityName,"
				+ " inst.country_name as countryName,crs.world_ranking,crs.stars,crs.recognition"
				+ " from course crs  inner join institute inst "
				+ " on crs.institute_id = inst.id "
				+ " inner join faculty f  on f.id = crs.faculty_id "
				+ " inner join course_delivery_modes cai on cai.course_id = crs.id"
				+ " left join institute_service iis  on iis.institute_id = inst.id where crs.institute_id = '" + instituteId + "'";

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and f.level_id in ('" + StringUtils.join(courseSearchDto.getLevelIds(), ',') + "')";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in ('" + StringUtils.join(courseSearchDto.getFacultyIds(), ',') + "')";
		}

		if (null != courseSearchDto.getCourseKeys() && !courseSearchDto.getCourseKeys().isEmpty()) {
			String value = "";
			int i = 0;
			for (String key : courseSearchDto.getCourseKeys()) {
				if (null == key || key.isEmpty()) {
					continue;
				}
				if (i == 0) {
					value = "'" + key.trim() + "'";
				} else {
					value = value + "," + "'" + key.trim() + "'";
				}
				i++;
			}
			sqlQuery += " and crs.name in (" + value + ")";
		}

		if (null != courseSearchDto.getMinCost() && courseSearchDto.getMinCost() >= 0) {
			sqlQuery += " and crs.cost_range >= " + courseSearchDto.getMinCost();
		}

		if (null != courseSearchDto.getMaxCost() && courseSearchDto.getMaxCost() >= 0) {
			sqlQuery += " and crs.cost_range <= " + courseSearchDto.getMaxCost();
		}

		if (null != courseSearchDto.getMinDuration() && courseSearchDto.getMinDuration() >= 0) {
			sqlQuery += " and cast(cai.duration as DECIMAL(9,2)) >= " + courseSearchDto.getMinDuration();
		}

		if (null != courseSearchDto.getMaxDuration() && courseSearchDto.getMaxDuration() >= 0) {
			sqlQuery += " and cast(cai.duration as DECIMAL(9,2)) <= " + courseSearchDto.getMaxDuration();
		}

		if (null != courseSearchDto.getSearchKey() && !courseSearchDto.getSearchKey().isEmpty()) {
			sqlQuery += " and crs.name like '%" + courseSearchDto.getSearchKey().trim() + "%'";
		}
		sqlQuery += ") A ";

		String sortingQuery = "";
		if (null != courseSearchDto.getSortingObj()) {
			CourseSearchFilterDto sortingObj = courseSearchDto.getSortingObj();
			if (null != sortingObj.getPrice() && !sortingObj.getPrice().isEmpty()) {
				if (sortingObj.getPrice().equals("ASC")) {
					sortingQuery = " order by A.cost_range asc";
				} else {
					sortingQuery = " order by A.cost_range desc";
				}
			}

			if (null != sortingObj.getLocation() && !sortingObj.getLocation().isEmpty()) {
				if (sortingObj.getLocation().equals("ASC")) {
					sortingQuery = " order by A.countryName, A.cityName asc";
				} else {
					sortingQuery = " order by A.countryName, A.cityName desc";
				}
			}

			if (null != sortingObj.getDuration() && !sortingObj.getDuration().isEmpty()) {
				if (sortingObj.getDuration().equals("ASC")) {
					sortingQuery = " order by cai.duration asc";
				} else {
					sortingQuery = " order by cai.duration desc";
				}
			}

			if (null != sortingObj.getRecognition() && !sortingObj.getRecognition().isEmpty()) {
				if (sortingObj.getRecognition().equals("ASC")) {
					sortingQuery = " order by A.recognition asc";
				} else {
					sortingQuery = " order by A.recognition desc";
				}
			}
		} else {
			sortingQuery = " order by A.cost_range asc";
		}
		sqlQuery += sortingQuery + " OFFSET (" + courseSearchDto.getPageNumber() + "-1)*" + courseSearchDto.getMaxSizePerPage() + " ROWS FETCH NEXT "
				+ courseSearchDto.getMaxSizePerPage() + " ROWS ONLY";

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> list = new ArrayList<>();
		CourseResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new CourseResponseDto();
			obj.setId(String.valueOf(row[0]));
			obj.setName(String.valueOf(row[1]));
			obj.setCost(String.valueOf(row[4]) + " " + String.valueOf(row[5]));
			Integer worldRanking = 0;
			if (null != row[8]) {
				worldRanking = Double.valueOf(String.valueOf(row[8])).intValue();
			}
			obj.setCourseRanking(Integer.valueOf(worldRanking.toString()));
			obj.setStars(Double.valueOf(String.valueOf(row[9])));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[11])));
			list.add(obj);
		}
		return list;
	}

	public CourseResponseDto getCourse(final String instituteId, final CourseSearchDto courseSearchDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ "inst.id as instId,inst.name as instName,"
				+ " crs.cost_range,crs.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.language,crs.stars,crs.recognition,crs.domestic_fee,crs.international_fee "
				+ "from course crs inner join institute inst "
				+ " on crs.institute_id = inst.id inner join country ctry  on ctry.id = inst.country_id inner join "
				+ "city ci  on ci.id = inst.city_id inner join faculty f  on f.id = crs.faculty_id "
				+ "left join institute_service iis  on iis.institute_id = inst.id where crs.institute_id = " + instituteId;

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and f.level_id in ('" + StringUtils.join(courseSearchDto.getLevelIds(), ',') + "')";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in ('" + StringUtils.join(courseSearchDto.getFacultyIds(), ',') + "')";
		}

		if (null != courseSearchDto.getCourseKeys() && !courseSearchDto.getCourseKeys().isEmpty()) {
			String value = "";
			int i = 0;
			for (String key : courseSearchDto.getCourseKeys()) {
				if (null == key || key.isEmpty()) {
					continue;
				}
				if (i == 0) {
					value = "'" + key.trim() + "'";
				} else {
					value = value + "," + "'" + key.trim() + "'";
				}
				i++;
			}
			sqlQuery += " and crs.name in (" + value + ")";
		}

		if (null != courseSearchDto.getMinCost() && courseSearchDto.getMinCost() >= 0) {
			sqlQuery += " and crs.cost_range >= " + courseSearchDto.getMinCost();
		}

		if (null != courseSearchDto.getMaxCost() && courseSearchDto.getMaxCost() >= 0) {
			sqlQuery += " and crs.cost_range <= " + courseSearchDto.getMaxCost();
		}

		if (null != courseSearchDto.getMinDuration() && courseSearchDto.getMinDuration() >= 0) {
			sqlQuery += " and cast(crs.duration as DECIMAL(9,2)) >= " + courseSearchDto.getMinDuration();
		}

		if (null != courseSearchDto.getMaxDuration() && courseSearchDto.getMaxDuration() >= 0) {
			sqlQuery += " and cast(crs.duration as DECIMAL(9,2)) <= " + courseSearchDto.getMaxDuration();
		}

		if (null != courseSearchDto.getSearchKey() && !courseSearchDto.getSearchKey().isEmpty()) {
			sqlQuery += " and crs.name like '%" + courseSearchDto.getSearchKey().trim() + "%'";
		}
		sqlQuery += ") A ";

		String sortingQuery = "";
		if (null != courseSearchDto.getSortingObj()) {
			CourseSearchFilterDto sortingObj = courseSearchDto.getSortingObj();
			if (null != sortingObj.getPrice() && !sortingObj.getPrice().isEmpty()) {
				if (sortingObj.getPrice().equals("ASC")) {
					sortingQuery = " order by A.cost_range asc";
				} else {
					sortingQuery = " order by A.cost_range desc";
				}
			}

			if (null != sortingObj.getLocation() && !sortingObj.getLocation().isEmpty()) {
				if (sortingObj.getLocation().equals("ASC")) {
					sortingQuery = " order by A.countryName, A.cityName asc";
				} else {
					sortingQuery = " order by A.countryName, A.cityName desc";
				}
			}

			if (null != sortingObj.getDuration() && !sortingObj.getDuration().isEmpty()) {
				if (sortingObj.getDuration().equals("ASC")) {
					sortingQuery = " order by A.duration asc";
				} else {
					sortingQuery = " order by A.duration desc";
				}
			}

			if (null != sortingObj.getRecognition() && !sortingObj.getRecognition().isEmpty()) {
				if (sortingObj.getRecognition().equals("ASC")) {
					sortingQuery = " order by A.recognition asc";
				} else {
					sortingQuery = " order by A.recognition desc";
				}
			}
		} else {
			sortingQuery = " order by A.cost_range asc";
		}
		sqlQuery += sortingQuery + " OFFSET (" + courseSearchDto.getPageNumber() + "-1)*" + courseSearchDto.getMaxSizePerPage() + " ROWS FETCH NEXT "
				+ courseSearchDto.getMaxSizePerPage() + " ROWS ONLY";

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> list = new ArrayList<>();
		CourseResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new CourseResponseDto();
			obj.setId(String.valueOf(row[0]));
			obj.setName(String.valueOf(row[1]));
			obj.setCost(String.valueOf(row[4]) + " " + String.valueOf(row[5]));
//			obj.setDuration(Double.valueOf(String.valueOf(row[6])));
//			obj.setDurationTime(String.valueOf(row[7]));
			obj.setCourseRanking(Integer.valueOf(String.valueOf(row[12])));
			obj.setLanguageShortKey(String.valueOf(row[13]));
			obj.setStars(Double.valueOf(String.valueOf(row[14])));
//			obj.setDomesticFee(Double.valueOf(String.valueOf(row[16])));
//			obj.setInternationalFee(Double.valueOf(String.valueOf(row[17])));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[18])));
			list.add(obj);
		}
		return obj;
	}

	@Override
	public Map<String, Object> getCourse(final String courseId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select crs.id as course_id,crs.stars as course_stars,crs.name as course_name,crs.language as course_language,crs.description as course_description,"
						+ "crs.duration as course_duration,crs.duration_time as course_duration_time,crs.world_ranking as world_ranking,ins.id as institute_id,"
						+ "ins.name as institute_name,ins.email as institute_email,ins.phone_number as institute_phone_number,"
						+ "ins.longitute as longitude,ins.latitute as latitute,ins.total_student as ins_total_student,ins.website as ins_website,"
						+ "ins.address as ins_address, crs.currency,crs.cost_range,crs.international_fee,crs.domestic_fee,cty.name as city_name,cntry.name as country_name,"
						+ "fty.name as faulty_name,le.id as level_id,le.name as level_name,cty.id as city_id,cntry.id as country_id,ins.about_us_info as about_us,"
						+ "ins.closing_hour as closing_hour,ins.opening_hour as opening_hour,cj.available_jobs as available_job,cd.student_visa_link as student_visa_link,crs.remarks,crs.intake from course crs "
						+ "left join institute ins  on ins.id = crs.institute_id left join country cntry  on cntry.id = inst.country_id left join city cty "
						+ "on cty.id = inst.city_id left join faculty fty  on fty.id = crs.faculty_id "
						+ "left join level le  on crs.level_id = le.id left join city_jobs cj on cty.id=cj.city_id "
						+ "left join country_details cd on cd.country_id=cntry.id where crs.id = " + courseId);

		List<Object[]> rows = query.list();
		InstituteResponseDto instituteObj = null;
		CourseDto courseObj = null;
		Map<String, Object> map = new HashMap<>();
		for (Object[] row : rows) {
			courseObj = new CourseDto();
			courseObj.setId(String.valueOf(row[0]));
			courseObj.setStars(String.valueOf(row[1]));
			courseObj.setName(String.valueOf(row[2]));
//			courseObj.setLanguage(String.valueOf(row[3]));
			courseObj.setDescription(String.valueOf(row[4]));
//			courseObj.setDuration(String.valueOf(row[5]));
//			courseObj.setDurationTime(String.valueOf(row[6]));
			courseObj.setWorldRanking(String.valueOf(row[7]));
//			if (row[19] != null) {
//				courseObj.setInternationalFee(Double.valueOf(String.valueOf(row[19])));
//			}
//			if (row[20] != null) {
//				courseObj.setDomasticFee(Double.valueOf(String.valueOf(row[20])));
//			}
			courseObj.setCost(String.valueOf(row[18]) + " " + String.valueOf(row[17]));
			courseObj.setFacultyName(String.valueOf(row[23]));
			if (row[24] != null) {
				courseObj.setLevelId(String.valueOf(row[24]));
			}
			courseObj.setLevelName(String.valueOf(row[25]));
//			courseObj.setIntakeDate(String.valueOf(row[34]));
			courseObj.setRemarks(String.valueOf(row[33]));

			instituteObj = new InstituteResponseDto();
			if (row[8] != null) {
				instituteObj.setId(String.valueOf(row[8]));
			}
			instituteObj.setName(String.valueOf(row[9]));
			if (row[7] != null) {
				instituteObj.setWorldRanking(Integer.valueOf(String.valueOf(row[7])));
			}
			instituteObj.setEmail(String.valueOf(row[10]));
			instituteObj.setPhoneNumber(String.valueOf(row[11]));
			instituteObj.setLongitude(Double.valueOf(String.valueOf(row[12])));
			instituteObj.setLatitude(Double.valueOf(String.valueOf(row[13])));
			if (row[14] != null) {
				instituteObj.setTotalStudent(Integer.parseInt(String.valueOf(row[14])));
			}
			instituteObj.setWebsite(String.valueOf(row[15]));
			instituteObj.setAddress(String.valueOf(row[16]));
			instituteObj.setVisaRequirement(String.valueOf(row[32]));
			instituteObj.setAboutUs(String.valueOf(row[28]));
			instituteObj.setLocation(String.valueOf(row[21]) + "," + String.valueOf(row[22]));
			instituteObj.setCountryName(String.valueOf(row[22]));
			instituteObj.setCityName(String.valueOf(row[21]));
			instituteObj.setTotalAvailableJobs(String.valueOf(row[31]));
			map.put("courseObj", courseObj);
			map.put("instituteObj", instituteObj);
		}
		return map;
	}

	public static void main(final String[] args) {
		List<Integer> list = new ArrayList<>();

		list.add(14);
		list.add(450);
		list.add(780);
		System.out.println(list);
	}

	// This is not recommended 
	@Override
	public List<CourseResponseDto> getCouresesByFacultyId(final String facultyId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.createAlias("faculty", "faculty");
		crit.add(Restrictions.eq("faculty.id", facultyId));
		crit.addOrder(Order.asc("course.name"));
		List<Course> courses = crit.list();
		List<CourseResponseDto> dtos = new ArrayList<>();
		for (Course course : courses) {
			CourseResponseDto courseObj = new CourseResponseDto();
			courseObj.setId(course.getId());
			courseObj.setStars(Double.valueOf(course.getStars()));
			courseObj.setName(course.getName());
			courseObj.setCurrencyCode(course.getCurrency());
			if(!ObjectUtils.isEmpty(course.getFaculty())) {
				courseObj.setFacultyName(course.getFaculty().getName());
				courseObj.setFacultyId(course.getFaculty().getId());
			}
			courseObj.setCourseRanking(course.getWorldRanking());
			dtos.add(courseObj);
		}
		return dtos;
	}

	@Override
	public List<CourseResponseDto> getCouresesByListOfFacultyId(final String facultyId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select distinct c.id, c.name as name  from course c where c.faculty_id in (" + facultyId + ") ORDER BY c.name");
		List<Object[]> rows = query.list();
		List<CourseResponseDto> dtos = new ArrayList<>();
		CourseResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new CourseResponseDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			dtos.add(obj);
		}
		return dtos;
	}

	@Override
	public int findTotalCount() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id from course sa where sa.is_active = 1 and sa.deleted_on IS NULL";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public List<CourseRequest> getAll(final Integer pageNumber, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select c.id , c.institute_id, i.country_name, i.city_name, c.faculty_id, c.name," +
				" c.description, c.intake, c.availabilty, c.created_by, c.updated_by, c.campus_location, c.website," +
				" c.recognition_type, c.abbreviation, c.updated_on, c.world_ranking, c.stars, c.remarks FROM course c" +
				" inner join institute i on c.institute_id = i.id where c.is_active = 1 and c.deleted_on IS NULL ORDER BY c.created_on DESC ";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseRequest> courses = getCourseDetails(rows, session);
		return courses;
	}

	private List<CourseRequest> getCourseDetails(final List<Object[]> rows, final Session session) {
		List<CourseRequest> courses = new ArrayList<>();
		for (Object[] row : rows) {
			CourseRequest obj = new CourseRequest();
			obj.setId(row[0].toString());
			
			if (row[1] != null) {
				obj.setInstituteId(row[1].toString());
				obj.setInstituteName(getInstituteName(row[1].toString(), session));
				obj.setCost(getCost(row[1].toString(), session));
				Institute institute = getInstitute(row[1].toString(), session);
				obj.setInstituteId(row[1].toString());
				obj.setInstituteName(institute.getName());
			}
			
			if(row[2] != null && row[3] != null) {
				obj.setLocation(row[3].toString() + ", " + row[2].toString());
			}
			if (row[2] != null) {
				obj.setCountryName(row[2].toString());
			}
			if (row[3] != null) {
				obj.setCityName(row[3].toString());
			}
			if (row[4] != null) {
				obj.setFacultyId(row[4].toString());
			}
			if (row[5] != null) {
				obj.setName(row[5].toString());
			}
			if (row[6] != null) {
				obj.setDescription(row[6].toString());
			}
			if (row[12] != null) {
				obj.setWebsite(row[12].toString());
			}
			if (row[14] != null) {
				obj.setLink(row[14].toString());
			}
			if (row[15] != null) {
				System.out.println(row[15].toString());
				Date updatedDate = (Date) row[15];
				System.out.println(updatedDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(updatedDate);
				obj.setLastUpdated(dateResult);
			}
			if (row[16] != null) {
				obj.setWorldRanking(row[16].toString());
			}
			if (row[17] != null) {
				obj.setStars(row[17].toString());
			}
			if (row[18] != null) {
				obj.setRequirements(row[18].toString());
			}
			courses.add(obj);
		}
		return courses;
	}

	private String getInstituteName(final String id, final Session session) {
		String name = null;
		if (id != null) {
			Institute obj = session.get(Institute.class, id);
			name = obj.getName();
		}
		return name;
	}

	private Institute getInstitute(final String id, final Session session) {
		Institute obj = null;
		if (id != null) {
			obj = session.get(Institute.class, id);
		}
		return obj;
	}

	@Override
	public List<CourseRequest> getUserCourse(final String userId, final Integer pageNumber, final Integer pageSize, final String currencyCode,
			final String sortBy, final boolean sortType) throws ValidationException {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select c.id , c.institute_id, i.country_name , i.city_name, c.faculty_id, c.name ,"
				+ " c.description, c.availabilty, c.created_by, c.updated_by, c.campus_location, c.website,"
				+ " c.recognition_type, c.abbreviation, c.updated_on, c.world_ranking, c.stars, c.remarks, c.currency,"
				+ " i.latitude, i.longitude FROM user_my_course umc inner join course c on umc.course_id = c.id"
				+ " left join institute i on c.institute_id = i.id where umc.is_active = 1 and c.is_active = 1 and umc.deleted_on"
				+ " IS NULL and umc.user_id = '"+ userId + "'";
		if (sortBy != null && "institute_name".contentEquals(sortBy)) {
			sqlQuery = sqlQuery + " ORDER BY i.name " + (sortType ? "ASC" : "DESC");
		} else if (sortBy != null) {
			sqlQuery = sqlQuery + " ORDER BY c." + sortBy + " " + (sortType ? "ASC" : "DESC");
		} else {
			sqlQuery = sqlQuery + " ORDER BY c.created_on DESC";
		}
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseRequest> courses = new ArrayList<>();
		CourseRequest obj = null;

		CurrencyRate curencyRate = currencyRateDao.getCurrencyRateByCurrencyCode(currencyCode);
		if (curencyRate == null || curencyRate.getConversionRate() == null || curencyRate.getConversionRate() == 0) {
			throw new ValidationException("Either No Currency found or Conversion rate is 0 for specified currency - " + currencyCode);
		}
//		double conversionRate = curencyRate.getConversionRate();

		for (Object[] row : rows) {
			obj = new CourseRequest();
			obj.setId(row[0].toString());
			if (row[1] != null) {
				Institute institute = getInstitute(row[1].toString(), session);
				obj.setInstituteId(row[1].toString());
				obj.setInstituteName(institute.getName());
				obj.setCost(getCost(row[1].toString(), session));
			}
			if (row[2] != null) {
				obj.setCountryName(row[2].toString());
			}
            obj.setCityName(row[3].toString());
            obj.setLocation(row[3].toString() + ", " + row[2].toString());
			obj.setFacultyId(row[4].toString());
			obj.setName(row[5].toString());
			if (row[6] != null) {
				obj.setDescription(row[6].toString());
			}
			if (row[7] != null) {
				obj.setAvailbility(row[7].toString());
			}
			if (row[10] != null) {
				obj.setCampusLocation(row[10].toString());
			}
			if (row[11] != null) {
				obj.setWebsite(row[11].toString());
			}
			if (row[12] != null) {
				obj.setRecognitionType(row[12].toString());
			}
			if (row[13] != null) {
				obj.setAbbreviation(row[13].toString());
			}
			if (row[15] != null) {
				obj.setWorldRanking(row[15].toString());
			}
			if (row[16] != null) {
				obj.setStars(row[16].toString());
			}
			if (row[17] != null) {
				obj.setRequirements(row[17].toString());
			}
			if (row[18] != null) {
				obj.setCurrency(row[18].toString());
			}
			if (row[19] != null && !row[19].toString().isEmpty()) {
				obj.setLatitude(Double.parseDouble(row[19].toString()));
			}
			if (row[20] != null && !row[20].toString().isEmpty()) {
				obj.setLongitude(Double.parseDouble(row[20].toString()));
			}
			
			obj.setIntake(getCourseIntakeBasedOnCourseId(obj.getId()).stream().map(x -> x.getIntakeDates()).collect(Collectors.toList()));
			obj.setLanguage(getCourseLanguageBasedOnCourseId(obj.getId()).stream().map(x -> x.getLanguage()).collect(Collectors.toList()));
			courses.add(obj);
		}
		return courses;
	}

	private String getCost(final String instituteId, final Session session) {
		String cost = null;
		Query query = session.createSQLQuery("select c.id, c.avg_cost_of_living from institute c  where c.id='" + instituteId +"'");
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			if (row[1] != null) {
				cost = row[1].toString();
			}
		}
		return cost;
	}

	@Override
	public int findTotalCountByUserId(final String userId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select count(*) from  user_my_course umc inner join course c on umc.course_id = c.id where umc.is_active = 1 and c.is_active = 1 and umc.deleted_on IS NULL and umc.user_id='"+ userId + "'";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	public void saveUserCompareCourse(final UserCompareCourse compareCourse) {
		Session session = sessionFactory.getCurrentSession();
		session.save(compareCourse);
	}

	@Override
	public void saveUserCompareCourseBundle(final UserCompareCourseBundle compareCourseBundle) {
		Session session = sessionFactory.getCurrentSession();
		session.save(compareCourseBundle);
	}

	@Override
	public List<UserCompareCourse> getUserCompareCourse(final String userId) {
		List<UserCompareCourse> compareCourses = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select ucc.id, ucc.compare_value FROM  user_compare_course ucc where ucc.deleted_on IS NULL and ucc.user_id='" + userId +"'";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		UserCompareCourse obj = null;
		for (Object[] row : rows) {
			obj = new UserCompareCourse();
			obj.setId(row[0].toString());
			if (row[1] != null) {
				obj.setCompareValue(row[1].toString());
			}
			compareCourses.add(obj);
		}
		return compareCourses;
	}

	@Override
	public CourseRequest getCourseById(final String courseId) {
		Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select c.id , c.institute_id, i.country_name , i.city_name, c.faculty_id, c.name , "
				+ "c.description, c.intake, c.availabilty, c.created_by, c.updated_by, c.campus_location, c.website,"
				+ " c.recognition_type, c.abbreviation, c.updated_on, c.world_ranking, c.stars, c.remarks  FROM course c"
                + " left join institute i on c.institute_id=i.id where c.id='" + courseId + "'";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		CourseRequest courseRequest = null;
		for (Object[] row : rows) {
			courseRequest = new CourseRequest();
			courseRequest.setId(row[0].toString());
			if (row[1] != null) {
				courseRequest.setInstituteId(row[1].toString());
				courseRequest.setInstituteName(getInstituteName(row[1].toString(), session));
				Institute institute = getInstitute(row[1].toString(), session);
				courseRequest.setInstituteId(row[1].toString());
				courseRequest.setInstituteName(institute.getName());
				courseRequest.setCost(getCost(row[1].toString(), session));
			}
			if (row[2] != null) {
				courseRequest.setCountryName(row[2].toString());
			}
			
			if(row[3] != null) {
				courseRequest.setCityName(row[3].toString());
			}
			
			courseRequest.setFacultyId(row[4].toString());
			courseRequest.setName(row[5].toString());
			if (row[6] != null) {
				courseRequest.setDescription(row[6].toString());
			}
			if (row[7] != null) {
				courseRequest
						.setIntake(getCourseIntakeBasedOnCourseId(courseRequest.getId()).stream().map(x -> x.getIntakeDates()).collect(Collectors.toList()));
			}
			if (row[8] != null) {
				courseRequest.setAvailbility(row[8].toString());
			}
			if (row[11] != null) {
				courseRequest.setCampusLocation(row[11].toString());
			}
			if (row[12] != null) {
				courseRequest.setWebsite(row[12].toString());
			}
			if (row[13] != null) {
				courseRequest.setRecognitionType(row[13].toString());
			}
			
			if (row[14] != null) {
				courseRequest.setAbbreviation(row[14].toString());
			}
			if (row[15] != null) {
				courseRequest.setWorldRanking(row[15].toString());
			}
			if (row[16] != null) {
				courseRequest.setStars(row[16].toString());
			}
			if (row[17] != null) {
				courseRequest.setRequirements(row[17].toString());
			}
		}
		return courseRequest;
	}

	
	@Override
	public Course getCourseData(final String id) {
		Session session = sessionFactory.getCurrentSession();
		Course course = null;
		if (id != null) {
			course = session.get(Course.class, id);
		}
		return course;
	}

	@Override
	public int getCountOfAdvanceSearch(final Object... values) {
		AdvanceSearchDto courseSearchDto = (AdvanceSearchDto) values[0];
		GlobalFilterSearchDto globalSearchFilterDto = null;

		if (values.length > 1) {
			globalSearchFilterDto = (GlobalFilterSearchDto) values[1];
		}
		String sizeSqlQuery = "select count(*) from course crs inner join institute inst "
				+ " on crs.institute_id = inst.id"
				+ " left join course_delivery_modes cai on cai.course_id = crs.id"
				+ " left join institute_service iis  on iis.institute_id = inst.id where 1=1 and crs.is_active=1 and crs.id "
				+ " not in (select umc.course_id from user_my_course umc where umc.user_id='"
				+ courseSearchDto.getUserId() + "') ";
		if (globalSearchFilterDto != null && globalSearchFilterDto.getIds() != null && globalSearchFilterDto.getIds().size() > 0) {
			sizeSqlQuery = addConditionForCourseList(sizeSqlQuery, globalSearchFilterDto.getIds());
		}
		sizeSqlQuery = addCondition(sizeSqlQuery, courseSearchDto);
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sizeSqlQuery);
		return ((Number) query.getSingleResult()).intValue();
	}

	@Override
	public List<CourseResponseDto> advanceSearch(final Object... values) {
		AdvanceSearchDto courseSearchDto = (AdvanceSearchDto) values[0];
		GlobalFilterSearchDto globalSearchFilterDto = null;

		if (values.length > 1) {
			globalSearchFilterDto = (GlobalFilterSearchDto) values[1];
		}
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select distinct crs.id as courseId,crs.name as courseName, inst.id as instId,inst.name as instName, crs.cost_range,"
				+ " crs.currency,cai.duration,cai.duration_time,inst.city_name as cityName,"
				+ " inst.country_name as countryName,inst.world_ranking,crs.stars,crs.recognition, cai.domestic_fee, cai.international_fee,"
				+ " crs.remarks, usd_domestic_fee, usd_international_fee,inst.latitude as latitude,inst.longitude as longitude,"
				+ " cai.delivery_type as deliveryType, cai.study_mode"
				+ " from course crs inner join institute inst"
				+ " on crs.institute_id = inst.id"
				+ " left join course_delivery_modes cai on cai.course_id = crs.id"
				+ " inner join faculty f on f.id = crs.faculty_id"
				+ " left join institute_service iis on iis.institute_id = inst.id where 1=1 and crs.is_active=1 and crs.id not in"
				+ " (select umc.course_id from user_my_course umc where umc.user_id='"+ courseSearchDto.getUserId() + "')";

		if (globalSearchFilterDto != null && globalSearchFilterDto.getIds() != null && globalSearchFilterDto.getIds().size() > 0) {
			sqlQuery = addConditionForCourseList(sqlQuery, globalSearchFilterDto.getIds());
		}
		boolean showIntlCost = false;
		sqlQuery = addCondition(sqlQuery, courseSearchDto);
//		sqlQuery += " group by crs.id";

		String sortingQuery = "";
		if (courseSearchDto.getSortBy() != null && !courseSearchDto.getSortBy().isEmpty()) {
			sortingQuery = addSorting(sortingQuery, courseSearchDto);
		}
		if (courseSearchDto.getPageNumber() != null && courseSearchDto.getMaxSizePerPage() != null) {
			PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
			sqlQuery += sortingQuery + " LIMIT " + PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage()) + " ,"
					+ courseSearchDto.getMaxSizePerPage();
		} else {
			sqlQuery += sortingQuery;
		}
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();

		List<CourseResponseDto> list = new ArrayList<>();
		List<CourseDeliveryModesDto> additionalInfoDtos = new ArrayList<>();
		CourseResponseDto courseResponseDto = null;
		for (Object[] row : rows) {
			courseResponseDto = getCourseData(row, courseSearchDto, showIntlCost,additionalInfoDtos);
			list.add(courseResponseDto);
		}
		return list;
	}

	private CourseResponseDto getCourseData(final Object[] row, final AdvanceSearchDto courseSearchDto, final boolean showIntlCost, 
			List<CourseDeliveryModesDto> additionalInfoDtos) {
		CourseResponseDto courseResponseDto = null;
		CourseDeliveryModesDto additionalInfoDto = new CourseDeliveryModesDto();
		Long cost = 0l;
		String newCurrencyCode = "";
		Double costRange = null;
		if (row[4] != null) {
			costRange = Double.valueOf(String.valueOf(row[4]));
		}
		newCurrencyCode = String.valueOf(row[5]);
		if (costRange != null) {
			cost = ConvertionUtil.roundOffToUpper(costRange);
			System.out.println(newCurrencyCode);
			System.out.println(cost);
		}
		courseResponseDto = new CourseResponseDto();
		courseResponseDto.setId(String.valueOf(row[0]));
		courseResponseDto.setName(String.valueOf(row[1]));
		courseResponseDto.setInstituteId(String.valueOf(row[2]));
		courseResponseDto.setInstituteName(String.valueOf(row[3]));
		courseResponseDto.setCityName(String.valueOf(row[8]));
		courseResponseDto.setCountryName(String.valueOf(row[9]));
		courseResponseDto.setLocation(String.valueOf(row[8]) + ", " + String.valueOf(row[9]));

		Integer worldRanking = 0;
		if (null != row[10]) {
			worldRanking = Double.valueOf(String.valueOf(row[10])).intValue();
		}
		courseResponseDto.setCourseRanking(worldRanking);
		courseResponseDto.setStars(Double.valueOf(String.valueOf(row[11])));
		courseResponseDto.setRequirements(String.valueOf(row[15]));
		if (courseSearchDto.getCurrencyCode() != null && !courseSearchDto.getCurrencyCode().isEmpty()) {
			if (row[16] != null) {
				CurrencyRate currencyRate = currencyRateDao.getCurrencyRateByCurrencyCode(courseSearchDto.getCurrencyCode());
				Double amt = Double.valueOf(row[16].toString());
				Double convertedRate = amt * currencyRate.getConversionRate();
				additionalInfoDto.setDomesticFee(CommonUtil.foundOff2Digit(convertedRate));
			}
			if (row[17] != null) {
				CurrencyRate currencyRate = currencyRateDao.getCurrencyRateByCurrencyCode(courseSearchDto.getCurrencyCode());
				Double amt = Double.valueOf(row[17].toString());
				Double convertedRate = amt * currencyRate.getConversionRate();
				additionalInfoDto.setInternationalFee(CommonUtil.foundOff2Digit(convertedRate));
			}
		} else {
			if (row[16] != null) {
				additionalInfoDto.setDomesticFee(CommonUtil.foundOff2Digit(Double.valueOf(row[16].toString())));
			}
			if (row[17] != null) {
				additionalInfoDto.setInternationalFee(CommonUtil.foundOff2Digit(Double.valueOf(row[17].toString())));
			}
		}
		if (row[5] != null) {
			courseResponseDto.setCurrencyCode(row[5].toString());
		}
		courseResponseDto.setLatitude((Double) row[18]);
		courseResponseDto.setLongitude((Double) row[19]);
		
		//Course Additional Info
		additionalInfoDto.setDuration(Integer.valueOf(String.valueOf(row[6])));
		additionalInfoDto.setDurationTime(String.valueOf(row[7]));
		additionalInfoDto.setDeliveryType(String.valueOf(row[20]));
		additionalInfoDto.setStudyMode(String.valueOf(row[21]));
		additionalInfoDto.setCourseId(String.valueOf(row[0]));
		additionalInfoDtos.add(additionalInfoDto);
		courseResponseDto.setCourseAdditionalInfo(additionalInfoDtos);
		return courseResponseDto;
	}

	private String addSorting(String sortingQuery, final AdvanceSearchDto courseSearchDto) {
		String sortTypeValue = "ASC";
		if (!courseSearchDto.getSortAsscending()) {
			sortTypeValue = "DESC";
		}
		if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.NAME.toString())) {
			sortingQuery = sortingQuery + " ORDER BY crs.name " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.DURATION.toString())) {
			sortingQuery = sortingQuery + " ORDER BY cai.duration " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.RECOGNITION.toString())) {
			sortingQuery = sortingQuery + " ORDER BY crs.recognition " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.DOMESTIC_PRICE.toString())) {
			sortingQuery = sortingQuery + " ORDER BY cai.domestic_fee " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.INTERNATION_PRICE.toString())) {
			sortingQuery = sortingQuery + " ORDER BY cai.international_fee " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.CREATED_DATE.toString())) {
			sortingQuery = sortingQuery + " ORDER BY crs.created_on " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.LOCATION.toString())) {
			sortingQuery = sortingQuery + " ORDER BY inst.country_name " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.PRICE.toString())) {
			sortingQuery = sortingQuery + " ORDER BY IF(crs.currency='" + courseSearchDto.getCurrencyCode()
					+ "', cai.usd_domestic_fee, cai.usd_international_fee) " + sortTypeValue + " ";
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase("instituteName")) {
			sortingQuery = " order by inst.name " + sortTypeValue.toLowerCase();
		} else if (courseSearchDto.getSortBy().equalsIgnoreCase("countryName")) {
			sortingQuery = " order by inst.country_name " + sortTypeValue.toLowerCase();
		}
		return sortingQuery;
	}

	private String addCondition(String sqlQuery, final AdvanceSearchDto courseSearchDto) {
		if (null != courseSearchDto.getCountryNames() && !courseSearchDto.getCountryNames().isEmpty()) {
			sqlQuery += " and inst.country_name in (" + courseSearchDto.getCountryNames().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}
		if (null != courseSearchDto.getCityNames() && !courseSearchDto.getCityNames().isEmpty()) {
			sqlQuery += " and inst.city_name in (" + courseSearchDto.getCityNames().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}
		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and crs.level_id in (" + courseSearchDto.getLevelIds().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}

		if (null != courseSearchDto.getFaculties() && !courseSearchDto.getFaculties().isEmpty()) {
			sqlQuery += " and crs.faculty_id in (" + courseSearchDto.getFaculties().stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'")) + ")";
		}

		if (null != courseSearchDto.getCourseKeys() && !courseSearchDto.getCourseKeys().isEmpty()) {
			sqlQuery += " and crs.name in (" + courseSearchDto.getCourseKeys().stream().map(addQuotes).collect(Collectors.joining(",")) + ")";
		}
		/**
		 * This is added as in advanced search names are to be passed now, so not
		 * disturbing the already existing code, this condition has been kept in place.
		 */
		else if (null != courseSearchDto.getNames() && !courseSearchDto.getNames().isEmpty()) {
			sqlQuery += " and crs.name in ('" + courseSearchDto.getNames().stream().map(String::valueOf).collect(Collectors.joining(",")) + "\"')";
		}

		if (null != courseSearchDto.getServiceIds() && !courseSearchDto.getServiceIds().isEmpty()) {
			sqlQuery += " and iis.service_id in ('" + courseSearchDto.getServiceIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getMinCost() && courseSearchDto.getMinCost() >= 0) {
			sqlQuery += " and crs.cost_range >= " + courseSearchDto.getMinCost();
		}

		if (null != courseSearchDto.getMaxCost() && courseSearchDto.getMaxCost() >= 0) {
			sqlQuery += " and crs.cost_range <= " + courseSearchDto.getMaxCost();
		}

		if (null != courseSearchDto.getMinDuration() && courseSearchDto.getMinDuration() >= 0) {
			sqlQuery += " and cast(cai.duration as DECIMAL(9,2)) >= " + courseSearchDto.getMinDuration();
		}

		if (null != courseSearchDto.getMaxDuration() && courseSearchDto.getMaxDuration() >= 0) {
			sqlQuery += " and cast(cai.duration as DECIMAL(9,2)) <= " + courseSearchDto.getMaxDuration();
		}

		if (null != courseSearchDto.getInstituteId()) {
			sqlQuery += " and inst.id ='" + courseSearchDto.getInstituteId() + "'";
		}

		if (courseSearchDto.getSearchKeyword() != null) {
			sqlQuery += " and ( inst.name like '%" + courseSearchDto.getSearchKeyword().trim() + "%'";
			sqlQuery += " or inst.country_name like '%" + courseSearchDto.getSearchKeyword().trim() + "%'";
			sqlQuery += " or crs.name like '%" + courseSearchDto.getSearchKeyword().trim() + "%' )";
		}

		if (courseSearchDto.getPartFulls() != null) {
			sqlQuery += " and cai.study_mode in ("+ courseSearchDto.getPartFulls().stream().map(addQuotes).collect(Collectors.joining(",")) + ")";
		}

		if (courseSearchDto.getDeliveryMethods() != null) {
			sqlQuery += " and cai.delivery_type in (" + courseSearchDto.getDeliveryMethods().stream().map(addQuotes).collect(Collectors.joining(",")) + ")";
		}

		/**
		 * This filter is added to get domestic courses from user country and
		 * international courses from other countries, the courses with availbilty ='A'
		 * will be shown to all users and with availbilty='N' will be shown to no one.
		 *
		 */
		if (null != courseSearchDto.getUserCountryId()) {
			sqlQuery += " and ((inst.country_name ='" + courseSearchDto.getUserCountryId() + "' and crs.availbilty = 'D') OR (inst.country_name <>'"
					+ courseSearchDto.getUserCountryId() + "' and crs.availbilty = 'I') OR crs.availbilty = 'A')";
		}
		return sqlQuery;
	}

	@Override
	public List<Course> getAllCourse() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select distinct c.id, c.name as name from course c");
		List<Object[]> rows = query.list();
		List<Course> courses = new ArrayList<>();
		for (Object[] row : rows) {
			Course obj = new Course();
			obj.setId(row[0].toString());
			if (row[1] != null) {
				obj.setName(row[1].toString());
			}
			courses.add(obj);
		}
		return courses;
	}

	@Override
	public List<CourseRequest> courseFilter(final int pageNumber, final Integer pageSize, final CourseFilterDto courseFilter) {
		Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select c.id , c.institute_id, inst.country_name , inst.city_name, c.faculty_id, c.name , "
				+ "c.description, c.intake,"
				+ "c.availabilty, c.created_by, c.updated_by, c.campus_location, c.website,"
				+ " c.recognition_type, c.abbreviation, c.updated_on, c.world_ranking, c.stars, c.remarks  FROM course c join institute inst"
				+ " on c.institute_id = inst.id inner join faculty f  on f.id = c.faculty_id "
				+ " left join institute_service iis  on iis.institute_id = inst.id where c.is_active = 1 and c.deleted_on IS NULL ";

		sqlQuery = addCourseFilterCondition(sqlQuery, courseFilter);
		sqlQuery += " ";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseRequest> courses = getCourseDetails(rows, session);
		return courses;
	}

	private String addCourseFilterCondition(String sqlQuery, final CourseFilterDto courseFilter) {

		if (null != courseFilter.getCountryName()) {
			sqlQuery += " and inst.country_name = '" + courseFilter.getCountryName() + "' ";
		}
		if (null != courseFilter.getInstituteId()) {
			sqlQuery += " and c.institute_id ='" + courseFilter.getInstituteId() + "' ";
		}

		if (null != courseFilter.getFacultyId()) {
			sqlQuery += " and c.faculty_id = '" + courseFilter.getFacultyId() + "' ";
		}

		if (null != courseFilter.getLanguage() && !courseFilter.getLanguage().isEmpty()) {
			sqlQuery += " and c.language = '" + courseFilter.getLanguage() + "' ";
		}

		if (null != courseFilter.getMinRanking() && courseFilter.getMinRanking() >= 0) {
			sqlQuery += " and c.cost_range >= " + courseFilter.getMinRanking();
		}

		if (null != courseFilter.getMaxRanking() && courseFilter.getMaxRanking() >= 0) {
			sqlQuery += " and c.cost_range <= " + courseFilter.getMaxRanking();
		}

		/**
		 * This filter is added to get domestic courses from user country and
		 * international courses from other countries, the courses with availbilty ='A'
		 * will be shown to all users and with availbilty='N' will be shown to no one.
		 *
		 */
		if (null != courseFilter.getUserCountryId()) {
			sqlQuery += " and ((inst.country_name ='" + courseFilter.getUserCountryId() + "' and c.availabilty = 'D') OR (inst.country_name ='" + courseFilter.getUserCountryId()
					+ "' and c.availabilty = 'I') OR c.availabilty = 'A')";
		}
		return sqlQuery;
	}

	@Override
	public int findTotalCountCourseFilter(final CourseFilterDto courseFilter) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select count(*) FROM course c join institute inst"
				+ " on c.institute_id = inst.id inner join faculty f on f.id = c.faculty_id "
				+ " left join institute_service iis on iis.institute_id = inst.id where c.is_active = 1 and c.deleted_on IS NULL ";
		sqlQuery = addCourseFilterCondition(sqlQuery, courseFilter);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public List<CourseRequest> autoSearch(final int pageNumber, final Integer pageSize, final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select c.id , c.institute_id, ist.country_name , ist.city_name, c.faculty_id, c.name," +
				" c.description, c.intake, c.availabilty, c.created_by, c.updated_by, c.campus_location, c.website," +
				" c.recognition_type, c.abbreviation, c.updated_on, c.world_ranking, c.stars, c.remarks  FROM course c inner join" +
				" institute ist on c.institute_id = ist.id" +
				" where c.is_active = 1 and c.deleted_on IS NULL and (c.name like '%" + searchKey + "%' or ist.name like '%" + searchKey +
				" %') ORDER BY c.created_on DESC ";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseRequest> courses = getCourseDetails(rows, session);
		return courses;
	}

	@Override
	public Long autoSearchTotalCount(final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
		BigInteger count = (BigInteger) session.createNativeQuery(
				"select count(*) FROM course c inner join institute ist on c.institute_id = ist.id" + 
				" where c.is_active = 1 and c.deleted_on IS NULL and (c.name like '%" + searchKey + "%' or ist.name like '%" + searchKey + 
				" %') ORDER BY c.created_on DESC ").uniqueResult();
		return count != null ? count.longValue() : 0L;
	}

	@Override
	public List<Course> facultyWiseCourseForTopInstitute(final List<Faculty> facultyList, final Institute institute) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.add(Restrictions.eq("institute", institute));
		crit.add(Restrictions.in("faculty", facultyList));
		return crit.list();
	}

	@Override
	public List<CountryDto> getCourseCountry() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct inst.country_name as countryName from course crs inner join institute inst on crs.institute_id = inst.id"
				+ " where crs.deleted_on IS NULL";
		Query query = session.createSQLQuery(sqlQuery);
		List<String> rows = query.list();
		List<CountryDto> countryDtos = new ArrayList<>();
		CountryDto countryDto = null;
		for (String row : rows) {
			countryDto = new CountryDto();
			if (row != null) {
				countryDto.setName(row);
			}
			List<LevelDto> levels = levelDAO.getCountryLevel(countryDto.getName());
			for (LevelDto level : levels) {
				List<FacultyDto> facultyList = dao.getCourseFaculty(countryDto.getName(), level.getId());
				level.setFaculty(facultyList);
			}
			countryDto.setLevels(levels);
			countryDtos.add(countryDto);
		}
		return countryDtos;
	}

	@Override
	public long getCourseCountForCountry(final String country) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.add(Restrictions.eq("country", country));
		crit.setProjection(Projections.rowCount());
		return (long) crit.uniqueResult();
	}

	@Override
	public List<Course> getTopRatedCoursesForCountryWorldRankingWise(final String country) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.add(Restrictions.eq("country", country));
		crit.addOrder(Order.desc("worldRanking"));
		return crit.list();
	}

	@Override
	public List<Course> getAllCourseForFacultyWorldRankingWise(final String facultyId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.createAlias("course.faculty", "courseFaculty");
		crit.add(Restrictions.eq("courseFaculty.id",facultyId));
		crit.addOrder(Order.asc("worldRanking"));
		return crit.list();
	}

	@Override
	public List<String> getAllCourseForFacultyWorldRankingWises(final String facultyId) {

		Session session = sessionFactory.getCurrentSession();
		List<String> courseList = session.createNativeQuery("select id from course c where c.faculty_id = ? order by c.world_ranking asc")
				.setString(1, facultyId).getResultList();
		return courseList;

	}

	@Override
	public List<Course> getCoursesFromId(final List<String> allSearchCourses) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.add(Restrictions.in("id", allSearchCourses));
		return crit.list();
	}

	@Override
	public Map<String, String> facultyWiseCourseIdMapForInstitute(final List<Faculty> facultyList, final String instituteId) {
		Map<String, String> mapOfCourseIdFacultyId = new HashMap<>();
		StringBuilder facultyIds = new StringBuilder();
		int count = 0;

		facultyIds = new StringBuilder();
		for (Faculty faculty : facultyList) {
			count++;

			facultyIds.append(faculty.getId());
			if (count < facultyList.size() - 1) {
				facultyIds.append(",");
			}
		}

		Session session = sessionFactory.getCurrentSession();
		List<Object[]> rows = session
				.createNativeQuery("Select id, faculty_id from course where institute_id = ? and faculty_id in (" + facultyIds + ") and id is not null")
				.setString(1, instituteId).getResultList();

		for (Object[] row : rows) {
			mapOfCourseIdFacultyId.put(row[0].toString(), row[1].toString());
		}

		return mapOfCourseIdFacultyId;
	}

	@Override
	public List<Course> getAllCoursesUsingId(final List<String> listOfRecommendedCourseIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.add(Restrictions.in("id", listOfRecommendedCourseIds));
		return crit.list();
	}

	@Override
	public List<String> getTopRatedCourseIdsForCountryWorldRankingWise(final String country) {
		Session session = sessionFactory.getCurrentSession();
		List<String> rows = session.createNativeQuery("select id from course where country_name = ? order by world_ranking desc")
				.setString(1, country).getResultList();
		List<String> courseIds = rows;

		return courseIds;
	}

	private String addConditionForCourseList(String sqlQuery, final List<String> courseIds) {
		if (null != courseIds) {
			sqlQuery += " and crs.id in (" + courseIds.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}
		return sqlQuery;
	}

	@Override
	public Long getCountOfDistinctInstitutesOfferingCoursesForCountry(final UserDto userDto, final String country) {
		Session session = sessionFactory.getCurrentSession();

		BigInteger count = (BigInteger) session.createNativeQuery(
				"select count(*) from (Select count(*) from course where country_id = ? and is_active = ? group by country_name, institute_id) as temp_table")
				.setParameter(1, country).setParameter(2, 1).uniqueResult();
		return count != null ? count.longValue() : 0L;
	}

	@Override
	public List<String> getDistinctCountryBasedOnCourses(final List<String> topSearchedCourseIds) {

		Session session = sessionFactory.getCurrentSession();

		String ids = topSearchedCourseIds.stream().map(String::toString).collect(Collectors.joining(","));

		System.out.println("IDs -- " + ids);
		List<String> countryIds = session
                .createNativeQuery("Select distinct i.country_name from course c join institute i on i.id=c.institute_id  where i.country_name is not null and " + "c.id in ('" + ids.replace(",", "','") + "')").getResultList();
		return countryIds;
	}

	@Override
	public List<String> getCourseListForCourseBasedOnParameters(final String courseId, final String instituteId, final String facultyId,
			final String countryId, final String cityId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder query = new StringBuilder();
		query.append("Select c.id from course c left join institute i on c.institute_id = i.id where 1=1");
		if (courseId != null) {
			query.append(" and c.id = '" + courseId + "'");
		}
		if (instituteId != null) {
			query.append(" and c.institute_id ='" + instituteId + "'");
		}
		if (facultyId != null) {
			query.append(" and c.faculty_id = '" + facultyId + "'");
		}
		if (countryId != null) {
			query.append(" and i.country_name = " + countryId);
		}
		if (cityId != null) {
			query.append(" and i.city_name = " + cityId);
		}
		List<String> courseIds = session.createNativeQuery(query.toString()).getResultList();
		return courseIds;
	}

	@Override
	public List<Long> getUserListFromUserWatchCoursesBasedOnCourses(final List<String> courseIds) {
		Session session = sessionFactory.getCurrentSession();
		String ids = courseIds.stream().map(String::toString).collect(Collectors.joining(","));
		List<Long> userIds = session.createNativeQuery("select distinct user_id from user_watch_course where course_id in (" + ids + ")").getResultList();
		return userIds;
	}

	@Override
	public List<String> getCourseIdsForCountry(final String country) {
		Session session = sessionFactory.getCurrentSession();
		List<String> courseList = session.createNativeQuery("select c.id from course c left join institute i on i.id = c.institute_id"
				+ " where i.country_name ='"+country+"'").getResultList();
		return courseList;
	}

	@Override
	public List<String> getAllCoursesForCountry(final List<String> otherCountryIds) {
		Session session = sessionFactory.getCurrentSession();
		String ids = otherCountryIds.stream().map(String::toString).collect(Collectors.joining(","));
		List<String> courseIdList = session.createNativeQuery("Select c.id from course c left join institute i on i.id = c.institute_id"
				+ " where i.country_name in ('" + ids.replace("'", "") + "')").getResultList();
		return courseIdList;
	}

	@Override
	public int updateCourseForCurrency(final CurrencyRate currencyRate) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(currencyRate);
		Integer count = session.createNativeQuery(
				"update course set usd_domestic_fee = domestic_fee * ?, usd_international_fee = international_fee * ?, updated_on = now() where currency =?")
				.setParameter(1, 1 / currencyRate.getConversionRate()).setParameter(2, 1 / currencyRate.getConversionRate())
				.setParameter(3, currencyRate.getToCurrencyCode()).executeUpdate();
		System.out.println("courses updated for " + currencyRate.getToCurrencyCode() + "-" + count);
		return count;
	}

	@Override
	public Integer getTotalCourseCountForInstitute(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class, "course");
		criteria.createAlias("institute", "institute");
		criteria.add(Restrictions.eq("institute.id", instituteId));
		criteria.setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

	@Override
	public List<CourseDTOElasticSearch> getUpdatedCourses(final Date updatedOn, final Integer startIndex, final Integer limit) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createNativeQuery("select crs.id, crs.name, crs.world_ranking as courseRanking, \r\n" + "crs.stars,crs.recognition,\r\n"
				+ "crs.duration, \r\n" + "crs.website, crs.language, crs.abbreviation,\r\n" + "crs.rec_date ,crs.remarks, crs.description,\r\n"
				+ "crs.is_active, crs.created_on, crs.updated_on,\r\n" + "crs.deleted_on, crs.created_by, crs.updated_by, crs.is_deleted,\r\n"
				+ "crs.availbilty, crs.part_full, crs.study_mode, crs.international_fee,\r\n"
				+ "crs.domestic_fee, crs.currency, crs.currency_time, crs.usd_international_fee,\r\n"
				+ "crs.usd_domestic_fee, crs.cost_range, crs.content, \r\n" + "inst.name as institute_name, fac.name as faculty_name,\r\n"
				+ "fac.description as faculty_description, cntry.name as country_name,\r\n"
				+ "ct.name as city_name, lev.code as level_code, lev.name as level_name, crs.recognition_type,"
				+ "crs.duration_time from course crs \r\n" + "inner join institute inst on crs.institute_id = inst.id\r\n"
				+ "inner join faculty fac on crs.faculty_id = fac.id\r\n" + "inner join country cntry on inst.country_id = cntry.id\r\n"
				+ "inner join city ct on crs.city_id = ct.id\r\n" + "inner join level lev on crs.level_id = lev.id\r\n" + "where crs.updated_on >= ? \r\n"
				+ "limit ?,?;");
		query.setParameter(1, updatedOn).setParameter(2, startIndex).setParameter(3, limit);

		List<Object[]> rows = query.list();
		List<CourseDTOElasticSearch> courseElasticSearchList = new ArrayList<>();
		for (Object[] objects : rows) {
			CourseDTOElasticSearch courseDtoElasticSearch = new CourseDTOElasticSearch();
			courseDtoElasticSearch.setId(String.valueOf(objects[0]));
			courseDtoElasticSearch.setName(String.valueOf(objects[1]));
			if (String.valueOf(objects[2]) != null && !String.valueOf(objects[2]).isEmpty() && !"null".equalsIgnoreCase(String.valueOf(objects[2]))) {
				courseDtoElasticSearch.setWorldRanking(Integer.valueOf(String.valueOf(objects[2])));
			} else {
				courseDtoElasticSearch.setWorldRanking(null);
			}

			if (String.valueOf(objects[3]) != null && !String.valueOf(objects[3]).isEmpty() && !"null".equalsIgnoreCase(String.valueOf(objects[3]))) {
				courseDtoElasticSearch.setStars(Integer.valueOf(String.valueOf(objects[3])));
			} else {
				courseDtoElasticSearch.setStars(null);
			}

			courseDtoElasticSearch.setRecognition(String.valueOf(objects[4]));
			courseDtoElasticSearch.setWebsite(String.valueOf(objects[6]));
			courseDtoElasticSearch.setAbbreviation(String.valueOf(objects[8]));
			courseDtoElasticSearch.setRemarks(String.valueOf(objects[10]));
			courseDtoElasticSearch.setDescription(String.valueOf(objects[11]));
			courseDtoElasticSearch.setAvailabilty(String.valueOf(objects[19]));

			courseDtoElasticSearch.setCurrency(String.valueOf(objects[24]));
			courseDtoElasticSearch.setCurrencyTime(String.valueOf(objects[25]));

			if (String.valueOf(objects[28]) != null && !String.valueOf(objects[28]).isEmpty() && !"null".equalsIgnoreCase(String.valueOf(objects[28]))) {
				courseDtoElasticSearch.setCostRange(Double.valueOf(String.valueOf(objects[28])));
			} else {
				courseDtoElasticSearch.setCostRange(null);
			}

			courseDtoElasticSearch.setContent(String.valueOf(objects[29]));

			courseDtoElasticSearch.setInstituteName(String.valueOf(objects[30]));
			courseDtoElasticSearch.setFacultyName(String.valueOf(objects[31]));
			courseDtoElasticSearch.setFacultyDescription(String.valueOf(objects[32]));
			courseDtoElasticSearch.setCountryName(String.valueOf(objects[33]));
			courseDtoElasticSearch.setCityName(String.valueOf(objects[34]));

			courseDtoElasticSearch.setLevelCode(String.valueOf(objects[35]));
			courseDtoElasticSearch.setLevelName(String.valueOf(objects[36]));
			
			courseDtoElasticSearch.setRecognitionType(String.valueOf(objects[37]));
			courseElasticSearchList.add(courseDtoElasticSearch);
		}
		return courseElasticSearchList;
	}

	@Override
	public Integer getCountOfTotalUpdatedCourses(final Date utCdatetimeAsOnlyDate) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class, "course");
		criteria.add(Restrictions.ge("updatedOn", utCdatetimeAsOnlyDate));
		criteria.setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		return count != null ? count.intValue() : 0;
	}

	@Override
	public List<CourseDTOElasticSearch> getCoursesToBeRetriedForElasticSearch(final List<String> courseIds, final Integer startIndex, final Integer limit) {
		String courseIdString = "";
		if (courseIds == null || courseIds.isEmpty()) {
			return new ArrayList<>();
		} else {
			courseIdString = courseIds.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));
		}
		Session session = sessionFactory.getCurrentSession();
		StringBuilder queryString = new StringBuilder("select crs.id, crs.name, crs.world_ranking as courseRanking, \r\n" + "crs.stars,crs.recognition,\r\n"
				+ "crs.duration, \r\n" + "crs.website, crs.language, crs.abbreviation,\r\n" + "crs.rec_date ,crs.remarks, crs.description,\r\n"
				+ "crs.is_active, crs.created_on, crs.updated_on,\r\n" + "crs.deleted_on, crs.created_by, crs.updated_by, crs.is_deleted,\r\n"
				+ "crs.availbilty, crs.part_full, crs.study_mode, crs.international_fee,\r\n"
				+ "crs.domestic_fee, crs.currency, crs.currency_time, crs.usd_international_fee,\r\n"
				+ "crs.usd_domestic_fee, crs.cost_range, crs.content, \r\n" + "inst.name as institute_name, fac.name as faculty_name,\r\n"
				+ "fac.description as faculty_description, cntry.name as country_name,\r\n"
				+ "ct.name as city_name, lev.code as level_code, lev.name as level_name, crs.recognition_type,"
				+ "crs.duration_time from course crs \r\n" + "inner join institute inst on crs.institute_id = inst.id\r\n"
				+ "inner join faculty fac on crs.faculty_id = fac.id\r\n" + "inner join country cntry on inst.country_id = cntry.id\r\n"
				+ "inner join city ct on crs.city_id = ct.id\r\n" + "inner join level lev on crs.level_id = lev.id\r\n" + "where crs.id in (");

		for (int i = 0; i < courseIds.size(); i++) {
			queryString.append("?");
			if (!(i == courseIds.size() - 1)) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		Query query = session.createNativeQuery(queryString.toString());
		for (int i = 0; i < courseIds.size(); i++) {
			query.setParameter(i + 1, courseIds.get(i));
		}

		List<Object[]> rows = query.list();
		List<CourseDTOElasticSearch> courseElasticSearchList = new ArrayList<>();
		for (Object[] objects : rows) {
			CourseDTOElasticSearch courseDtoElasticSearch = new CourseDTOElasticSearch();
			courseDtoElasticSearch.setId(String.valueOf(objects[0]));
			courseDtoElasticSearch.setName(String.valueOf(objects[1]));
			if (String.valueOf(objects[2]) != null && !String.valueOf(objects[2]).isEmpty() && !"null".equalsIgnoreCase(String.valueOf(objects[2]))) {
				courseDtoElasticSearch.setWorldRanking(Integer.valueOf(String.valueOf(objects[2])));
			} else {
				courseDtoElasticSearch.setWorldRanking(null);
			}

			if (String.valueOf(objects[3]) != null && !String.valueOf(objects[3]).isEmpty() && !"null".equalsIgnoreCase(String.valueOf(objects[3]))) {
				courseDtoElasticSearch.setStars(Integer.valueOf(String.valueOf(objects[3])));
			} else {
				courseDtoElasticSearch.setStars(null);
			}

			courseDtoElasticSearch.setRecognition(String.valueOf(objects[4]));
			courseDtoElasticSearch.setWebsite(String.valueOf(objects[6]));
			courseDtoElasticSearch.setAbbreviation(String.valueOf(objects[8]));
			courseDtoElasticSearch.setRemarks(String.valueOf(objects[10]));
			courseDtoElasticSearch.setDescription(String.valueOf(objects[11]));
			courseDtoElasticSearch.setAvailabilty(String.valueOf(objects[19]));
			
			courseDtoElasticSearch.setCurrency(String.valueOf(objects[24]));
			courseDtoElasticSearch.setCurrencyTime(String.valueOf(objects[25]));

			if (String.valueOf(objects[28]) != null && !String.valueOf(objects[28]).isEmpty() && !"null".equalsIgnoreCase(String.valueOf(objects[28]))) {
				courseDtoElasticSearch.setCostRange(Double.valueOf(String.valueOf(objects[28])));
			} else {
				courseDtoElasticSearch.setCostRange(null);
			}

			courseDtoElasticSearch.setContent(String.valueOf(objects[29]));

			courseDtoElasticSearch.setInstituteName(String.valueOf(objects[30]));
			courseDtoElasticSearch.setFacultyName(String.valueOf(objects[31]));
			courseDtoElasticSearch.setFacultyDescription(String.valueOf(objects[32]));
			courseDtoElasticSearch.setCountryName(String.valueOf(objects[33]));
			courseDtoElasticSearch.setCityName(String.valueOf(objects[34]));

			courseDtoElasticSearch.setLevelCode(String.valueOf(objects[35]));
			courseDtoElasticSearch.setLevelName(String.valueOf(objects[36]));
			courseDtoElasticSearch.setRecognitionType(String.valueOf(objects[37]));
			courseElasticSearchList.add(courseDtoElasticSearch);
		}
		return courseElasticSearchList;
	}

	@Override
	public void saveCourseIntake(final CourseIntake courseIntake) {
		Session session = sessionFactory.getCurrentSession();
		session.save(courseIntake);
	}

	@Override
	public void deleteCourseIntake(final String courseId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "delete CourseIntake where course_id = :courseId";
		Query q = session.createQuery(hql).setParameter("courseId", courseId);
		q.executeUpdate();

	}

	@Override
	public List<CourseIntake> getCourseIntakeBasedOnCourseId(final String courseId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CourseIntake.class, "courseIntake");
		crit.createAlias("courseIntake.course", "course");
		crit.add(Restrictions.eq("course.id", courseId));
		return crit.list();
	}

	@Override
	public List<CourseIntake> getCourseIntakeBasedOnCourseIdList(final List<String> courseIds) {
		List<String> courseId=new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CourseIntake.class, "courseIntake");
		crit.createAlias("courseIntake.course", "course");
		for(int i = 0 ; i < courseIds.size(); i++) {
			courseId.add("'"+courseIds.get(i)+"'");
		}
		crit.add(Restrictions.in("course.id", courseId));
		return crit.list();
	}
	
	@Override
	public void deleteCourseDeliveryMethod(final String courseId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "delete CourseDeliveryMethod where course_id = :courseId";
		Query q = session.createQuery(hql).setParameter("courseId", courseId);
		q.executeUpdate();
	}

	@Override
	public void saveCourseLanguage(final CourseLanguage courseLanguage) {
		Session session = sessionFactory.getCurrentSession();
		session.save(courseLanguage);

	}

	@Override
	public void deleteCourseLanguage(final String courseId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "delete CourseLanguage where course_id = :courseId";
		Query q = session.createQuery(hql).setParameter("courseId", courseId);
		q.executeUpdate();

	}

	@Override
	public List<CourseLanguage> getCourseLanguageBasedOnCourseId(final String courseId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CourseLanguage.class, "courseLanguage");
		crit.createAlias("courseLanguage.course", "course");
		crit.add(Restrictions.eq("course.id", courseId));
		return crit.list();
	}

	@Override
	public List<String> getUserSearchCourseRecommendation(final Integer startIndex, final Integer pageSize, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class);
		criteria.setProjection(Projections.property("name"));
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			criteria.add(Restrictions.ilike("name", searchKeyword, MatchMode.ANYWHERE));
		}
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
	}

	@Override
	public int getDistinctCourseCountbyName(String courseName) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sqlQuery = new StringBuilder("select distinct c.name as courseName from course c");
		if (StringUtils.isNotEmpty(courseName)) {
			sqlQuery.append(" where name like ('" + "%" + courseName.trim() + "%')");
		}
		Query query = session.createSQLQuery(sqlQuery.toString());
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public List<CourseResponseDto> getDistinctCourseListByName(Integer startIndex, Integer pageSize, String courseName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class).setProjection(Projections.projectionList()
				.add(Projections.groupProperty("name").as("name"))
				.add(Projections.property("id").as("id"))
				.add(Projections.property("worldRanking").as("courseRanking"))
				.add(Projections.property("currency").as("currencyCode"))
				.add(Projections.property("costRange").as("costRange")))
				.setResultTransformer(Transformers.aliasToBean(CourseResponseDto.class));
		if (StringUtils.isNotEmpty(courseName)) {
			criteria.add(Restrictions.like("name", courseName,MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}
	
	public Integer getCoursesCountBylevelId(final String levelId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sqlQuery = new StringBuilder("select count(*) from  course c where c.level_id='"+ levelId + "'");
		Query query = session.createSQLQuery(sqlQuery.toString());
		Integer count =  Integer.valueOf(query.uniqueResult().toString());
		return count;
	}
	
	@Override
	public List<Course> getAllCourseByInstituteIdAndFacultyIdAndStatus(String instituteId, String facultyId, boolean isActive) {
		return courseRepository.findByInstituteIdAndFacultyIdAndIsActive(instituteId, facultyId, isActive);
	}

	@Override
	public List<Course> getAllCourseByInstituteIdAndFacultyId(String instituteId, String facultyId) {
		return courseRepository.findByInstituteIdAndFacultyId(instituteId, facultyId);
	}

	@Override
	public List<CourseResponseDto> getNearestCourseForAdvanceSearch(AdvanceSearchDto courseSearchDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT crs.id as courseId,crs.name as courseName,crs.world_ranking,crs.stars as stars, inst.id, inst.name as instituteName," + 
				" crs.cost_range, inst.country_name, inst.city_name, crs.currency, inst.latitude, inst.longitude, cai.duration, cai.duration_time," +
				" cai.study_mode, cai.usd_domestic_fee, cai.usd_international_fee, cai.id as additionalInfoId, cai.delivery_type,"+
				" 6371 * ACOS(SIN(RADIANS('"+ courseSearchDto.getLatitude() +"')) * SIN(RADIANS(inst.latitude)) + COS(RADIANS('"+ courseSearchDto.getLatitude() +"')) * COS(RADIANS(inst.latitude)) * COS(RADIANS(inst.longitude)-" + 
				" RADIANS('"+ courseSearchDto.getLongitude() +"'))) AS distance_in_km FROM course crs inner join institute inst on inst.id = crs.institute_id" +
				" inner join faculty f on f.id = crs.faculty_id LEFT JOIN institute_service iis on iis.institute_id = inst.id" +
				" LEFT JOIN course_delivery_modes cai on cai.course_id = crs.id where inst.latitude is not null and" +
				" inst.longitude is not null and inst.latitude!= " + courseSearchDto.getLatitude() + " and inst.longitude!= " + courseSearchDto.getLongitude();
		
		sqlQuery = addCondition(sqlQuery, courseSearchDto);
		
	    sqlQuery += " HAVING distance_in_km <= " + courseSearchDto.getInitialRadius();
		
		String sortingQuery = "";
		if (courseSearchDto.getSortBy() != null && !courseSearchDto.getSortBy().isEmpty()) {
			sortingQuery = addSorting(sortingQuery, courseSearchDto);
		}
		
		if (courseSearchDto.getPageNumber() != null && courseSearchDto.getMaxSizePerPage() != null) {
			PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
			sqlQuery += sortingQuery + " LIMIT " + PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage()) + " ,"
					+ courseSearchDto.getMaxSizePerPage();
		} else {
			sqlQuery += sortingQuery;
		}
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> nearestCourseDTOs = new ArrayList<>();
		List<CourseDeliveryModesDto> additionalInfoDtos = new ArrayList<>();
		for (Object[] row : rows) {
			CourseResponseDto nearestCourseDTO = new CourseResponseDto();
			nearestCourseDTO.setId(String.valueOf(row[0]));
			nearestCourseDTO.setName(String.valueOf(row[1]));
			nearestCourseDTO.setCourseRanking((Integer) row[2]);
			nearestCourseDTO.setStars(Double.parseDouble(String.valueOf(row[3])));
			nearestCourseDTO.setInstituteId((String) row[4]);
			nearestCourseDTO.setInstituteName((String) row[5]);
			nearestCourseDTO.setCostRange((Double) row[6]);
			nearestCourseDTO.setCountryName((String) row[7]);
			nearestCourseDTO.setCityName((String) row[8]);
			nearestCourseDTO.setCurrencyCode((String) row[9]);
			nearestCourseDTO.setLatitude((Double) row[10]);
			nearestCourseDTO.setLongitude((Double) row[11]);
			
			CourseDeliveryModesDto additionalInfoDto = new CourseDeliveryModesDto();
			if(row[17] != null) {
				additionalInfoDto.setId(String.valueOf(row[17]));
				additionalInfoDto.setDeliveryType(String.valueOf(row[18]));
				if(row[14] != null) {
					additionalInfoDto.setStudyMode(String.valueOf(row[14]));
				}
				if(row[12] != null) {
					additionalInfoDto.setDuration(Integer.parseInt(String.valueOf(row[12])));
				}
				if(row[13] != null) {
					additionalInfoDto.setDurationTime(String.valueOf(row[13]));
				}
				if(row[15] != null) {
					additionalInfoDto.setUsdDomesticFee(Double.parseDouble(String.valueOf(row[15])));
				}
				if(row[16] != null) {
					additionalInfoDto.setUsdInternationalFee(Double.parseDouble(String.valueOf(row[16])));
				}
				additionalInfoDto.setCourseId(String.valueOf(row[0]));
				additionalInfoDtos.add(additionalInfoDto);
			}
			nearestCourseDTO.setCourseAdditionalInfo(additionalInfoDtos);
			nearestCourseDTOs.add(nearestCourseDTO);
		}
		return nearestCourseDTOs;
	}

	@Override
	public List<CourseResponseDto> getCourseByCountryName(Integer pageNumber, Integer pageSize, String countryName) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "Select DISTINCT crs.id as courseId, crs.name as courseName, crs.world_ranking,avg(crs.stars) as stars, crs.duration,"
				+ " crs.duration_time, crs.`language`, institute.id, institute.name as instituteName,"
				+ " crs.cost_range, crs.domestic_fee, crs.international_fee, institute.country_name, institute.city_name, "
				+ " crs.currency, institute.latitute,institute.longitute from course crs left join institute institute on crs.institute_id ="
				+ " institute.id where institute.country_name = '"+ countryName + "' GROUP BY crs.id limit "+ PaginationUtil.getStartIndex(pageNumber, pageSize) + " ," + pageSize;
		
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> nearestCourseDTOs = new ArrayList<>();
		for (Object[] row : rows) {
			CourseResponseDto nearestCourseDTO = new CourseResponseDto();
			nearestCourseDTO.setId((String.valueOf(row[0])));
			nearestCourseDTO.setName(String.valueOf(row[1]));
			nearestCourseDTO.setCourseRanking((Integer) row[2]);
			nearestCourseDTO.setStars(((BigDecimal) row[3]).doubleValue());
//			nearestCourseDTO.setDuration((Double) row[4]);
//			nearestCourseDTO.setDurationTime((String) row[5]);
			nearestCourseDTO.setInstituteId((String) row[7]);
			nearestCourseDTO.setInstituteName((String) row[8]);
			nearestCourseDTO.setCostRange((Double) row[9]);
//			nearestCourseDTO.setDomesticFee((Double) row[10]);
//			nearestCourseDTO.setInternationalFee((Double) row[11]);
			nearestCourseDTO.setCountryName((String) row[12]);
			nearestCourseDTO.setCityName((String) row[13]);
			nearestCourseDTO.setCurrencyCode((String) row[14]);
			nearestCourseDTO.setLatitude((Double) row[15]);
			nearestCourseDTO.setLongitude((Double) row[16]);
			nearestCourseDTO.setLocation((String) row[13] + ", " + (String) row[12]);
			nearestCourseDTOs.add(nearestCourseDTO);
		}
		return nearestCourseDTOs;
	}

	@Override
	public Integer getTotalCountOfNearestCourses(Double latitude, Double longitude, Integer initialRadius) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT course.id," + 
				" 6371 * ACOS(SIN(RADIANS('"+ latitude +"')) * SIN(RADIANS(institute.latitude)) + COS(RADIANS('"+ latitude +"')) * COS(RADIANS(institute.latitude)) *" + 
				" COS(RADIANS(institute.longitude) - RADIANS('"+ longitude +"'))) AS distance_in_km FROM institute institute inner join course on \r\n" + 
				" institute.id = course.institute_id where institute.latitude is not null" + 
				" and institute.longitude is not null and institute.latitude!= "+ latitude +" and institute.longitude!= " + longitude +
				" group by course.id HAVING distance_in_km <= "+initialRadius;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		Integer totalCount = rows.size();
		return totalCount;
	}

}
