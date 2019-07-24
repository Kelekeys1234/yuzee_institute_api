package com.seeka.app.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.Institute;
import com.seeka.app.dto.CourseDto;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.GlobalSearchResponseDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.jobs.CurrencyUtil;
import com.seeka.app.util.ConvertionUtil;
import com.seeka.app.util.GlobalSearchWordUtil;

@Repository
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class CourseDAO implements ICourseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Course obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(Course obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public Course get(BigInteger id) {
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

    @Override
    public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto courseSearchDto, Currency currency, BigInteger userCountryId) {
        Session session = sessionFactory.getCurrentSession();

        String sqlQuery = "select distinct crs.id as courseId,crs.name as courseName," + "inst.id as instId,inst.name as instName, cp.cost_range, "
                        + "cp.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
                        + "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition, cp.local_fees, cp.intl_fees,crs.remarks "
                        + "from course crs  inner join course_pricing cp  on cp.course_id = crs.id inner join institute inst "
                        + " on crs.institute_id = inst.id inner join country ctry  on ctry.id = crs.country_id inner join "
                        + "city ci  on ci.id = crs.city_id inner join faculty f  on f.id = crs.faculty_id "
                        + "left join institute_service iis  on iis.institute_id = inst.id where 1=1";

        boolean showIntlCost = false;

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

            if (null != userCountryId && courseSearchDto.getCountryIds().size() == 1 && !courseSearchDto.getCountryIds().get(0).equals(userCountryId)) {
                showIntlCost = true;
            }

            // sqlQuery += " and crs.country_id in ("+StringUtils.join(filterObj.getCountryIds(), ',')+")";
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
            // sqlQuery += " and iis.service_id in ("+StringUtils.join(filterObj.getServiceIds(), ',')+")";
        }

        if (null != courseSearchDto.getMinCost() && courseSearchDto.getMinCost() >= 0) {
            sqlQuery += " and cp.cost_range >= " + courseSearchDto.getMinCost();
        }

        if (null != courseSearchDto.getMaxCost() && courseSearchDto.getMaxCost() >= 0) {
            sqlQuery += " and cp.cost_range <= " + courseSearchDto.getMaxCost();
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
        if (null != courseSearchDto.getSortingObj()) {
            CourseSearchFilterDto sortingObj = courseSearchDto.getSortingObj();
            if (null != sortingObj.getPrice() && !sortingObj.getPrice().isEmpty()) {
                if (sortingObj.getPrice().equals("ASC")) {
                    sortingQuery = " order by cp.cost_range asc";
                } else {
                    sortingQuery = " order by cp.cost_range desc";
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
            sortingQuery = " order by cp.intl_fees asc";
        }
        /*
         * sqlQuery += sortingQuery + " OFFSET (" + filterObj.getPageNumber() + "-1)*" + filterObj.getMaxSizePerPage() + " ROWS FETCH NEXT " + filterObj.getMaxSizePerPage() +
         * " ROWS ONLY";
         */
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

        List<CourseResponseDto> list = new ArrayList<CourseResponseDto>();
        CourseResponseDto obj = null;
        Currency oldCurrency = null;
        Double usdConv = 0.00;
        Long cost = 0l, localFees = 0l, intlFees = 0l;
        String newCurrencyCode = "";
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
                if (null != currency) {
                    String oldCurrencyCode = String.valueOf(row[5]);
                    oldCurrency = CurrencyUtil.getCurrencyObjByCode(oldCurrencyCode);
                    usdConv = 1 / oldCurrency.getConversionRate();
                    newCurrencyCode = currency.getCode();
                    costRange = costRange * usdConv * currency.getConversionRate();
                    localFeesD = localFeesD * usdConv * currency.getConversionRate();
                    intlFeesD = intlFeesD * usdConv * currency.getConversionRate();
                }
                if (costRange != null) {
                    cost = ConvertionUtil.roundOffToUpper(costRange);
                }
                if (localFeesD != null) {
                    localFees = ConvertionUtil.roundOffToUpper(localFeesD);
                }
                if (intlFeesD != null) {
                    intlFees = ConvertionUtil.roundOffToUpper(intlFeesD);
                }
                obj = new CourseResponseDto();
                if (showIntlCost) {
                    obj.setCost(intlFees + " " + newCurrencyCode);
                } else {
                    obj.setCost(localFees + " " + newCurrencyCode);
                }
                obj.setLocalFees(localFees + " " + newCurrencyCode);
                obj.setIntlFees(intlFees + " " + newCurrencyCode);
                obj.setCourseId(new BigInteger((String.valueOf(row[0]))));
                obj.setCourseName(String.valueOf(row[1]));
                obj.setInstituteId(new BigInteger((String.valueOf(row[2]))));
                obj.setInstituteName(String.valueOf(row[3]));
                obj.setDuration(String.valueOf(row[6]));
                obj.setDurationTime(String.valueOf(row[7]));
                obj.setCityId(new BigInteger((String.valueOf(row[8]))));
                obj.setCountryId(new BigInteger((String.valueOf(row[9]))));
                obj.setLocation(String.valueOf(row[10]) + ", " + String.valueOf(row[11]));
                obj.setCountryName(String.valueOf(row[11]));
                obj.setCityName(String.valueOf(row[10]));

                Integer worldRanking = 0;
                if (null != row[4]) {
                    worldRanking = Double.valueOf(String.valueOf(row[12])).intValue();
                }
                obj.setWorldRanking(worldRanking.toString());
                obj.setCourseLanguage(String.valueOf(row[13]));
                obj.setLanguageShortKey(String.valueOf(row[13]));
                obj.setStars(String.valueOf(row[14]));
                obj.setRequirements(String.valueOf(row[18]));
                obj.setTotalCount(rows1.size());
                list.add(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    @Override
    public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(CourseSearchDto courseSearchDto, Currency currency, String oldCurrencyCode) {

        Session session = sessionFactory.getCurrentSession();

        String sqlQuery = "select  min(cp.local_fees) as minLocalFees,max(cp.local_fees) as maxLocalFees," + "min(cp.intl_fees) as minIntlFees ,max(cp.intl_fees ) as maxIntlFees "
                        + "from course crs  inner join course_pricing cp  on cp.course_id = crs.id inner join institute inst "
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
                if (oldCurrencyCode != null && (!currency.getCode().toLowerCase().equals(oldCurrencyCode.toLowerCase()))) {
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
    public List<GlobalSearchResponseDto> getAllCourseAndInstituteBySearchKeywords(String[] keywords, BigInteger pageNumber, BigInteger maxRows) {
        Session session = sessionFactory.getCurrentSession();

        String courseQuery = "", insituteQuery = "";
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

        String sqlQuery = "select * from (select distinct f.id as id, f.name as name,'COURSE' as type " + "from course crs  left join country ctry  on ctry.id = crs.country_id "
                        + "left join city ci  on ci.id = crs.city_id left join faculty f on f.id =crs.faculty_id " + "where ci.name like '%perth%' " + "union all "
                        + "select distinct inst.id as id,inst.name as name,'INSTITUTE' as type from " + "institute inst  left join country ctry  on ctry.id = inst.country_id "
                        + "left join city ci  on ci.id = inst.city_id left join faculty_level fl  " + "on fl.institute_id = inst.id left join faculty f on f.id =fl.faculty_id "
                        + "where ci.name like '%perth%' ) A " + "order by A.name";

        sqlQuery += ") A ";

        String sortingQuery = " order by A.cost_range asc";

        sqlQuery += sortingQuery + " OFFSET (" + pageNumber + "-1)*" + maxRows + " ROWS FETCH NEXT " + maxRows + " ROWS ONLY";

        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<CourseResponseDto> list = new ArrayList<CourseResponseDto>();
        CourseResponseDto obj = null;
        for (Object[] row : rows) {
            obj = new CourseResponseDto();
            obj.setCourseId(new BigInteger((String.valueOf(row[0]))));
            obj.setCourseName(String.valueOf(row[1]));
            obj.setInstituteId(new BigInteger((String.valueOf(row[2]))));
            obj.setInstituteName(String.valueOf(row[3]));
            obj.setCost(String.valueOf(row[4]) + " " + String.valueOf(row[5]));
            obj.setDuration(String.valueOf(row[6]));
            obj.setDurationTime(String.valueOf(row[7]));
            obj.setCityId(new BigInteger((String.valueOf(row[8]))));
            obj.setCountryId(new BigInteger((String.valueOf(row[9]))));
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
    public List<CourseResponseDto> getAllCoursesByInstitute(BigInteger instituteId, CourseSearchDto courseSearchDto) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName," + "inst.id as instId,inst.name as instName,"
                        + " cp.cost_range,cp.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
                        + "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition,cp.local_fees,cp.intl_fees "
                        + "from course crs  inner join course_pricing cp  on cp.course_id = crs.id inner join institute inst "
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
            sqlQuery += " and cp.cost_range >= " + courseSearchDto.getMinCost();
        }

        if (null != courseSearchDto.getMaxCost() && courseSearchDto.getMaxCost() >= 0) {
            sqlQuery += " and cp.cost_range <= " + courseSearchDto.getMaxCost();
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
        List<CourseResponseDto> list = new ArrayList<CourseResponseDto>();
        CourseResponseDto obj = null;
        for (Object[] row : rows) {
            obj = new CourseResponseDto();
            obj.setCourseId(new BigInteger((String.valueOf(row[0]))));
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

    public CourseResponseDto getCourse(BigInteger instituteId, CourseSearchDto courseSearchDto) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName," + "inst.id as instId,inst.name as instName,"
                        + " cp.cost_range,cp.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
                        + "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition,cp.local_fees,cp.intl_fees "
                        + "from course crs  inner join course_pricing cp  on cp.course_id = crs.id inner join institute inst "
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
            sqlQuery += " and cp.cost_range >= " + courseSearchDto.getMinCost();
        }

        if (null != courseSearchDto.getMaxCost() && courseSearchDto.getMaxCost() >= 0) {
            sqlQuery += " and cp.cost_range <= " + courseSearchDto.getMaxCost();
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
        List<CourseResponseDto> list = new ArrayList<CourseResponseDto>();
        CourseResponseDto obj = null;
        for (Object[] row : rows) {
            obj = new CourseResponseDto();
            obj.setCourseId(new BigInteger((String.valueOf(row[0]))));
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
    public Map<String, Object> getCourse(BigInteger courseId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                        .createSQLQuery("select crs.id as crs_id,crs.stars as crs_stars,crs.name as crs_name,crs.course_lang as crs_cour_lang,crs.description as crs_desc,crs.duration as crs_dur,crs.duration_time as crs_du_time,crs.world_ranking as crs_word_ranking, "
                                        + "ins.id as ins_id,ins.name as ins_name,crs.world_ranking as ins_wrld_rank,ins.int_emails as ins_int_emails,ins.int_ph_num as ins_int_ph_num,ins.longitude as ins_longitude,ins.latitude as ins_latitude,ins.t_num_of_stu as ins_t_num_of_stu,ins.website as ins_website,ins.address as ins_address, "
                                        + "id.about_us_info as ab_us_info,id.closing_hour as close_hour,id.opening_hour as open_hour, "
                                        + "cp.currency,cp.cost_range,cp.intl_fees,cp.local_fees, "
                                        + "cty.name as city_name,cntry.name as country_name,fty.name as faulty_name,le.id as le_id,le.name as le_name,cty.id as cityid,cntry.id as countryid,cntry.visa as visa from course crs  "
                                        + "inner join institute ins  on ins.id = crs.institute_id inner join country cntry  on cntry.id = crs.country_id "
                                        + "inner join city cty  on cty.id = crs.city_id inner join faculty fty  on fty.id = crs.faculty_id inner join institute_details id  on id.institute_id = crs.institute_id "
                                        + "inner join level le  on fty.level_id = le.id inner join course_pricing cp  on cp.course_id = crs.id where " + "crs.id = '" + courseId
                                        + "'");

        List<Object[]> rows = query.list();
        InstituteResponseDto instituteObj = null;
        CourseDto courseObj = null;
        Map<String, Object> map = new HashMap<>();
        for (Object[] row : rows) {
            courseObj = new CourseDto();
            courseObj.setCourseId(new BigInteger((String.valueOf(row[0]))));
            courseObj.setStars(String.valueOf(row[1]));
            courseObj.setCourseName(String.valueOf(row[2]));
            courseObj.setCourseLanguage(String.valueOf(row[3]));
            courseObj.setDescription(String.valueOf(row[4]));
            courseObj.setDuration(String.valueOf(row[5]));
            courseObj.setDurationTime(String.valueOf(row[6]));
            courseObj.setWorldRanking(String.valueOf(row[7]));
            courseObj.setIntlFees(String.valueOf(row[23]) + " " + String.valueOf(row[21]));
            courseObj.setLocalFees(String.valueOf(row[24]) + " " + String.valueOf(row[21]));
            courseObj.setCost(String.valueOf(row[22]) + " " + String.valueOf(row[21]));
            courseObj.setFacultyName(String.valueOf(row[27]));
            courseObj.setLevelId(new BigInteger((String.valueOf(row[28]))));
            courseObj.setLevelName(String.valueOf(row[29]));

            instituteObj = new InstituteResponseDto();
            instituteObj.setInstituteId(new BigInteger((String.valueOf(row[8]))));
            instituteObj.setStars(String.valueOf(row[1]));
            instituteObj.setInstituteName(String.valueOf(row[9]));
            instituteObj.setWorldRanking(String.valueOf(row[7]));
            instituteObj.setInterEmail(String.valueOf(row[11]));
            instituteObj.setInterPhoneNumber(String.valueOf(row[12]));
            instituteObj.setLongitude(String.valueOf(row[13]));
            instituteObj.setLatitute(String.valueOf(row[14]));
            instituteObj.setTotalNoOfStudents(Integer.parseInt(String.valueOf(row[15])));
            instituteObj.setWebsite(String.valueOf(row[16]));
            instituteObj.setAddress(String.valueOf(row[17]));
            instituteObj.setVisaRequirement(String.valueOf(row[32]));
            instituteObj.setAboutUs(String.valueOf(row[18]));
            instituteObj.setClosingHour(String.valueOf(row[19]));
            instituteObj.setOpeningHour(String.valueOf(row[20]));
            instituteObj.setLocation(String.valueOf(row[25]) + "," + String.valueOf(row[26]));
            instituteObj.setCountryName(String.valueOf(row[26]));
            instituteObj.setCityName(String.valueOf(row[25]));
            instituteObj.setCityId(new BigInteger((String.valueOf(row[30]))));
            instituteObj.setCountryId(new BigInteger((String.valueOf(row[31]))));

            map.put("courseObj", courseObj);
            map.put("instituteObj", instituteObj);
        }
        return map;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        list.add(14);
        list.add(450);
        list.add(780);
        System.out.println(list);
    }

    @Override
    public List<CourseResponseDto> getCouresesByFacultyId(BigInteger facultyId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Course.class);
        crit.add(Restrictions.eq("facultyObj.id", facultyId));
        crit.addOrder(Order.asc("name"));
        List<Course> courses = crit.list();
        List<CourseResponseDto> dtos = new ArrayList<CourseResponseDto>();
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
    public List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct c.id, c.name as name  from course c  " + "where c.faculty_id in (" + facultyId + ") ORDER BY c.name");
        List<Object[]> rows = query.list();
        List<CourseResponseDto> dtos = new ArrayList<CourseResponseDto>();
        CourseResponseDto obj = null;
        for (Object[] row : rows) {
            obj = new CourseResponseDto();
            obj.setCourseId(new BigInteger((row[0].toString())));
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
    public List<CourseRequest> getAll(Integer pageNumber, Integer pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select c.id ,c.c_id, c.institute_id, c.country_id , c.city_id, c.faculty_id, c.name , "
                        + "cd.description, cd.intake,c.duration, c.course_lang,cd.domestic_fee,cd.international_fee,"
                        + "cd.grade, cd.file_url, cd.contact, cd.opening_hours, cd.campus_location, cd.website,"
                        + " cd.job_part_time, cd.job_full_time, cd.course_link, c.updated_on  FROM course c inner join course_details cd "
                        + " on c.id = cd.course_id where c.is_active = 1 and c.deleted_on IS NULL ORDER BY c.created_on DESC ";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<CourseRequest> courses = new ArrayList<CourseRequest>();
        CourseRequest obj = null;
        for (Object[] row : rows) {
            obj = new CourseRequest();
            obj.setCourseId(new BigInteger((row[0].toString())));
            obj.setcId(Integer.valueOf(row[1].toString()));
            if (row[2] != null) {
                obj.setInstituteId(new BigInteger((row[2].toString())));
                obj.setInstituteName(getInstituteName(row[2].toString(), session));
            }
            if (row[3] != null) {
                obj.setCountryId(new BigInteger((row[3].toString())));
                obj.setLocation(((getLocationName(row[3].toString(), session))));
            }
            obj.setCityId(new BigInteger((row[4].toString())));
            obj.setFacultyId(new BigInteger((row[5].toString())));
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
            obj.setEnglishEligibility(getEnglishEligibility(session, obj.getCourseId()));
            courses.add(obj);
        }
        return courses;
    }

    private String getLocationName(String id, Session session) {
        String name = null;
        if (id != null) {
            Country obj = session.get(Country.class, new BigInteger(id));
            name = obj.getName();
        }
        return name;
    }

    private String getInstituteName(String id, Session session) {
        String name = null;
        if (id != null) {
            Institute obj = session.get(Institute.class, new BigInteger(id));
            name = obj.getName();
        }
        return name;
    }

    private CourseEnglishEligibility getEnglishEligibility(Session session, BigInteger courseId) {
        CourseEnglishEligibility eligibility = null;
        Criteria crit = session.createCriteria(CourseEnglishEligibility.class);
        crit.add(Restrictions.eq("course.id", courseId));
        if (!crit.list().isEmpty()) {
            eligibility = (CourseEnglishEligibility) crit.list().get(0);
        }
        return eligibility;
    }

    @Override
    public List<CourseRequest> searchCoursesBasedOnFilter(String sqlQuery) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<CourseRequest> courses = new ArrayList<CourseRequest>();
        CourseRequest obj = null;
        for (Object[] row : rows) {
            obj = new CourseRequest();
            obj.setCourseId(new BigInteger((row[0].toString())));
            obj.setcId(Integer.valueOf(row[1].toString()));
            obj.setInstituteId(new BigInteger((row[2].toString())));
            obj.setCountryId(new BigInteger((row[3].toString())));
            obj.setCityId(new BigInteger((row[4].toString())));
            obj.setFacultyId(new BigInteger((row[5].toString())));
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
            obj.setEnglishEligibility(getEnglishEligibility(session, obj.getCourseId()));
            courses.add(obj);
        }
        return courses;
    }

    @Override
    public List<CourseRequest> getUserCourse(BigInteger userId, Integer pageNumber, Integer pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select c.id ,c.c_id, c.institute_id, c.country_id , c.city_id, c.faculty_id, c.name , "
                        + "cd.description, cd.intake,c.duration, c.course_lang,cd.domestic_fee,cd.international_fee,"
                        + "cd.grade, cd.file_url, cd.contact, cd.opening_hours, cd.campus_location, cd.website,"
                        + " cd.job_part_time, cd.job_full_time, cd.course_link  FROM  user_my_course umc inner join course c on umc.course_id = c.id inner join course_details cd "
                        + " on c.id = cd.course_id where c.is_active = 1 and c.deleted_on IS NULL and umc.user_id = " + userId + "  ORDER BY c.created_on DESC ";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<CourseRequest> courses = new ArrayList<CourseRequest>();
        CourseRequest obj = null;
        for (Object[] row : rows) {
            obj = new CourseRequest();
            obj.setCourseId(new BigInteger((row[0].toString())));
            obj.setcId(Integer.valueOf(row[1].toString()));
            obj.setInstituteId(new BigInteger((row[2].toString())));
            obj.setCountryId(new BigInteger((row[3].toString())));
            obj.setCityId(new BigInteger((row[4].toString())));
            obj.setFacultyId(new BigInteger((row[5].toString())));
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
            obj.setEnglishEligibility(getEnglishEligibility(session, new BigInteger((row[0].toString()))));
            courses.add(obj);
        }
        return courses;
    }
}