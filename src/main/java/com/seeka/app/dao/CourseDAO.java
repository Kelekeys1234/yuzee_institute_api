package com.seeka.app.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dto.CourseDto;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.CurrencyConvertorRequest;
import com.seeka.app.dto.GlobalSearchResponseDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.enumeration.CourseSortBy;
import com.seeka.app.jobs.CurrencyUtil;
import com.seeka.app.service.CurrencyService;
import com.seeka.app.util.CDNServerUtil;
import com.seeka.app.util.ConvertionUtil;
import com.seeka.app.util.GlobalSearchWordUtil;
import com.seeka.app.util.IConstant;

@Repository
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class CourseDAO implements ICourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CurrencyService currencyService;

	@Override
	public void save(final Course obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final Course obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override
	public Course get(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		Course obj = session.get(Course.class, id);
		return obj;
	}

	@Override
	public List<Course> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class);
		return crit.list();
	}

	public Currency getCurrencyByCode(final String currencyCode) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Currency.class);
		crit.add(Restrictions.eq("code", currencyCode));
		return (Currency) crit.list().get(0);
	}

	@Override
	public List<CourseResponseDto> getAllCoursesByFilter(final CourseSearchDto courseSearchDto) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select distinct crs.id as courseId,crs.name as courseName," + "inst.id as instId,inst.name as instName, crs.cost_range, "
				+ "crs.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition, crs.domestic_fee, crs.international_fee,crs.remarks, usd_domestic_fee, usd_international_fee "
				+ "from course crs inner join institute inst "
				+ " on crs.institute_id = inst.id inner join country ctry  on ctry.id = crs.country_id inner join "
				+ "city ci  on ci.id = crs.city_id inner join faculty f  on f.id = crs.faculty_id "
				+ "left join institute_service iis  on iis.institute_id = inst.id where 1=1";

		boolean showIntlCost = false;
		if (null != courseSearchDto.getInstituteId()) {
			sqlQuery += " and inst.id =" + courseSearchDto.getInstituteId();
		}

		if (null != courseSearchDto.getCountryIds() && !courseSearchDto.getCountryIds().isEmpty()) {
			sqlQuery += " and crs.country_id in (" + courseSearchDto.getCountryIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != courseSearchDto.getCityIds() && !courseSearchDto.getCityIds().isEmpty()) {
			sqlQuery += " and crs.city_id in (" + courseSearchDto.getCityIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and f.level_id in (" + courseSearchDto.getLevelIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in (" + courseSearchDto.getFacultyIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != courseSearchDto.getCourseKeys() && !courseSearchDto.getCourseKeys().isEmpty()) {
			sqlQuery += " and crs.name in (" + courseSearchDto.getCourseKeys().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != courseSearchDto.getServiceIds() && !courseSearchDto.getServiceIds().isEmpty()) {
			sqlQuery += " and iis.service_id in (" + courseSearchDto.getServiceIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
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

		if (null != courseSearchDto.getCourseName() && !courseSearchDto.getCourseName().isEmpty()) {
			sqlQuery += " and crs.name like '%" + courseSearchDto.getCourseName().trim() + "%'";
		}

		sqlQuery += " ";
		String sortingQuery = "";
		if (courseSearchDto.getSortBy() != null && !courseSearchDto.getSortBy().isEmpty()) {
			String sortTypeValue = "ASC";
			if (!courseSearchDto.getSortAsscending()) {
				sortTypeValue = "DESC";
			}
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.NAME.toString())) {
				sortingQuery = sortingQuery + " ORDER BY crs.name " + sortTypeValue + " ";
			}
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.DOMESTIC_PRICE.toString())) {
				sortingQuery = sortingQuery + " ORDER BY crs.domestic_fee " + sortTypeValue + " ";
			}
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.INTERNATION_PRICE.toString())) {
				sortingQuery = sortingQuery + " ORDER BY crs.international_fee " + sortTypeValue + " ";
			}
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.CREATED_DATE.toString())) {
				sortingQuery = sortingQuery + " ORDER BY crs.created_on " + sortTypeValue + " ";
			}
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.DURATION.toString())) {
				sortingQuery = sortingQuery + " ORDER BY crs.duration " + sortTypeValue + " ";
			}
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.RECOGNITION.toString())) {
				sortingQuery = sortingQuery + " ORDER BY crs.recognition " + sortTypeValue + " ";
			}
			if (courseSearchDto.getSortBy().equalsIgnoreCase(CourseSortBy.LOCATION.toString())) {
				sortingQuery = sortingQuery + " ORDER BY ctry.name " + sortTypeValue + " ";
			}
		} else {
			if (null != courseSearchDto.getSortingObj()) {
				CourseSearchFilterDto sortingObj = courseSearchDto.getSortingObj();
				if (null != sortingObj.getPrice() && !sortingObj.getPrice().isEmpty()) {
					if (sortingObj.getPrice().equals("ASC")) {
						sortingQuery = " order by crs.cost_range asc";
					} else {
						sortingQuery = " order by crs.cost_range desc";
					}
				}

				if (null != sortingObj.getLocation() && !sortingObj.getLocation().isEmpty()) {
					if (sortingObj.getLocation().equals("ASC")) {
						sortingQuery = " order by ctry.countryName, ci.cityName asc";
					} else {
						sortingQuery = " order by ctry.countryName, ci.cityName desc";
					}
				}

				if (null != sortingObj.getDuration() && !sortingObj.getDuration().isEmpty()) {
					if (sortingObj.getDuration().equals("ASC")) {
						sortingQuery = " order by crs.duration asc";
					} else {
						sortingQuery = " order by crs.duration desc";
					}
				}
				if (null != sortingObj.getRecognition() && !sortingObj.getRecognition().isEmpty()) {
					if (sortingObj.getRecognition().equals("ASC")) {
						sortingQuery = " order by crs.recognition asc";
					} else {
						sortingQuery = " order by crs.recognition desc";
					}
				}

				if (null != sortingObj.getCourse() && !sortingObj.getCourse().isEmpty()) {
					if (sortingObj.getCourse().equals("ASC")) {
						sortingQuery = " order by inst.name asc";
					} else {
						sortingQuery = " order by inst.name desc";
					}
				}

				if (null != sortingObj.getInstitute() && !sortingObj.getInstitute().isEmpty()) {
					if (sortingObj.getInstitute().equals("ASC")) {
						sortingQuery = " order by crs.courseName asc";
					} else {
						sortingQuery = " order by crs.courseName desc";
					}
				}

				if (null != sortingObj.getLatestCourse() && !sortingObj.getLatestCourse().isEmpty()) {
					if (sortingObj.getLatestCourse().equals("ASC")) {
						sortingQuery = " order by crs.courseId asc";
					} else {
						sortingQuery = " order by crs.courseId desc";
					}
				}
			} else {
				sortingQuery = " order by crs.international_fee asc";
			}
		}
		String sizeQuery = sqlQuery;
		if (courseSearchDto.getPageNumber() != null && courseSearchDto.getMaxSizePerPage() != null) {
			sqlQuery += sortingQuery + " LIMIT " + courseSearchDto.getPageNumber() + " ," + courseSearchDto.getMaxSizePerPage();
		} else {
			sqlQuery += sortingQuery;
		}
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();

		Query query1 = session.createSQLQuery(sizeQuery);
		List<Object[]> rows1 = query1.list();

		List<CourseResponseDto> list = new ArrayList<>();
		CourseResponseDto courseResponseDto = null;
		Long cost = 0l, localFees = 0l, intlFees = 0l;
		String newCurrencyCode = "";
		BigInteger baseCurrencyId = null;
		BigInteger toCurrencyId = null;
		if (courseSearchDto.getCurrencyCode() != null && !courseSearchDto.getCurrencyCode().isEmpty()) {
			baseCurrencyId = getCurrencyByCode(IConstant.DEFAULT_BASE_CURRENCY).getId();
			toCurrencyId = getCurrencyByCode(courseSearchDto.getCurrencyCode()).getId();
		}
		for (Object[] row : rows) {
			try {
				Double costRange = null;
				Double localFeesD = null;
				Double intlFeesD = null;
				if (row[4] != null) {
					costRange = Double.valueOf(String.valueOf(row[4]));
				}
				if (row[16] != null) {
					localFeesD = Double.valueOf(String.valueOf(row[16]));
				}
				if (row[17] != null) {
					intlFeesD = Double.valueOf(String.valueOf(row[17]));
				}
				newCurrencyCode = String.valueOf(row[5]);
				if (costRange != null) {
					cost = ConvertionUtil.roundOffToUpper(costRange);
					System.out.println(cost);
				}
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
				courseResponseDto.setLocalFees(localFees + " " + newCurrencyCode);
				courseResponseDto.setIntlFees(intlFees + " " + newCurrencyCode);
				courseResponseDto.setCourseId(new BigInteger(String.valueOf(row[0])));
				courseResponseDto.setCourseName(String.valueOf(row[1]));
				courseResponseDto.setInstituteId(new BigInteger(String.valueOf(row[2])));
				courseResponseDto.setInstituteName(String.valueOf(row[3]));
				courseResponseDto.setDuration(String.valueOf(row[6]));
				courseResponseDto.setDurationTime(String.valueOf(row[7]));
				courseResponseDto.setCityId(new BigInteger(String.valueOf(row[8])));
				courseResponseDto.setCountryId(new BigInteger(String.valueOf(row[9])));
				courseResponseDto.setLocation(String.valueOf(row[10]) + ", " + String.valueOf(row[11]));
				courseResponseDto.setCountryName(String.valueOf(row[11]));
				courseResponseDto.setCityName(String.valueOf(row[10]));

				Integer worldRanking = 0;
				if (null != row[4]) {
					worldRanking = Double.valueOf(String.valueOf(row[12])).intValue();
				}
				courseResponseDto.setWorldRanking(worldRanking.toString());
				courseResponseDto.setCourseLanguage(String.valueOf(row[13]));
				courseResponseDto.setLanguageShortKey(String.valueOf(row[13]));
				courseResponseDto.setStars(String.valueOf(row[14]));
				courseResponseDto.setRequirements(String.valueOf(row[18]));
				courseResponseDto.setTotalCount(rows1.size());
				if (courseSearchDto.getCurrencyCode() != null && !courseSearchDto.getCurrencyCode().isEmpty()) {
					if (row[19] != null) {
						CurrencyConvertorRequest dto = new CurrencyConvertorRequest();
						dto.setFromCurrencyId(baseCurrencyId);
						dto.setToCurrencyId(toCurrencyId);
						dto.setAmount(Double.valueOf(row[19].toString()));
						Double convertedRate = currencyService.convertCurrency(dto);
						if (convertedRate != null) {
							courseResponseDto.setLocalFees(String.valueOf(convertedRate) + " " + courseSearchDto.getCurrencyCode());
						}
					}
					if (row[20] != null) {
						CurrencyConvertorRequest dto = new CurrencyConvertorRequest();
						dto.setFromCurrencyId(baseCurrencyId);
						dto.setToCurrencyId(toCurrencyId);
						dto.setAmount(Double.valueOf(row[20].toString()));
						Double convertedRate = currencyService.convertCurrency(dto);
						if (convertedRate != null) {
							courseResponseDto.setIntlFees(String.valueOf(convertedRate) + " " + courseSearchDto.getCurrencyCode());
						}
					}
				}
				list.add(courseResponseDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(final CourseSearchDto courseSearchDto, final Currency currency,
			final String oldCurrencyCode) {

		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select  min(crs.domestic_fee) as minLocalFees,max(crs.domestic_fee) as maxLocalFees,"
				+ "min(crs.international_fee) as minIntlFees ,max(crs.international_fee ) as maxIntlFees " + "from course crs inner join institute inst "
				+ " on crs.institute_id = inst.id inner join country ctry  on ctry.id = crs.country_id inner join "
				+ "city ci  on ci.id = crs.city_id inner join faculty f  on f.id = crs.faculty_id left join  "
				+ "institute_service iis  on iis.institute_id = inst.id where 1=1 ";

		if (null != courseSearchDto.getCountryIds() && !courseSearchDto.getCountryIds().isEmpty()) {

			String value = "";
			int i = 0;
			for (BigInteger key : courseSearchDto.getCountryIds()) {
				if (i == 0) {
					value = "'" + key + "'";
				} else {
					value = value + "," + "'" + key + "'";
				}
				i++;
			}
			sqlQuery += " and crs.country_id in (" + value + ")";
		}

		if (null != courseSearchDto.getCityIds() && !courseSearchDto.getCityIds().isEmpty()) {

			String value = "";
			int i = 0;
			for (BigInteger key : courseSearchDto.getCityIds()) {
				if (i == 0) {
					value = "'" + key + "'";
				} else {
					value = value + "," + "'" + key + "'";
				}
				i++;
			}
			sqlQuery += " and crs.city_id in (" + value + ")";
		}

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			String value = "";
			int i = 0;
			for (BigInteger key : courseSearchDto.getLevelIds()) {
				if (i == 0) {
					value = "'" + key + "'";
				} else {
					value = value + "," + "'" + key + "'";
				}
				i++;
			}
			sqlQuery += " and f.level_id in (" + value + ")";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {

			String value = "";
			int i = 0;
			for (BigInteger key : courseSearchDto.getFacultyIds()) {
				if (i == 0) {
					value = "'" + key + "'";
				} else {
					value = value + "," + "'" + key + "'";
				}
				i++;
			}
			sqlQuery += " and crs.faculty_id in (" + value + ")";
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

		if (null != courseSearchDto.getServiceIds() && !courseSearchDto.getServiceIds().isEmpty()) {
			String value = "";
			int i = 0;
			for (BigInteger key : courseSearchDto.getFacultyIds()) {
				if (i == 0) {
					value = "'" + key + "'";
				} else {
					value = value + "," + "'" + key + "'";
				}
				i++;
			}
			sqlQuery += " and iis.service_id in (" + value + ")";
		}

		if (null != courseSearchDto.getSearchKey() && !courseSearchDto.getSearchKey().isEmpty()) {
			sqlQuery += " and crs.name like '%" + courseSearchDto.getSearchKey().trim() + "%'";
		}

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		CourseFilterCostResponseDto responseDto = new CourseFilterCostResponseDto();

		Currency oldCurrency = null;
		Double usdConv = 0.00;
		Long minLocalFeesl = 0l, maxLocalFeesl = 0l, minIntlFeesl = 0l, maxIntlFeesl = 0l;
		String newCurrencyCode = "";
		System.out.println(newCurrencyCode + "=" + minIntlFeesl + "=" + maxIntlFeesl);

		for (Object[] row : rows) {
			try {
				Double minLocalFees = null;
				Double maxLocalFees = null;
				Double minIntlFees = null;
				Double maxIntlFees = null;
				if (row[0] != null) {
					minLocalFees = Double.valueOf(String.valueOf(row[0]));
				}
				if (row[1] != null) {
					maxLocalFees = Double.valueOf(String.valueOf(row[1]));
				}
				if (row[2] != null) {
					minIntlFees = Double.valueOf(String.valueOf(row[2]));
				}
				if (row[3] != null) {
					maxIntlFees = Double.valueOf(String.valueOf(row[3]));
				}
				if (oldCurrencyCode != null && !currency.getCode().toLowerCase().equals(oldCurrencyCode.toLowerCase())) {
					oldCurrency = CurrencyUtil.getCurrencyObjByCode(oldCurrencyCode);
					usdConv = 1 / oldCurrency.getConversionRate();
					newCurrencyCode = currency.getCode();
					if (minLocalFees != null) {
						minLocalFees = minLocalFees * usdConv * currency.getConversionRate();
					}
					if (maxLocalFees != null) {
						maxLocalFees = maxLocalFees * usdConv * currency.getConversionRate();
					}
					if (minIntlFees != null) {
						minIntlFees = minIntlFees * usdConv * currency.getConversionRate();
					}
					if (maxIntlFees != null) {
						maxIntlFees = maxIntlFees * usdConv * currency.getConversionRate();
					}
				}

				if (minLocalFees != null) {
					minLocalFeesl = ConvertionUtil.roundOffToUpper(minLocalFees);
				}
				if (maxLocalFees != null) {
					maxLocalFeesl = ConvertionUtil.roundOffToUpper(maxLocalFees);
				}
				if (minIntlFees != null) {
					minIntlFeesl = ConvertionUtil.roundOffToUpper(minIntlFees);
				}
				if (maxIntlFees != null) {
					maxIntlFeesl = ConvertionUtil.roundOffToUpper(maxIntlFees);
				}
				responseDto.setMinCost(minLocalFeesl);
				responseDto.setMaxCost(maxLocalFeesl);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return responseDto;
	}

	// @Override
	public List<GlobalSearchResponseDto> getAllCourseAndInstituteBySearchKeywords(final String[] keywords, final BigInteger pageNumber,
			final BigInteger maxRows) {
		Session session = sessionFactory.getCurrentSession();

		String courseQuery = "", insituteQuery = "";
		System.out.println(insituteQuery);
		if (null != keywords && keywords.length > 0) {
			int i = 0;
			for (String key : keywords) {
				if (null == key || key.isEmpty()) {
					continue;
				}
				boolean toBeRemoved = GlobalSearchWordUtil.isToBeRemovedWord(key);
				if (toBeRemoved) {
					continue;
				}
				if (i == 0) {
					courseQuery = "ci.name like '%" + key + "%'";
				} else {
					courseQuery = courseQuery + " and ci.name like '%" + key + "%'";
				}
				i++;
			}
		}

		String sqlQuery = "select * from (select distinct f.id as id, f.name as name,'COURSE' as type "
				+ "from course crs  left join country ctry  on ctry.id = crs.country_id "
				+ "left join city ci  on ci.id = crs.city_id left join faculty f on f.id =crs.faculty_id " + "where ci.name like '%perth%' " + "union all "
				+ "select distinct inst.id as id,inst.name as name,'INSTITUTE' as type from "
				+ "institute inst  left join country ctry  on ctry.id = inst.country_id "
				+ "left join city ci  on ci.id = inst.city_id left join faculty_level fl  "
				+ "on fl.institute_id = inst.id left join faculty f on f.id =fl.faculty_id " + "where ci.name like '%perth%' ) A " + "order by A.name";

		sqlQuery += ") A ";

		String sortingQuery = " order by A.cost_range asc";

		sqlQuery += sortingQuery + " OFFSET (" + pageNumber + "-1)*" + maxRows + " ROWS FETCH NEXT " + maxRows + " ROWS ONLY";

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> list = new ArrayList<>();
		CourseResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new CourseResponseDto();
			obj.setCourseId(new BigInteger(String.valueOf(row[0])));
			obj.setCourseName(String.valueOf(row[1]));
			obj.setInstituteId(new BigInteger(String.valueOf(row[2])));
			obj.setInstituteName(String.valueOf(row[3]));
			obj.setCost(String.valueOf(row[4]) + " " + String.valueOf(row[5]));
			obj.setDuration(String.valueOf(row[6]));
			obj.setDurationTime(String.valueOf(row[7]));
			obj.setCityId(new BigInteger(String.valueOf(row[8])));
			obj.setCountryId(new BigInteger(String.valueOf(row[9])));
			obj.setLocation(String.valueOf(row[10]) + ", " + String.valueOf(row[11]));
			obj.setWorldRanking(String.valueOf(row[12]));
			obj.setCourseLanguage(String.valueOf(row[13]));
			obj.setLanguageShortKey(String.valueOf(row[13]));
			obj.setStars(String.valueOf(row[14]));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[16])));
			obj.setInstituteImageUrl("https://www.adelaide.edu.au/front/images/mo-orientation.jpg");
			obj.setInstituteLogoUrl("https://global.adelaide.edu.au/v/style-guide2/assets/img/logo.png");
			list.add(obj);
		}
		return null;
	}

	@Override
	public List<CourseResponseDto> getAllCoursesByInstitute(final BigInteger instituteId, final CourseSearchDto courseSearchDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ "inst.id as instId,inst.name as instName,"
				+ " crs.cost_range, crs.currency, crs.duration,crs.duration_time, ci.id as cityId, ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition,crs.domestic_fee,crs.international_fee "
				+ "from course crs  inner join institute inst "
				+ " on crs.institute_id = inst.id inner join country ctry  on ctry.id = crs.country_id inner join "
				+ "city ci  on ci.id = crs.city_id inner join faculty f  on f.id = crs.faculty_id "
				+ "left join institute_service iis  on iis.institute_id = inst.id where crs.institute_id = '" + instituteId + "'";

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and f.level_id in (" + StringUtils.join(courseSearchDto.getLevelIds(), ',') + ")";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in (" + StringUtils.join(courseSearchDto.getFacultyIds(), ',') + ")";
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
			obj.setCourseId(new BigInteger(String.valueOf(row[0])));
			obj.setCourseName(String.valueOf(row[1]));
			obj.setCost(String.valueOf(row[4]) + " " + String.valueOf(row[5]));
			obj.setDuration(String.valueOf(row[6]));
			obj.setDurationTime(String.valueOf(row[7]));
			Integer worldRanking = 0;
			if (null != row[12]) {
				worldRanking = Double.valueOf(String.valueOf(row[12])).intValue();
			}
			obj.setWorldRanking(worldRanking.toString());
			obj.setCourseLanguage(String.valueOf(row[13]));
			obj.setLanguageShortKey(String.valueOf(row[13]));
			obj.setStars(String.valueOf(row[14]));
			obj.setLocalFees(String.valueOf(row[16]) + " " + String.valueOf(row[5]));
			obj.setIntlFees(String.valueOf(row[17]) + " " + String.valueOf(row[5]));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[18])));
			list.add(obj);
		}
		return list;
	}

	public CourseResponseDto getCourse(final BigInteger instituteId, final CourseSearchDto courseSearchDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ "inst.id as instId,inst.name as instName,"
				+ " crs.cost_range,crs.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition,crs.domestic_fee,crs.international_fee "
				+ "from course crs inner join institute inst "
				+ " on crs.institute_id = inst.id inner join country ctry  on ctry.id = crs.country_id inner join "
				+ "city ci  on ci.id = crs.city_id inner join faculty f  on f.id = crs.faculty_id "
				+ "left join institute_service iis  on iis.institute_id = inst.id where crs.institute_id = " + instituteId;

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and f.level_id in (" + StringUtils.join(courseSearchDto.getLevelIds(), ',') + ")";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in (" + StringUtils.join(courseSearchDto.getFacultyIds(), ',') + ")";
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
			obj.setCourseId(new BigInteger(String.valueOf(row[0])));
			obj.setCourseName(String.valueOf(row[1]));
			obj.setCost(String.valueOf(row[4]) + " " + String.valueOf(row[5]));
			obj.setDuration(String.valueOf(row[6]));
			obj.setDurationTime(String.valueOf(row[7]));
			obj.setWorldRanking(String.valueOf(row[12]));
			obj.setCourseLanguage(String.valueOf(row[13]));
			obj.setLanguageShortKey(String.valueOf(row[13]));
			obj.setStars(String.valueOf(row[14]));
			obj.setLocalFees(String.valueOf(row[16]) + " " + String.valueOf(row[5]));
			obj.setIntlFees(String.valueOf(row[17]) + " " + String.valueOf(row[5]));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[18])));
			list.add(obj);
		}
		return obj;
	}

	@Override
	public Map<String, Object> getCourse(final BigInteger courseId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select crs.id as course_id,crs.stars as course_stars,crs.name as course_name,crs.course_lang as course_language,crs.description as course_description,"
						+ "crs.duration as course_duration,crs.duration_time as course_duration_time,crs.world_ranking as world_ranking,ins.id as institute_id,"
						+ "ins.name as institute_name,ins.email as institute_email,ins.phone_number as institute_phone_number,"
						+ "ins.longitute as longitude,ins.latitute as latitute,ins.total_student as ins_total_student,ins.website as ins_website,"
						+ "ins.address as ins_address, crs.currency,crs.cost_range,crs.international_fee,crs.domestic_fee,cty.name as city_name,cntry.name as country_name,"
						+ "fty.name as faulty_name,le.id as level_id,le.name as level_name,cty.id as city_id,cntry.id as country_id,ins.about_us_info as about_us,"
						+ "ins.closing_hour as closing_hour,ins.opening_hour as opening_hour,cj.available_jobs as available_job,cd.student_visa_link as student_visa_link,crs.remarks,crs.intake from course crs "
						+ "inner join institute ins  on ins.id = crs.institute_id inner join country cntry  on cntry.id = crs.country_id inner join city cty "
						+ "on cty.id = crs.city_id inner join faculty fty  on fty.id = crs.faculty_id "
						+ "inner join level le  on crs.level_id = le.id left join city_jobs cj on cty.id=cj.city_id "
						+ "left join country_details cd on cd.country_id=cntry.id where crs.id = '" + courseId + "'");

		List<Object[]> rows = query.list();
		InstituteResponseDto instituteObj = null;
		CourseDto courseObj = null;
		Map<String, Object> map = new HashMap<>();
		for (Object[] row : rows) {
			courseObj = new CourseDto();
			courseObj.setCourseId(new BigInteger(String.valueOf(row[0])));
			courseObj.setStars(String.valueOf(row[1]));
			courseObj.setCourseName(String.valueOf(row[2]));
			courseObj.setCourseLanguage(String.valueOf(row[3]));
			courseObj.setDescription(String.valueOf(row[4]));
			courseObj.setDuration(String.valueOf(row[5]));
			courseObj.setDurationTime(String.valueOf(row[6]));
			courseObj.setWorldRanking(String.valueOf(row[7]));
			courseObj.setIntlFees(String.valueOf(row[19]) + " " + String.valueOf(row[17]));
			courseObj.setLocalFees(String.valueOf(row[20]) + " " + String.valueOf(row[17]));
			courseObj.setCost(String.valueOf(row[18]) + " " + String.valueOf(row[17]));
			courseObj.setFacultyName(String.valueOf(row[23]));
			if (row[24] != null) {
				courseObj.setLevelId(new BigInteger(String.valueOf(row[24])));
			}
			courseObj.setLevelName(String.valueOf(row[25]));
			courseObj.setIntakeDate(String.valueOf(row[34]));
			courseObj.setRemarks(String.valueOf(row[33]));

			instituteObj = new InstituteResponseDto();
			instituteObj.setInstituteId(new BigInteger(String.valueOf(row[8])));
			instituteObj.setStars(String.valueOf(row[1]));
			instituteObj.setInstituteName(String.valueOf(row[9]));
			instituteObj.setWorldRanking(String.valueOf(row[7]));
			instituteObj.setInterEmail(String.valueOf(row[10]));
			instituteObj.setInterPhoneNumber(String.valueOf(row[11]));
			instituteObj.setLongitude(String.valueOf(row[12]));
			instituteObj.setLatitute(String.valueOf(row[13]));
			if (row[14] != null) {
				instituteObj.setTotalNoOfStudents(Integer.parseInt(String.valueOf(row[14])));
			}
			instituteObj.setWebsite(String.valueOf(row[15]));
			instituteObj.setAddress(String.valueOf(row[16]));
			instituteObj.setVisaRequirement(String.valueOf(row[32]));
			instituteObj.setAboutUs(String.valueOf(row[28]));
			instituteObj.setClosingHour(String.valueOf(row[29]));
			instituteObj.setOpeningHour(String.valueOf(row[30]));
			instituteObj.setLocation(String.valueOf(row[21]) + "," + String.valueOf(row[22]));
			instituteObj.setCountryName(String.valueOf(row[22]));
			instituteObj.setCityName(String.valueOf(row[21]));
			if (row[26] != null) {
				instituteObj.setCityId(new BigInteger(String.valueOf(row[26])));
			}
			if (row[27] != null) {
				instituteObj.setCountryId(new BigInteger(String.valueOf(row[27])));
			}
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

	@Override
	public List<CourseResponseDto> getCouresesByFacultyId(final BigInteger facultyId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class);
		crit.add(Restrictions.eq("facultyObj.id", facultyId));
		crit.addOrder(Order.asc("name"));
		List<Course> courses = crit.list();
		List<CourseResponseDto> dtos = new ArrayList<>();
		for (Course course : courses) {
			CourseResponseDto courseObj = new CourseResponseDto();
			courseObj.setCourseId(course.getId());
			courseObj.setStars(course.getStars());
			courseObj.setCourseName(course.getName());
			courseObj.setCourseLanguage(course.getCourseLang());
			courseObj.setDuration(course.getDuration());
			courseObj.setDurationTime(course.getDurationTime());
			courseObj.setWorldRanking(course.getWorldRanking());
			dtos.add(courseObj);
		}
		return dtos;
	}

	@Override
	public List<CourseResponseDto> getCouresesByListOfFacultyId(final String facultyId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select distinct c.id, c.name as name  from course c  " + "where c.faculty_id in (" + facultyId + ") ORDER BY c.name");
		List<Object[]> rows = query.list();
		List<CourseResponseDto> dtos = new ArrayList<>();
		CourseResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new CourseResponseDto();
			obj.setCourseId(new BigInteger(row[0].toString()));
			obj.setCourseName(row[1].toString());
			dtos.add(obj);
		}
		CourseResponseDto allObject = new CourseResponseDto();
		allObject.setCourseId(new BigInteger("111111"));
		allObject.setCourseName("All");
		dtos.add(allObject);
		return dtos;
	}

	@Override
	public int findTotalCount() {
		int status = 1;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id from course sa where sa.is_active = " + status + " and sa.deleted_on IS NULL";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public List<CourseRequest> getAll(final Integer pageNumber, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select c.id ,c.c_id, c.institute_id, c.country_id , c.city_id, c.faculty_id, c.name , "
				+ "c.description, c.intake, c.duration, c.course_lang, c.domestic_fee, c.international_fee,"
				+ "c.availbilty, c.study_mode, c.created_by, c.updated_by, c.campus_location, c.website,"
				+ " c.recognition_type, c.part_full, c.course_link, c.updated_on, c.world_ranking, c.stars, c.duration_time, c.remarks  FROM course c "
				+ " where c.is_active = 1 and c.deleted_on IS NULL ORDER BY c.created_on DESC ";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseRequest> courses = new ArrayList<>();
		CourseRequest obj = null;
		for (Object[] row : rows) {
			obj = new CourseRequest();
			obj.setCourseId(new BigInteger(row[0].toString()));
			obj.setcId(Integer.valueOf(row[1].toString()));
			if (row[2] != null) {
				obj.setInstituteId(new BigInteger(row[2].toString()));
				obj.setInstituteName(getInstituteName(row[2].toString(), session));
				obj.setCost(getCost(row[2].toString(), session));
				Institute institute = getInstitute(row[2].toString(), session);
				obj.setInstituteId(new BigInteger(row[2].toString()));
				obj.setInstituteName(institute.getName());
				obj.setCost(getCost(row[2].toString(), session));
				if (row[3] != null) {
					Country country = getCountry(row[3].toString(), session);
					obj.setInstituteLogoUrl(getInstituteLogo(country.getName(), institute.getName()));
					obj.setInstituteImageUrls(getInstituteImage(country.getName(), institute.getName()));
				}
			}
			if (row[3] != null) {
				obj.setCountryId(new BigInteger(row[3].toString()));
				obj.setLocation(getLocationName(row[3].toString(), session));
			}
			obj.setCityId(new BigInteger(row[4].toString()));
			obj.setFacultyId(new BigInteger(row[5].toString()));
			if (row[6] != null) {
				obj.setName(row[6].toString());
			}
			if (row[7] != null) {
				obj.setDescription(row[7].toString());
			}
			if (row[8] != null) {
				obj.setIntake(row[8].toString());
			}
			if (row[9] != null) {
				obj.setDuration(row[9].toString());
			}
			if (row[10] != null) {
				obj.setLanguaige(row[10].toString());
			}
			if (row[11] != null) {
				obj.setDomasticFee(row[11].toString());
			}
			if (row[12] != null) {
				obj.setInternationalFee(row[12].toString());
			}
			if (row[13] != null) {
				// obj.setGrades(row[13].toString());
			}
			if (row[14] != null) {
				obj.setDocumentUrl(row[14].toString());
			}
			if (row[15] != null) {
				// obj.setContact(row[15].toString());
			}
			if (row[16] != null) {
				// obj.setOpeningHourFrom(row[16].toString());
			}
			if (row[17] != null) {
				// obj.setCampusLocation(row[17].toString());
			}
			if (row[18] != null) {
				obj.setWebsite(row[18].toString());
			}
			if (row[19] != null) {
				// obj.setPartTime(row[19].toString());
			}
			if (row[20] != null) {
				obj.setFullTime(row[20].toString());
			}
			if (row[21] != null) {
				obj.setCourseLink(row[21].toString());
			}
			if (row[22] != null) {
				System.out.println(row[2].toString());
				System.out.println(row[22].toString());
				Date createdDate = (Date) row[22];
				System.out.println(createdDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(createdDate);
				obj.setLastUpdated(dateResult);

			}
			if (row[23] != null) {
				obj.setWorldRanking(row[23].toString());
			}
			if (row[24] != null) {
				obj.setStars(row[24].toString());
			}
			if (row[25] != null) {
				obj.setDurationTime(row[25].toString());
			}
			if (row[26] != null) {
				obj.setRequirements(row[26].toString());
			}
			obj.setEnglishEligibility(getEnglishEligibility(session, obj.getCourseId()));
			courses.add(obj);
		}
		return courses;
	}

	private String getLocationName(final String id, final Session session) {
		String name = null;
		if (id != null) {
			Country obj = session.get(Country.class, new BigInteger(id));
			name = obj.getName();
		}
		return name;
	}

	private Country getCountry(final String id, final Session session) {
		Country obj = null;
		if (id != null) {
			obj = session.get(Country.class, new BigInteger(id));
		}
		return obj;
	}

	private String getInstituteName(final String id, final Session session) {
		String name = null;
		if (id != null) {
			Institute obj = session.get(Institute.class, new BigInteger(id));
			name = obj.getName();
		}
		return name;
	}

	private Institute getInstitute(final String id, final Session session) {
		Institute obj = null;
		if (id != null) {
			obj = session.get(Institute.class, new BigInteger(id));
		}
		return obj;
	}

	private CourseEnglishEligibility getEnglishEligibility(final Session session, final BigInteger courseId) {
		CourseEnglishEligibility eligibility = null;
		Criteria crit = session.createCriteria(CourseEnglishEligibility.class);
		crit.add(Restrictions.eq("course.id", courseId));
		if (!crit.list().isEmpty()) {
			eligibility = (CourseEnglishEligibility) crit.list().get(0);
		}
		return eligibility;
	}

	@Override
	public List<CourseRequest> searchCoursesBasedOnFilter(final String sqlQuery) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseRequest> courses = new ArrayList<>();
		CourseRequest obj = null;
		for (Object[] row : rows) {
			obj = new CourseRequest();
			obj.setCourseId(new BigInteger(row[0].toString()));
			obj.setcId(Integer.valueOf(row[1].toString()));
			if (row[2] != null) {
				obj.setInstituteId(new BigInteger(row[2].toString()));
				obj.setInstituteName(getInstituteName(row[2].toString(), session));
				obj.setCost(getCost(row[2].toString(), session));
				Institute institute = getInstitute(row[2].toString(), session);
				obj.setInstituteId(new BigInteger(row[2].toString()));
				obj.setInstituteName(institute.getName());
				obj.setCost(getCost(row[2].toString(), session));
				if (row[3] != null) {
					Country country = getCountry(row[3].toString(), session);
					obj.setInstituteLogoUrl(getInstituteLogo(country.getName(), institute.getName()));
					obj.setInstituteImageUrls(getInstituteImage(country.getName(), institute.getName()));
				}
			}
			if (row[3] != null) {
				obj.setCountryId(new BigInteger(row[3].toString()));
				obj.setLocation(getLocationName(row[3].toString(), session));
			}
			obj.setCityId(new BigInteger(row[4].toString()));
			obj.setFacultyId(new BigInteger(row[5].toString()));
			obj.setName(row[6].toString());
			if (row[7] != null) {
				obj.setDescription(row[7].toString());
			}
			if (row[8] != null) {
				obj.setIntake(row[8].toString());
			}
			if (row[9] != null) {
				obj.setDuration(row[9].toString());
			}
			if (row[10] != null) {
				obj.setLanguaige(row[10].toString());
			}
			if (row[11] != null) {
				obj.setDomasticFee(row[11].toString());
			}
			if (row[12] != null) {
				obj.setInternationalFee(row[12].toString());
			}
			if (row[13] != null) {
				obj.setGrades(row[13].toString());
			}
			if (row[14] != null) {
				obj.setDocumentUrl(row[14].toString());
			}
			if (row[15] != null) {
				obj.setContact(row[15].toString());
			}
			if (row[16] != null) {
				obj.setOpeningHourFrom(row[16].toString());
			}
			if (row[17] != null) {
				obj.setCampusLocation(row[17].toString());
			}
			if (row[18] != null) {
				obj.setWebsite(row[18].toString());
			}
			if (row[19] != null) {
				obj.setPartTime(row[19].toString());
			}
			if (row[20] != null) {
				obj.setFullTime(row[20].toString());
			}
			if (row[21] != null) {
				obj.setCourseLink(row[21].toString());
			}
			if (row[22] != null) {
				System.out.println(row[2].toString());
				System.out.println(row[22].toString());
				Date createdDate = (Date) row[22];
				System.out.println(createdDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(createdDate);
				obj.setLastUpdated(dateResult);
			}
			if (row[23] != null) {
				obj.setWorldRanking(row[23].toString());
			}
			if (row[24] != null) {
				obj.setStars(row[24].toString());
			}
			if (row[25] != null) {
				obj.setDurationTime(row[25].toString());
			}
			obj.setEnglishEligibility(getEnglishEligibility(session, obj.getCourseId()));
			courses.add(obj);
		}
		return courses;
	}

	@Override
	public List<CourseRequest> getUserCourse(final BigInteger userId, final Integer pageNumber, final Integer pageSize, final String currencyCode,
			final String sortBy, final boolean sortType) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select c.id ,c.c_id, c.institute_id, c.country_id , c.city_id, c.faculty_id, c.name , "
				+ "c.description, c.intake,c.duration, c.course_lang,c.domestic_fee, c.international_fee,"
				+ " c.availbilty, c.study_mode, c.created_by, c.updated_by, c.campus_location, c.website,"
				+ " c.recognition_type, c.part_full, c.course_link, c.updated_on, c.world_ranking, c.stars, c.duration_time, c.remarks  FROM  user_my_course umc left join course c on umc.course_id = c.id "
				+ " where umc.is_active = 1 and umc.deleted_on IS NULL and umc.user_id = " + userId + "  ORDER BY c.created_on DESC ";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseRequest> courses = new ArrayList<>();
		CourseRequest obj = null;
		for (Object[] row : rows) {
			obj = new CourseRequest();
			obj.setCourseId(new BigInteger(row[0].toString()));
			obj.setcId(Integer.valueOf(row[1].toString()));
			if (row[2] != null) {
				Institute institute = getInstitute(row[2].toString(), session);
				obj.setInstituteId(new BigInteger(row[2].toString()));
				obj.setInstituteName(institute.getName());
				obj.setCost(getCost(row[2].toString(), session));
				if (row[3] != null) {
					Country country = getCountry(row[3].toString(), session);
					obj.setInstituteLogoUrl(getInstituteLogo(country.getName(), institute.getName()));
					obj.setInstituteImageUrls(getInstituteImage(country.getName(), institute.getName()));
				}
			}
			if (row[3] != null) {
				obj.setCountryId(new BigInteger(row[3].toString()));
				obj.setLocation(getLocationName(row[3].toString(), session));
			}
			obj.setCityId(new BigInteger(row[4].toString()));
			obj.setFacultyId(new BigInteger(row[5].toString()));
			obj.setName(row[6].toString());
			if (row[7] != null) {
				obj.setDescription(row[7].toString());
			}
			if (row[8] != null) {
				obj.setIntake(row[8].toString());
			}
			if (row[9] != null) {
				obj.setDuration(row[9].toString());
			}
			if (row[10] != null) {
				obj.setLanguaige(row[10].toString());
			}
			if (row[11] != null) {
				obj.setDomasticFee(row[11].toString());
			}
			if (row[12] != null) {
				obj.setInternationalFee(row[12].toString());
			}
			if (row[13] != null) {
				obj.setGrades(row[13].toString());
			}
			if (row[14] != null) {
				obj.setDocumentUrl(row[14].toString());
			}
			if (row[15] != null) {
				obj.setContact(row[15].toString());
			}
			if (row[16] != null) {
				obj.setOpeningHourFrom(row[16].toString());
			}
			if (row[17] != null) {
				obj.setCampusLocation(row[17].toString());
			}
			if (row[18] != null) {
				obj.setWebsite(row[18].toString());
			}
			if (row[19] != null) {
				obj.setPartTime(row[19].toString());
			}
			if (row[20] != null) {
				obj.setFullTime(row[20].toString());
			}
			if (row[21] != null) {
				obj.setCourseLink(row[21].toString());
			}
			if (row[22] != null) {
				System.out.println(row[2].toString());
				System.out.println(row[22].toString());
				Date createdDate = (Date) row[22];
				System.out.println(createdDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(createdDate);
				obj.setLastUpdated(dateResult);
			}
			if (row[23] != null) {
				obj.setWorldRanking(row[23].toString());
			}
			if (row[24] != null) {
				obj.setStars(row[24].toString());
			}
			if (row[25] != null) {
				obj.setDurationTime(row[25].toString());
			}
			if (row[26] != null) {
				obj.setRequirements(row[26].toString());
			}
			obj.setEnglishEligibility(getEnglishEligibility(session, new BigInteger(row[0].toString())));
			courses.add(obj);
		}
		return courses;
	}

	private String getCost(final String instituteId, final Session session) {
		String cost = null;
		Query query = session.createSQLQuery("select c.id, c.avg_cost_of_living from institute c  where c.id=" + instituteId);
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			if (row[1] != null) {
				cost = row[1].toString();
			}
		}
		return cost;
	}

	private List<String> getInstituteImage(final String countryName, final String instituteName) {
		List<String> images = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			images.add(CDNServerUtil.getInstituteImages(countryName, instituteName, i));
		}
		return images;
	}

	private String getInstituteLogo(final String countryName, final String instituteName) {
		return CDNServerUtil.getInstituteLogoImage(countryName, instituteName);
	}

	@Override
	public int findTotalCountByUserId(final BigInteger userId) {
		int status = 1;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id from user_my_course sa where sa.is_active = " + status + " and sa.deleted_on IS NULL and sa.user_id=" + userId;
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
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
	public List<UserCompareCourse> getUserCompareCourse(final BigInteger userId) {
		List<UserCompareCourse> compareCourses = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select ucc.id, ucc.compare_value FROM  user_compare_course ucc where ucc.deleted_on IS NULL and ucc.user_id=" + userId;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		UserCompareCourse obj = null;
		for (Object[] row : rows) {
			obj = new UserCompareCourse();
			obj.setId(new BigInteger(row[0].toString()));
			if (row[1] != null) {
				obj.setCompareValue(row[1].toString());
			}
			compareCourses.add(obj);
		}
		return compareCourses;
	}

	@Override
	public CourseRequest getCourseById(final Integer courseId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select c.id ,c.c_id, c.institute_id, c.country_id , c.city_id, c.faculty_id, c.name , "
				+ "c.description, c.intake,c.duration, c.course_lang, c.domestic_fee, c.international_fee,"
				+ "c.availbilty, c.study_mode, c.created_by, c.updated_by, c.campus_location, c.website,"
				+ " c.recognition_type, c.part_full, c.course_link, c.updated_on, c.world_ranking, c.stars, c.duration_time,c.remarks  FROM course c "
				+ " where c.id=" + courseId;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		CourseRequest courseRequest = null;
		for (Object[] row : rows) {
			courseRequest = new CourseRequest();
			courseRequest.setCourseId(new BigInteger(row[0].toString()));
			courseRequest.setcId(Integer.valueOf(row[1].toString()));
			if (row[2] != null) {
				courseRequest.setInstituteId(new BigInteger(row[2].toString()));
				courseRequest.setInstituteName(getInstituteName(row[2].toString(), session));
				Institute institute = getInstitute(row[2].toString(), session);
				courseRequest.setInstituteId(new BigInteger(row[2].toString()));
				courseRequest.setInstituteName(institute.getName());
				courseRequest.setCost(getCost(row[2].toString(), session));
				if (row[3] != null) {
					Country country = getCountry(row[3].toString(), session);
					courseRequest.setInstituteLogoUrl(getInstituteLogo(country.getName(), institute.getName()));
					courseRequest.setInstituteImageUrls(getInstituteImage(country.getName(), institute.getName()));
				}
			}
			if (row[3] != null) {
				courseRequest.setCountryId(new BigInteger(row[3].toString()));
				courseRequest.setLocation(getLocationName(row[3].toString(), session));
			}
			courseRequest.setCityId(new BigInteger(row[4].toString()));
			courseRequest.setFacultyId(new BigInteger(row[5].toString()));
			courseRequest.setName(row[6].toString());
			if (row[7] != null) {
				courseRequest.setDescription(row[7].toString());
			}
			if (row[8] != null) {
				courseRequest.setIntake(row[8].toString());
			}
			if (row[9] != null) {
				courseRequest.setDuration(row[9].toString());
			}
			if (row[10] != null) {
				courseRequest.setLanguaige(row[10].toString());
			}
			if (row[11] != null) {
				courseRequest.setDomasticFee(row[11].toString());
			}
			if (row[12] != null) {
				courseRequest.setInternationalFee(row[12].toString());
			}
			if (row[13] != null) {
				courseRequest.setGrades(row[13].toString());
			}
			if (row[14] != null) {
				courseRequest.setDocumentUrl(row[14].toString());
			}
			if (row[15] != null) {
				courseRequest.setContact(row[15].toString());
			}
			if (row[16] != null) {
				courseRequest.setOpeningHourFrom(row[16].toString());
			}
			if (row[17] != null) {
				courseRequest.setCampusLocation(row[17].toString());
			}
			if (row[18] != null) {
				courseRequest.setWebsite(row[18].toString());
			}
			if (row[19] != null) {
				courseRequest.setPartTime(row[19].toString());
			}
			if (row[20] != null) {
				courseRequest.setFullTime(row[20].toString());
			}
			if (row[21] != null) {
				courseRequest.setCourseLink(row[21].toString());
			}
			if (row[22] != null) {
				System.out.println(row[2].toString());
				System.out.println(row[22].toString());
				Date createdDate = (Date) row[22];
				System.out.println(createdDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(createdDate);
				courseRequest.setLastUpdated(dateResult);
			}
			if (row[23] != null) {
				courseRequest.setWorldRanking(row[23].toString());
			}
			if (row[24] != null) {
				courseRequest.setStars(row[24].toString());
			}
			if (row[25] != null) {
				courseRequest.setDurationTime(row[25].toString());
			}
			if (row[26] != null) {
				courseRequest.setRequirements(row[26].toString());
			}
			courseRequest.setEnglishEligibility(getEnglishEligibility(session, new BigInteger(row[0].toString())));
		}
		return courseRequest;
	}

	@Override
	public List<YoutubeVideo> getYoutubeDataforCourse(final BigInteger instituteId, final Set<String> keyword) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sqlQuery = new StringBuilder("select * from youtube_video where type ='Institution'");

		if (!keyword.isEmpty()) {
			String queryString = keyword.stream().collect(Collectors.joining("%' or '%"));
			sqlQuery.append(" and ( (video_title like '%").append(queryString).append("%'").append(")");
			sqlQuery.append(" or ( description like '%").append(queryString).append("%'").append("))");
			sqlQuery.append(" LIMIT 0,10");
		}

		Query query = session.createSQLQuery(sqlQuery.toString());
		List<Object[]> rows;
		rows = query.list();
		if (rows.size() == 0) {
			StringBuilder sqlQuery1 = new StringBuilder("select * from youtube_video where type ='Institution'");
			sqlQuery.append(" LIMIT 0,10");
			Query query1 = session.createSQLQuery(sqlQuery1.toString());
			rows = query1.list();
		}
		List<YoutubeVideo> resultList = new ArrayList<>();
		for (Object[] row : rows) {
			YoutubeVideo youtubeVideo = new YoutubeVideo();
			youtubeVideo.setYoutubeVideoId(new BigInteger(row[0].toString()));
			youtubeVideo.setId(new BigInteger(row[1].toString()));
			youtubeVideo.setVideoTitle(row[3].toString());
			youtubeVideo.setDescription(row[4].toString());
			youtubeVideo.setVedioId(row[5].toString());
			youtubeVideo.setUrl(row[6].toString());
			resultList.add(youtubeVideo);
		}

		return resultList;
	}

	@Override
	public Course getCourseData(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		Course course = null;
		if (id != null) {
			course = session.get(Course.class, id);
		}
		return course;
	}
}