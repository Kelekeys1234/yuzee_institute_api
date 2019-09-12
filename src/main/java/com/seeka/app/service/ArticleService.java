package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.ArticleCity;
import com.seeka.app.bean.ArticleCountry;
import com.seeka.app.bean.ArticleCourse;
import com.seeka.app.bean.ArticleFaculty;
import com.seeka.app.bean.ArticleFolder;
import com.seeka.app.bean.ArticleFolderMap;
import com.seeka.app.bean.ArticleGender;
import com.seeka.app.bean.ArticleInstitute;
import com.seeka.app.bean.ArticleUserCitizenship;
import com.seeka.app.bean.Category;
import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.bean.SubCategory;
import com.seeka.app.dao.ArticleCityDAO;
import com.seeka.app.dao.ArticleCountryDAO;
import com.seeka.app.dao.ArticleCourseDAO;
import com.seeka.app.dao.ArticleFacultyDAO;
import com.seeka.app.dao.ArticleFolderDao;
import com.seeka.app.dao.ArticleFolderMapDao;
import com.seeka.app.dao.ArticleGenderDAO;
import com.seeka.app.dao.ArticleInstituteDAO;
import com.seeka.app.dao.CityDAO;
import com.seeka.app.dao.CountryDAO;
import com.seeka.app.dao.CourseDAO;
import com.seeka.app.dao.FacultyDAO;
import com.seeka.app.dao.IArticleDAO;
import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dao.IUserArticleDAO;
import com.seeka.app.dao.InstituteDAO;
import com.seeka.app.dto.ArticleDto;
import com.seeka.app.dto.ArticleDto2;
import com.seeka.app.dto.ArticleDto3;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.ArticleNameDto;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseDto;
import com.seeka.app.dto.ElasticSearchArticleDto;
import com.seeka.app.dto.ElasticSearchDTO;
import com.seeka.app.dto.GenderDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.SearchDto;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class ArticleService implements IArticleService {

//    @Value("${storage.connection.url}")
//    private String storageURL;

//    @Value("${elastic.search.host.port}")
//    private String elasticSearchPort;
//
//    @Value("${elastic.search.host.ip}")
//    private String elasticSearchHost;

	@Value("${s3.url}")
	private String s3URL;

	@Autowired
	private IArticleDAO articleDAO;

	@Autowired
	private ICategoryDAO categoryDAO;

	@Autowired
	private IUserArticleDAO userArticleDAO;

	@Autowired
	private ArticleCountryDAO articleCountryDAO;

	@Autowired
	private ArticleCityDAO articleCityDAO;

	@Autowired
	private ArticleCourseDAO articleCourseDAO;

	@Autowired
	private ArticleFacultyDAO articleFacultyDAO;

	@Autowired
	private ArticleInstituteDAO articleInstituteDAO;

	@Autowired
	private ArticleGenderDAO articleGenderDAO;

	@Autowired
	private ArticleFolderDao articleFolderDao;

	@Autowired
	private ArticleFolderMapDao articleFolderMapDao;

	@Autowired
	private CourseDAO courseDAO;

	@Autowired
	private CountryDAO countryDAO;

	@Autowired
	private CityDAO cityDAO;

	@Autowired
	private InstituteDAO instituteDAO;

	@Autowired
	private FacultyDAO facultyDAO;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IImageService iImageService;

	@Override
	public List<SeekaArticles> getAll() {
		return articleDAO.getAll();
	}

	@Override
	public List<SeekaArticles> getArticlesByLookup(final PageLookupDto pageLookupDto) {
		return articleDAO.getArticlesByLookup(pageLookupDto);
	}

	@Override
	public Map<String, Object> deleteArticle(final String articleId) {
		Map<String, Object> response = new HashMap<>();
		String status = IConstant.DELETE_SUCCESS;
		try {
			SeekaArticles article = articleDAO.findById(new BigInteger(articleId));
			if (article != null) {
				article.setActive(false);
				article.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
				articleDAO.deleteArticle(article);
				response.put("status", HttpStatus.OK.value());

				/**
				 * Code to Update ElasticSearch Instance with the Article Data - Delete Article
				 * Data From ElasticSearch Instance
				 */
				// RestTemplate restTemplate = new RestTemplate();
				//
				// ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
				// elasticSearchDto.setIndex(elasticSearchIndex);
				// elasticSearchDto.setType("article");
				// elasticSearchDto.setEntityId(String.valueOf(article.getId()));
				// elasticSearchDto.setObject(article);
				//
				// restTemplate.delete("http://"+elasticSearchHost+":"+elasticSearchPort+"/elasticSearch",
				// elasticSearchDto);
				//
				/**
				 * ElasticSearch Updation Code Ends
				 */

			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				status = IConstant.DELETE_FAILURE_ID_NOT_FOUND;
			}
		} catch (Exception exception) {
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			status = IConstant.SQL_ERROR;
		}
		response.put("message", status);
		return response;
	}

	@Override
	public Map<String, Object> getArticleById(final String articleId) {
		Map<String, Object> response = new HashMap<>();
		ArticleDto3 articleDto = null;
		String status = IConstant.ARTICLE_GET_SUCCESS;
		try {
			SeekaArticles article = articleDAO.findById(new BigInteger(articleId));
			if (article != null) {
				articleDto = new ArticleDto3();
				articleDto.setId(article.getId());
				if (article.getCategory() != null) {
					articleDto.setCategory(String.valueOf(article.getCategory().getId()));
				}
				if (article.getSubcategory() != null) {
					articleDto.setSubcategory(String.valueOf(article.getSubcategory().getId()));
				}
				articleDto.setHeading(article.getHeading());
				articleDto.setContent(article.getContent());
				articleDto.setLink(article.getLink());
				articleDto.setImageUrl(article.getImagepath());
				articleDto.setCompnayName(article.getCompanyName());
				articleDto.setCompanyWebsite(article.getCompanyWebsite());
				if (article.getArticleType() != null) {
					articleDto.setSeekaArticleType(article.getArticleType().split(",")[0]);
					articleDto.setRecArticleType(article.getArticleType().split(",")[1]);
				}
				List<CountryDto> articleCountry = articleCountryDAO.findByArticleId(article.getId());
				List<City> articleCity = articleCityDAO.findByArticleId(article.getId());
				List<CourseDto> articleCourse = articleCourseDAO.findByArticleId(article.getId());
				List<Faculty> faculty = articleFacultyDAO.findByArticleId(article.getId());
				List<InstituteResponseDto> institute = articleInstituteDAO.findByArticleId(article.getId());
				List<GenderDto> articleGender = articleGenderDAO.findByArticleId(article.getId());
				articleDto.setCountry(articleCountry);
				articleDto.setCity(articleCity);
				articleDto.setInstitute(institute);
				articleDto.setFaculty(faculty);
				articleDto.setCourses(articleCourse);
				articleDto.setGender(articleGender);

				ArticleUserCitizenship userCitizenship = userArticleDAO.findArticleUserCitizenshipDetails(article.getId());
				if (userCitizenship.getCountry() != null) {
					articleDto.setUserCountry(String.valueOf(userCitizenship.getCountry().getId()));
				}
				if (userCitizenship.getCity() != null) {
					articleDto.setUserCity(String.valueOf(userCitizenship.getCity().getId()));
				}
				response.put("status", HttpStatus.OK.value());
			} else {
				status = IConstant.ARTICLE_NOT_FOUND;
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			status = IConstant.SQL_ERROR;
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("message", status);
		response.put("articleDto", articleDto);
		return response;
	}

	@Override
	public SeekaArticles findByArticleId(final BigInteger articleId) {
		return articleDAO.findById(articleId);
	}

	@Override
	public Map<String, Object> fetchAllArticleByPage(final BigInteger page, final BigInteger size, final String query, final boolean status,
			final BigInteger categoryId, final String tag, final String status2) {
		Map<String, Object> response = new HashMap<>();
		List<SeekaArticles> articles = null;
		PaginationUtilDto paginationUtilDto = null;
		int totalCount = 0;
		String sqlQuery = null;
		try {
			if (categoryId == null && status2 == null && tag == null) {
				totalCount = articleDAO.findTotalCount();
				paginationUtilDto = PaginationUtil.calculatePagination(page.intValue(), size.intValue(), totalCount);
				articles = articleDAO.fetchAllArticleByPage(page, size, query, status);
			} else {
				String countQuery = "select sa.id from seeka_articles sa where sa.active = 1 and sa.deleted_on IS NULL ";
				sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.active = "
						+ "1 and sa.deleted_on IS NULL";
				if (categoryId != null) {
					sqlQuery += " and sa.category_id = " + categoryId;
					countQuery += " and sa.category_id = " + categoryId;
				}
				if (tag != null && !tag.isEmpty()) {
					sqlQuery += " and sa.tags  = '" + tag + "'";
					countQuery += " and sa.tags  = '" + tag + "'";
				}
				if (status2 != null && !status2.isEmpty()) {
					sqlQuery += " and sa.status  = '" + status2 + "'";
					countQuery += " and sa.status  = '" + status2 + "'";
				}
				sqlQuery += " ORDER BY sa.created_at DESC";
				sqlQuery = sqlQuery + " LIMIT " + page + " ," + size;
				articles = articleDAO.articleByFilter(sqlQuery);
				totalCount = articleDAO.findTotalCountBasedOnCondition(countQuery);
				paginationUtilDto = PaginationUtil.calculatePagination(page.intValue(), size.intValue(), totalCount);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		if (articles != null && !articles.isEmpty()) {
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.ARTICLE_GET_SUCCESS);
			response.put("data", articles);
			response.put("totalCount", totalCount);
			response.put("pageNumber", paginationUtilDto.getPageNumber());
			response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
			response.put("hasNextPage", paginationUtilDto.isHasNextPage());
			response.put("totalPages", paginationUtilDto.getTotalPages());
		} else {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", IConstant.ARTICLE_NOT_FOUND);
			response.put("data", articles);
		}
		return response;
	}

	@Override
	public Map<String, Object> saveArticle(final MultipartFile file, final ArticleDto articledto) {
		Map<String, Object> response = new HashMap<>();
		String ResponseStatus = IConstant.SUCCESS;
		try {
			// save data
			SeekaArticles article = new SeekaArticles();
			if (articledto != null && articledto.getId() != null) {
				article = articleDAO.findById(articledto.getId());
				if (article != null) {
					article.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
				}
			} else {
				article = new SeekaArticles();
				article.setCreatedAt(DateUtil.getUTCdatetimeAsDate());
			}
			article.setImagepath(articledto.getImageUrl());
			article.setHeading(articledto.getHeading());
			article.setContent(articledto.getContent());
			article.setActive(true);
			if (articledto.getCategory() != null) {
				Category category = categoryDAO.findCategoryById(articledto.getCategory());
				if (category != null) {
					article.setCategory(category);
				}
			}
			SubCategory subcategory = new SubCategory();
			subcategory.setId(articledto.getSubcategory());
			article.setSubcategory(subcategory);
			article.setLink(articledto.getLink());
			article.setCountry(articledto.getCountry());
			article.setCity(articledto.getCity());
			article.setInstitute(articledto.getInstitute());
			article.setFaculty(articledto.getFaculty());
			article.setCourses(articledto.getCourses());
			article.setGender(articledto.getGender());
			article = articleDAO.save(article);
			BigInteger subCAtegory = article.getSubcategory().getId();
			articleDAO.updateArticle(subCAtegory, article.getId());
			if (articledto.getUserCountry() != null && articledto.getUserCity() != null) {
				ArticleUserCitizenship userCitizenship = new ArticleUserCitizenship();
				City city = new City();
				city.setId(articledto.getUserCity());
				userCitizenship.setCity(city);

				Country country = new Country();
				country.setId(articledto.getUserCountry());
				userCitizenship.setCountry(country);
				userCitizenship.setSeekaArticles(article);
				userCitizenship.setCreatedDate(DateUtil.getUTCdatetimeAsDate());
				userCitizenship.setUpdatedDate(DateUtil.getUTCdatetimeAsDate());
				userArticleDAO.saveArticleUserCitizenship(userCitizenship);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			ResponseStatus = IConstant.DELETE_FAILURE;
		}
		response.put("status", 1);
		response.put("message", ResponseStatus);
		return response;
	}

	@Override
	public Map<String, Object> searchArticle(final SearchDto article) {
		Map<String, Object> response = new HashMap<>();
		String ResponseStatus = IConstant.ARTICLE_GET_SUCCESS;
		List<SeekaArticles> articles = null;
		int totalCount = 0;
		try {
			totalCount = articleDAO.findTotalCount();
			articles = articleDAO.searchArticle(article);
			response.put("status", HttpStatus.OK.value());
		} catch (Exception exception) {
			exception.printStackTrace();
			ResponseStatus = IConstant.SQL_ERROR;
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("message", ResponseStatus);
		response.put("data", articles);
		response.put("totalCount", totalCount);
		return response;
	}

	@Override
	public Map<String, Object> saveMultiArticle(final ArticleDto2 articledto, final MultipartFile file) {
		Map<String, Object> response = new HashMap<>();
		String ResponseStatus = IConstant.ARTICLE_ADD_SUCCESS;
		response.put("status", HttpStatus.OK.value());
		try {
			// save data
			SeekaArticles article = new SeekaArticles();
			if (articledto != null && articledto.getId() != null) {
				article = articleDAO.findById(articledto.getId());
				if (article != null) {
					article.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
				}
			} else {
				article = new SeekaArticles();
				article.setCreatedAt(DateUtil.getUTCdatetimeAsDate());
			}
			article.setImagepath(articledto.getImageUrl());
			article.setHeading(articledto.getHeading());
			article.setContent(articledto.getContent());
			article.setActive(true);
			article.setCompanyName(articledto.getCompnayName());
			article.setCompanyWebsite(articledto.getCompanyWebsite());
			article.setArticleType(articledto.getCeekaArticleType() + "," + articledto.getRecArticleType());
			article.setSeekaRecommended(articledto.getSeekaRecommended());
			article.setTags(articledto.getTags());
			article.setWebsiteUrl(articledto.getWebsiteUrl());
			article.setStatus(articledto.getStatus());
			if (articledto.getCategory() != null) {
				Category category = categoryDAO.findCategoryById(articledto.getCategory());
				if (category != null) {
					article.setCategory(category);
				}
			}
			SubCategory subcategory = new SubCategory();
			subcategory.setId(articledto.getSubcategory());
			article.setSubcategory(subcategory);
			article.setLink(articledto.getLink());
			article.setAuthor(articledto.getAuthor());
			article.setEnabled(articledto.getEnabled());
			article.setFeatured(articledto.getFeatured());
			article.setNotes(articledto.getNotes());
			if (articledto.getPostDate() != null) {
				article.setPostDate(DateUtil.stringDateToDateYYYY_MM_DD(articledto.getPostDate()));
			}
			if (articledto.getExpireyDate() != null) {
				article.setExpireyDate(DateUtil.stringDateToDateYYYY_MM_DD(articledto.getPostDate()));
			}
			article = articleDAO.save(article);

			ElasticSearchArticleDto elasticSearchArticleDto = getElasticSearchDto(article);
			BigInteger subCAtegory = article.getSubcategory().getId();
			articleDAO.updateArticle(subCAtegory, article.getId());
			if (articledto.getUserCountry() != null && articledto.getUserCity() != null) {
				ArticleUserCitizenship userCitizenship = new ArticleUserCitizenship();
				City city = new City();
				city.setId(articledto.getUserCity());
				userCitizenship.setCity(city);

				Country country = new Country();
				country.setId(articledto.getUserCountry());
				userCitizenship.setCountry(country);
				userCitizenship.setSeekaArticles(article);
				userCitizenship.setCreatedDate(DateUtil.getUTCdatetimeAsDate());
				userCitizenship.setUpdatedDate(DateUtil.getUTCdatetimeAsDate());
				userArticleDAO.saveArticleUserCitizenship(userCitizenship);

				elasticSearchArticleDto.setUserCity(city);
				elasticSearchArticleDto.setUserCountry(country);
			}
			if (articledto.getCountry() != null && !articledto.getCountry().isEmpty()) {
				List<ArticleCountry> list = new ArrayList<>();
				List<Country> countries = new ArrayList<>();
				for (BigInteger id : articledto.getCountry()) {
					ArticleCountry bean = new ArticleCountry();
					bean.setSeekaArticles(article);
					Country country = new Country();
					country.setId(id);
					bean.setCountry(country);
					list.add(bean);
					countries.add(countryDAO.get(id));
				}
				elasticSearchArticleDto.setCountry(countries);
				articleCountryDAO.saveArticleCountry(list, article.getId());
			}
			if (articledto.getCity() != null && !articledto.getCity().isEmpty()) {
				List<ArticleCity> list = new ArrayList<>();
				List<City> cities = new ArrayList<>();
				for (BigInteger id : articledto.getCity()) {
					ArticleCity bean = new ArticleCity();
					bean.setSeekaArticles(article);
					City city = new City();
					city.setId(id);
					bean.setCity(city);
					list.add(bean);
					cities.add(cityDAO.get(id));
				}
				elasticSearchArticleDto.setCity(cities);
				articleCityDAO.saveArticleCity(list, article.getId());
			}
			if (articledto.getCourses() != null && !articledto.getCourses().isEmpty()) {
				List<ArticleCourse> list = new ArrayList<>();
				List<Course> courses = new ArrayList<>();
				for (BigInteger id : articledto.getCourses()) {
					ArticleCourse bean = new ArticleCourse();
					bean.setSeekaArticles(article);
					bean.setCourse(courseDAO.get(id));
					list.add(bean);
					courses.add(courseDAO.get(id));
				}
				elasticSearchArticleDto.setCourses(courses);
				articleCourseDAO.saveArticleCorses(list, article.getId());
			}
			if (articledto.getFaculty() != null && !articledto.getFaculty().isEmpty()) {
				List<ArticleFaculty> list = new ArrayList<>();
				List<Faculty> faculties = new ArrayList<>();
				for (BigInteger id : articledto.getFaculty()) {
					ArticleFaculty bean = new ArticleFaculty();
					bean.setSeekaArticles(article);

					Faculty faculty = new Faculty();
					faculty.setId(id);
					bean.setFaculty(faculty);
					list.add(bean);
					faculties.add(facultyDAO.get(id));
				}
				elasticSearchArticleDto.setFaculty(faculties);
				articleFacultyDAO.saveArticleFaculty(list, article.getId());
			}
			if (articledto.getInstitute() != null && !articledto.getInstitute().isEmpty()) {
				List<ArticleInstitute> list = new ArrayList<>();
				List<Institute> institutes = new ArrayList<>();
				for (BigInteger id : articledto.getInstitute()) {
					ArticleInstitute bean = new ArticleInstitute();
					bean.setSeekaArticles(article);
					Institute institute = new Institute();
					institute.setId(id);
					bean.setInstitute(institute);
					list.add(bean);
					institutes.add(instituteDAO.get(id));
				}
				elasticSearchArticleDto.setInstitute(institutes);
				articleInstituteDAO.saveArticleInstitute(list, article.getId());
			}
			if (articledto.getGender() != null && !articledto.getGender().isEmpty()) {
				List<ArticleGender> list = new ArrayList<>();
				for (String id : articledto.getGender()) {
					ArticleGender bean = new ArticleGender();
					bean.setSeekaArticles(article);
					bean.setGender(id);
					list.add(bean);
				}
				elasticSearchArticleDto.setGender(articledto.getGender());
				articleGenderDAO.saveArticleGender(list, article.getId());
			}
			response.put("articleId", article.getId());

			/**
			 * Code to Update ElasticSearch Instance with the Article Data -- Add/Update
			 * Article to ElasticSearch Instance
			 */
			saveArticleOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX, IConstant.ELASTIC_SEARCH_ARTICLE_TYPE, elasticSearchArticleDto,
					IConstant.ELASTIC_SEARCH);
			/**
			 * ElasticSearch Updation Code Ends
			 */
		} catch (Exception exception) {
			exception.printStackTrace();
			ResponseStatus = IConstant.SQL_ERROR;
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("message", ResponseStatus);
		return response;
	}

	private ElasticSearchArticleDto getElasticSearchDto(final SeekaArticles articleData) {
		ElasticSearchArticleDto elasticSearchArticleDto = new ElasticSearchArticleDto();
		elasticSearchArticleDto.setCreatedAt(articleData.getCreatedAt());
		elasticSearchArticleDto.setImagepath(articleData.getImagepath());
		elasticSearchArticleDto.setHeading(articleData.getHeading());
		elasticSearchArticleDto.setContent(articleData.getContent());
		elasticSearchArticleDto.setActive(true);
		elasticSearchArticleDto.setCompanyName(articleData.getCompanyName());
		elasticSearchArticleDto.setCompanyWebsite(articleData.getCompanyWebsite());
		elasticSearchArticleDto.setArticleType(articleData.getArticleType());
		elasticSearchArticleDto.setSeekaRecommended(articleData.getSeekaRecommended());
		elasticSearchArticleDto.setTags(articleData.getTags());
		elasticSearchArticleDto.setWebsiteUrl(articleData.getWebsiteUrl());
		elasticSearchArticleDto.setStatus(articleData.getStatus());
		elasticSearchArticleDto.setCategory(articleData.getCategory());
		elasticSearchArticleDto.setSubCategory(articleData.getSubcategory());
		elasticSearchArticleDto.setLink(articleData.getLink());
		elasticSearchArticleDto.setAuthor(articleData.getAuthor());
		elasticSearchArticleDto.setEnabled(articleData.getEnabled());
		elasticSearchArticleDto.setFeatured(articleData.getFeatured());
		elasticSearchArticleDto.setNotes(articleData.getNotes());
		elasticSearchArticleDto.setPostDate(articleData.getPostDate());
		elasticSearchArticleDto.setExpireyDate(articleData.getPostDate());
		return elasticSearchArticleDto;
	}

	@Override
	public Map<String, Object> saveArticleFolder(final ArticleFolderDto articleFolderDto) {
		Map<String, Object> response = new HashMap<>();
		try {
			ArticleFolder result = null;
			if (articleFolderDto.getId() != null) {
				result = articleFolderDao.findById(articleFolderDto.getId());
			}
			if (result != null) {
				result.setFolderName(articleFolderDto.getFolderName());
				result.setUpdatedAt(new Date());
				result.setUserId(articleFolderDto.getUserId());
				articleFolderDao.save(result);
				response.put("id", result.getId());
				response.put("message", IConstant.UPDATE_ARTICLE_FOLDER_SUCCESS);
			} else {
				ArticleFolder articleFolder = new ArticleFolder();
				articleFolder.setFolderName(articleFolderDto.getFolderName());
				articleFolder.setCreatedAt(new Date());
				articleFolder.setDeleted(true);
				articleFolder.setUpdatedAt(new Date());
				articleFolder.setUserId(articleFolderDto.getUserId());
				articleFolderDao.save(articleFolder);
				response.put("message", IConstant.ADD_ARTICLE_FOLDER_SUCCESS);
				response.put("id", articleFolder.getId());
			}
			response.put("status", HttpStatus.OK.value());
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		return response;
	}

	@Override
	public Map<String, Object> getArticleFolderById(final BigInteger articleFolderId) {
		Map<String, Object> response = new HashMap<>();
		ArticleFolder result = null;
		try {
			result = articleFolderDao.findById(articleFolderId);
			if (result != null) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.GET_ARTICLE_FOLDER_SUCCESS);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.GET_ARTICLE_FOLDER_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		response.put("data", result);
		return response;
	}

	@Override
	public Map<String, Object> getAllArticleFolder() {
		Map<String, Object> response = new HashMap<>();
		List<ArticleFolder> articleFolders = new ArrayList<>();
		try {
			articleFolders = articleFolderDao.getAllArticleFolder();
			if (articleFolders != null && !articleFolders.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.GET_ARTICLE_FOLDER_SUCCESS);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.GET_ARTICLE_FOLDER_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		response.put("data", articleFolders);
		return response;
	}

	@Override
	public Map<String, Object> deleteArticleFolderById(final BigInteger articleFolderId) {
		Map<String, Object> response = new HashMap<>();
		String ResponseStatus = IConstant.SUCCESS;
		try {
			ArticleFolder result = articleFolderDao.findById(articleFolderId);
			if (result != null) {
				result.setDeleted(false);
				result.setUpdatedAt(new Date());
				articleFolderDao.save(result);
				ResponseStatus = IConstant.ARTICLE_FOLDER_DELETED;
				response.put("status", HttpStatus.OK.value());
			} else {
				ResponseStatus = IConstant.GET_ARTICLE_FOLDER_NOT_FOUND;
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			ResponseStatus = IConstant.SQL_ERROR;
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("message", ResponseStatus);
		return response;
	}

	@Override
	public Map<String, Object> mapArticleFolder(final ArticleFolderMapDto articleFolderMapDto) {
		Map<String, Object> response = new HashMap<>();
		String ResponseStatus = IConstant.FOLDER_ARTICLE_MAP_SUCCESS;
		try {
			ArticleFolderMap articleFolderMap = new ArticleFolderMap();
			articleFolderMap.setFolderId(articleFolderMapDto.getFolderId());
			articleFolderMap.setArticleId(articleFolderMapDto.getArticleId());
			articleFolderMap.setUserId(articleFolderMapDto.getUserId());
			articleFolderMapDao.save(articleFolderMap);
			response.put("status", HttpStatus.OK.value());
		} catch (Exception exception) {
			exception.printStackTrace();
			ResponseStatus = IConstant.SQL_ERROR;
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("message", ResponseStatus);
		return response;
	}

	@Override
	public Map<String, Object> getFolderWithArticle(final BigInteger userId) {
		Map<String, Object> response = new HashMap<>();
		List<ArticleFolder> articleFolders = new ArrayList<>();
		try {
			articleFolders = articleFolderDao.getAllArticleFolderByUserId(userId);
			if (!articleFolders.isEmpty()) {
				for (ArticleFolder articleFolder : articleFolders) {
					List<ArticleNameDto> articles = articleFolderMapDao.getFolderArticles(articleFolder.getId());
					String imageUrl = articleFolderMapDao.getFolderImageUrl(articleFolder.getId());
					articleFolder.setFolderImage(imageUrl);
					articleFolder.setArticles(articles);
					articleFolder.setUserId(userId);
				}
				response.put("message", IConstant.GET_ARTICLE_FOLDER_SUCCESS);
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.GET_ARTICLE_FOLDER_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		response.put("data", articleFolders);
		return response;
	}

	@Override
	public Map<String, Object> searchBasedOnNameAndContent(final String searchText) {
		Map<String, Object> response = new HashMap<>();
		String responseStatus = IConstant.ARTICLE_GET_SUCCESS;
		List<SeekaArticles> articles = null;
		try {
			articles = articleDAO.searchBasedOnNameAndContent(searchText);
			if (articles != null && !articles.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				responseStatus = IConstant.ARTICLE_NOT_FOUND;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			responseStatus = IConstant.SQL_ERROR;
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("message", responseStatus);
		response.put("data", articles);
		return response;
	}

	@Override
	public Map<String, Object> addArticleImage(final MultipartFile file, final BigInteger articleId) {
		Map<String, Object> response = new HashMap<>();

		SeekaArticles article = articleDAO.findById(articleId);
		if (null == article) {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", IConstant.ARTICLE_NOT_FOUND);
		} else {
			String imageName = iImageService.uploadImage(file, articleId, "ARTICLE", null);
			article.setImagepath(s3URL + imageName);
			articleDAO.save(article);
			response.put("status", HttpStatus.OK.value());
			response.put("message", "file uploading successfully.");
			response.put("data", imageName);
		}

		return response;
	}

	@Override
	public Map<String, Object> unMappedFolder(final BigInteger articleId, final BigInteger folderId) {
		Map<String, Object> response = new HashMap<>();
		try {
			articleFolderDao.unMappedFolder(articleId, folderId);
			response.put("status", HttpStatus.OK.value());
			response.put("message", "Article unmapped successfully");
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", exception.getCause());
		}
		return response;
	}

	@Override
	public Map<String, Object> getArticleByFolderId(final BigInteger folderId) {
		Map<String, Object> response = new HashMap<>();
		List<ArticleDto3> articleDto3s = new ArrayList<>();
		try {
			List<ArticleFolderMap> articleFolderMaps = articleFolderMapDao.getArticleByFolderId(folderId);
			if (!articleFolderMaps.isEmpty()) {
				for (ArticleFolderMap articleFolder : articleFolderMaps) {
					Map<String, Object> articleResponse = getArticleById(String.valueOf(articleFolder.getArticleId()));
					articleDto3s.add((ArticleDto3) articleResponse.get("articleDto"));
				}
				response.put("message", IConstant.GET_ARTICLE_FOLDER_SUCCESS);
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.GET_ARTICLE_FOLDER_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		response.put("data", articleDto3s);
		return response;
	}

	public void saveArticleOnElasticSearch(final String elasticSearchIndex, final String type, final ElasticSearchArticleDto articleDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(articleDto.getId()));
		elasticSearchDto.setObject(articleDto);
		System.out.println(elasticSearchDto);
		ResponseEntity<Object> object = restTemplate.postForEntity("http://" + elasticSearchName + "/elasticSearch/", elasticSearchDto, Object.class);
		System.out.println(object);
	}
}
