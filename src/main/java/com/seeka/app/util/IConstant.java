package com.seeka.app.util;

import java.util.Arrays;
import java.util.List;

public class IConstant {
	public static final String CURRENCY_URL = "https://data.fixer.io/api/";
	public static final String API_KEY = "30c3311abac5d7016332d637bade7b54";
	public static final String USD_CODE = "USD";

	static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String CORRELATION_ID = "correlationId";
	public static final String USER_ID = "userId";
	public static final String SESSION_ID = "sessionId";
	public static final String TENANT_CODE = "tenantCode";
	public static final String SUCCESS_MESSAGE = "Success.!";
	public static final String CATEGORY_GET_SUCCESS = "Category get successfully";
	public static final String CATEGORY_NOT_FOUND = "Category not found";
	public static final String CATEGORY_DELETE_SUCCESS = "Category deleted successfully";
	public static final int SUCCESS_CODE = 1;
	public static final String SUB_CATEGORY_NOT_FOUND = "Sub category not found";
	public static final String INSTITUDE_NOT_FOUND = "Institude is not found";
	public static final String FACULTY_NOT_FOUND = "Faulty is not found";
	public static final String COURSES_NOT_FOUND = "Courses is not found";
	public static final String SUCCESS = "Success.!";
	public static final String FAIL = "Fail.!";
	public static final String DELETE_FAILURE = "Getting issue when article is deleting";
	public static final String DELETE_FAILURE_ID_NOT_FOUND = "Specified article not found";
	public static final String DELETE_SUCCESS = "SeekaArticles deleted successfully";
	public static final String SUB_CATEGORY_ERROR = "Getting datbase transaction error";
	public static final String BASE_PACKAGE = "com.seeka";
	public static final String INSTITUDE_API = "Api Document";
	public static final String COUNTRY_SUCCESS_MESSAGE = "Country addded successfully";
	public static final String CITY_SUCCESS_MESSAGE = "City addded successfully";
	public static final String SCHOLARSHIP_SUCCESS_MESSAGE = "Scholarship addded successfully";
	public static final String SCHOLARSHIP_DELETE_SUCCESS = "Scholarship deleted successfully";
	public static final String DELETE_FAILURE_ID_NOT_FOUND_SCHOLARSHIP = "Specified Scholarship not found";
	public static final String DELETE_FAILURE_SCHOLARSHIP = "Getting issue when Scholarship is deleting";

	public static final String UPDATE_FAILURE_ID_NOT_FOUND_SCHOLARSHIP = "Specified Scholarship not found";
	public static final String UPDATE_FAILURE_SCHOLARSHIP = "Getting issue when Scholarship is updating";
	public static final String SCHOLARSHIP_UPDATE_SUCCESS = "Scholarship updated successfully";
	public static final String EDUCATION_SUCCESS = "Education system get Successfully";
	public static final String EDUCATION_NOT_FOUND = "Education system not found";
	public static final String SQL_ERROR = "Getting sql error";
	public static final String COUNTY_NOT_FOUND = "Country id can't be null";
	public static final String EDUCATION_SUCCESS_UPDATE = "Education system updated successfully";
	public static final String EDUCATION_SYSTEM_NOT_FOUND = "Education system not found";
	public static final String ARTICLE_NOT_FOUND = "Article not found";
	public static final String ARTICLE_GET_SUCCESS = "Article get successfully";
	public static final String ARTICLE_ADD_SUCCESS = "Article added successfully";
	public static final String UPDATE_ARTICLE_FOLDER_SUCCESS = "Article folder updated successfully";
	public static final String ADD_ARTICLE_FOLDER_SUCCESS = "Article folder added successfully";
	public static final String GET_ARTICLE_FOLDER_SUCCESS = "Get article successfully";
	public static final String GET_ARTICLE_FOLDER_NOT_FOUND = "Article not found";
	public static final String ARTICLE_FOLDER_DELETED = "Article folder deleted successfully";
	public static final String FOLDER_ARTICLE_MAP_SUCCESS = "Folder mapped with article successfully";
	public static final String CITY_GET_SUCCESS = "City get successfully";
	public static final String CITY_GET_NOT_FOUND = "City not found";
	public static final String COUNTRY_GET_SUCCESS = "Country get successfully";
	public static final String COUNTRY_GET_NOT_FOUND = "Country not found";
	public static final String DISCOVER_GET_COUNTRY_SUCCESS = "Discover country get successfully";
	public static final String DISCOVER_COUNTRY_NOT_FOUND = "Discover country not found";
	public static final String COURSE_ADD_SUCCESS = "Course added successfully";
	public static final String HELP_SUCCESS_MESSAGE = "Help added successfully";
	public static final String HELP_ANSWER_SUCCESS_MESSAGE = "Help Answer added successfully";
	public static final String HELP_CATEGORY_SUCCESS_MESSAGE = "Help Category added successfully";
	public static final String HELP_SUBCATEGORY_SUCCESS_MESSAGE = "Help SubCategory added successfully";
	public static final String HELP_UPDATE_MESSAGE = "Help updated successfully";
	public static final String HELP_NOT_FOUND = "Help not found";
	public static final String HELP_CATEGORY_NOT_FOUND = "Help Category not found";
	public static final String HELP_SUBCATEGORY_NOT_FOUND = "Help Sub Category not found";
	public static final String HELP_SUCCESS = "Help get Successfully";
	public static final String HELP_ANSWER_SUCCESS = "Help Answer get Successfully";
	public static final String HELP_ANSWER_NOT_FOUND = "Help Answer not found";
	public static final String HELP_CATEGORY_SUCCESS = "Help Category get Successfully";
	public static final String HELP_SUBCATEGORY_SUCCESS = "Help SubCategory get Successfully";
	public static final String COURSE_GET_SUCCESS_MESSAGE = "Course get successfully";
	public static final String COURSE_GET_NOT_FOUND = "Course not found";
	public static final String COURSE_DELETED_SUCCESS = "Course deleted successfully";
	public static final String FACULTY_SAVE_SUCCESS = "Faculty added successfully";
	public static final String COUNTRY_TYPE = "Country";
	public static final String INSTITUTE_TYPE = "Institution";
	public static final String CITY_TYPE = "City";
	public static final String COURSE_TYPE = "Course";
	public static final String DEFAULT_BASE_CURRENCY = "USD";

	public static final String STORAGE = "STORAGE-SERVICE/storage";
	public static final String IDENTITY = "AUTH-SERVICE/identity";
	public static final String NOTIFICATION = "NOTIFICATION-SERVICE/notification/api/v1";
	public static final String COMMON = "COMMON-API/common/api/v1";
	public static final String REVIEW = "REVIEW-SERVICE/review/api/v1/review/user";

	public static final String STORAGE_CONNECTION_URL = "http://" + STORAGE + "/api/v1";
	public static final String IDENTITY_CONNECTION_URL = "http://" + IDENTITY + "/api/v1";
	public static final String USER_DETAIL_CONNECTION_URL = IDENTITY_CONNECTION_URL + "/users/basic";
	public static final String USER_ACHIVEMENT_CONNECTION_URL = IDENTITY_CONNECTION_URL + "/user/achivement/user";
	public static final String USER_DEVICE_CONNECTION_URL = IDENTITY_CONNECTION_URL + "/user/device/basic";

	public static final String NOTIFICATION_CONNECTION_URL = "http://" + NOTIFICATION + "/push";
	public static final String COMMON_CONNECTION_URL = "http://" + COMMON;
	public static final String REVIEW_CONNECTION_URL = "http://" + REVIEW;
	
	public static final String VIEW_TRANSACTION = "VIEW-TRANSACTION/transaction/api/v1";
	public static final String VIEW_TRANSACTION_URL = "http://" + VIEW_TRANSACTION;

	public static final String ELASTIC_SEARCH = "ELASTIC-SEARCH/elasticSearch";
	public static final String ELASTIC_SEARCH_INDEX_COURSE = "seeka_dev";
	// temp for testing
	// public static final String ELASTIC_SEARCH_INDEX_COURSE_TEST =
	// "seeka_dev_course_temp";

	public static final String ELASTIC_SEARCH_INDEX_ARTICLE = "seeka_dev_article";
	public static final String ELASTIC_SEARCH_INDEX_INSTITUTE = "seeka_dev_institute";
	// temp for testing
	// public static final String ELASTIC_SEARCH_INDEX_INSTITUTE_TEST =
	// "seeka_dev_institute_temp_tet";

	public static final String ELASTIC_SEARCH_INDEX_SCHOLARSHIP = "seeka_dev_scholarship";

	// For testing pupose only
	// public static final String ELASTIC_SEARCH_INDEX_SCHOLARSHIP_TEST =
	// "seeka_dev_scholarship_temp";

	public static final String ELASTIC_SEARCH_URL = ELASTIC_SEARCH + "/";

	public static final String ELASTIC_SEARCH_COURSE_TYPE = "course";
	public static final String ELASTIC_SEARCH_ARTICLE_TYPE = "article";
	public static final String COURSE_DEFAULT_DESCRPTION = "Seeka believes that every person deserves an equal opportunity in education, career aspirations and life. Our database is developed to match users no matter where they live. With over 200,000 scholarships available to choose from, we have carefully paired you with the best.";

	public static final Integer INSITUTE_PER_COUNTRY = 2;

	public static final Integer COUNTRY_PER_PAGE = 10;

	public static final Integer SCHOLARSHIPS_PER_COUNTRY_FOR_RECOMMENDATION = 4;

	public static final Integer TOTAL_INSTITUTES_PER_PAGE = 20;
	/***************
	 * CONSTANTS FOR DISPLAYING MESSAGES
	 ******************************************/
	public static final String INSTITUTE = "Institute";
	public static final String COURSE = "Course";
	public static final String ARTICLE = "Article";
	public static final String SCHOLARSHIP = "Scholarship";
	/***************************************************************************************/

	public static final Integer CURRENCY_THRESHOLD = 2;

	public static final int COURSES_PER_SCHEDULER_LOOP = 30;

	public static final String EVENT_BRITE_API_KEY = "6I6BHL5TKXEYXU3PTFYO";

	public static final List<String> COUNTRY_LIST_FOR_COURSES_GLOBAL_SEARCH_LANDING_PAGE = Arrays.asList("Australia", "Canada", "United Kingdom",
			"United States", "New Zealand");

	public static final List<String> LEVEL_LIST_FOR_COURSES_GLOBAL_SEARCH_LANDING_PAGE = Arrays.asList("Undergraduate", "Postgraduate");

	public static final List<String> LIST_OF_ARTICLE_CATEGORY = Arrays.asList("Campus Life", "Academic Help", "Career and Trends",
			"Personal Experiences and Inspirations", "Work and Internships", "Financial", "Self-Help", "News");

	public static final Integer ARTICLES_PER_CATEGORY_FOR_RECOMMENDATION = 2;
	
	public static final String STATE_GET_SUCCESS = "State get successfully";
	public static final String STATE_NOT_FOUND = "State not found";
	public static final String STATE_SUCCESS_MESSAGE = "State addded successfully";
}
