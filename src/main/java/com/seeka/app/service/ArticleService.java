package com.seeka.app.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.ArticleFolder;
import com.seeka.app.bean.ArticleFolderMap;
import com.seeka.app.bean.ArticleUserDemographic;
import com.seeka.app.bean.Category;
import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.bean.SubCategory;
import com.seeka.app.dao.ArticleFolderDao;
import com.seeka.app.dao.ArticleFolderMapDao;
import com.seeka.app.dao.CityDAO;
import com.seeka.app.dao.CountryDAO;
import com.seeka.app.dao.CourseDAO;
import com.seeka.app.dao.FacultyDAO;
import com.seeka.app.dao.IArticleDAO;
import com.seeka.app.dao.IArticleUserDemographicDao;
import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dao.ISubCategoryDAO;
import com.seeka.app.dao.InstituteDAO;
import com.seeka.app.dto.ArticleCityDto;
import com.seeka.app.dto.ArticleCountryDto;
import com.seeka.app.dto.ArticleElasticSearchDto;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.ArticleNameDto;
import com.seeka.app.dto.ArticleResponseDetailsDto;
import com.seeka.app.dto.ArticleUserDemographicDto;
import com.seeka.app.dto.ElasticSearchDTO;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.SearchDto;
import com.seeka.app.dto.SeekaArticleDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
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
	private ISubCategoryDAO subCategoryDAO;

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
	private IArticleUserDemographicDao iArticleUserDemographicDao;

	@Override
	public List<SeekaArticles> getAll() {
		return null;
	}

	@Override
	public List<SeekaArticles> getArticlesByLookup(final PageLookupDto pageLookupDto) {
		return articleDAO.getArticlesByLookup(pageLookupDto);
	}

	@Override
	public SeekaArticles deleteArticle(final String articleId) {
		SeekaArticles article = articleDAO.findById(new BigInteger(articleId));
		if (article != null) {
			article.setActive(false);
			article.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
			article = articleDAO.deleteArticle(article);
		}
		return article;
	}

	@Override
	public ArticleResponseDetailsDto getArticleById(final String articleId) {
		SeekaArticles article = articleDAO.findById(new BigInteger(articleId));
		ArticleResponseDetailsDto articleResponseDetailsDto = getResponseObject(article);
		return articleResponseDetailsDto;
	}

	@Override
	public List<ArticleResponseDetailsDto> getArticleList(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword) {
		List<SeekaArticles> articleList = articleDAO.getAll(startIndex, pageSize, sortByField, sortByType, searchKeyword);
		List<ArticleResponseDetailsDto> articleResponseDetailsDtoList = new ArrayList<>();
		for (SeekaArticles article : articleList) {
			ArticleResponseDetailsDto articleResponseDetailsDto = getResponseObject(article);
			articleResponseDetailsDtoList.add(articleResponseDetailsDto);
		}
		return articleResponseDetailsDtoList;
	}

	private ArticleResponseDetailsDto getResponseObject(final SeekaArticles article) {
		ArticleResponseDetailsDto articleResponseDetailsDto = new ArticleResponseDetailsDto();
		if (article != null) {
			BeanUtils.copyProperties(article, articleResponseDetailsDto);
			articleResponseDetailsDto.setId(article.getId());
			BeanUtils.copyProperties(article, articleResponseDetailsDto);
			if (article.getCategory() != null) {
				articleResponseDetailsDto.setCategoryId(article.getCategory().getId());
				articleResponseDetailsDto.setCategoryName(article.getCategory().getName());
			}
			if (article.getSubcategory() != null) {
				articleResponseDetailsDto.setSubcategoryName(article.getSubcategory().getName());
				articleResponseDetailsDto.setSubcategoryId(article.getSubcategory().getId());
			}
			if (article.getCountry() != null) {
				articleResponseDetailsDto.setCountryId(article.getCountry().getId());
				articleResponseDetailsDto.setCountryName(article.getCountry().getName());
			}
			if (article.getCity() != null) {
				articleResponseDetailsDto.setCityId(article.getCity().getId());
				articleResponseDetailsDto.setCityName(article.getCity().getName());
			}
			if (article.getFaculty() != null) {
				articleResponseDetailsDto.setFacultyId(article.getFaculty().getId());
				articleResponseDetailsDto.setFacutyName(article.getFaculty().getName());
			}
			if (article.getInstitute() != null) {
				articleResponseDetailsDto.setInstituteId(article.getInstitute().getId());
				articleResponseDetailsDto.setInstituteName(article.getInstitute().getName());
			}
			if (article.getCourse() != null) {
				articleResponseDetailsDto.setCourseId(article.getCourse().getId());
				articleResponseDetailsDto.setCourseName(article.getCourse().getName());
			}

			List<ArticleUserDemographicDto> userDemographicDtoList = new ArrayList<>();
			Map<BigInteger, ArticleUserDemographic> countyList = new HashMap<>();
			List<ArticleUserDemographic> userDemographicList = iArticleUserDemographicDao.getbyArticleId(article.getId());

			for (ArticleUserDemographic articleUserDemographic : userDemographicList) {
				countyList.put(articleUserDemographic.getCountry().getId(), articleUserDemographic);
			}
			for (Map.Entry<BigInteger, ArticleUserDemographic> obj : countyList.entrySet()) {
				ArticleUserDemographicDto demographicDto = new ArticleUserDemographicDto();

				demographicDto.setArticle(article.getId());
				demographicDto.setGender(obj.getValue().getGender());
				ArticleCountryDto countrydto = new ArticleCountryDto();
				BeanUtils.copyProperties(obj.getValue().getCountry(), countrydto);
				demographicDto.setCitizenship(countrydto);

				List<ArticleCityDto> articleCityDtoList = new ArrayList<>();
				List<ArticleUserDemographic> articleUserDemographicCityList = iArticleUserDemographicDao.getArticleCityListbyCountryId(countrydto.getId(),
						article.getId());
				for (ArticleUserDemographic cityObj : articleUserDemographicCityList) {
					ArticleCityDto cityDto = new ArticleCityDto();
					BeanUtils.copyProperties(cityObj.getCity(), cityDto);
					articleCityDtoList.add(cityDto);
				}
				demographicDto.setCities(articleCityDtoList);
				userDemographicDtoList.add(demographicDto);
			}
			articleResponseDetailsDto.setUserDemographic(userDemographicDtoList);

		}
		return articleResponseDetailsDto;
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
	public SeekaArticleDto saveMultiArticle(final SeekaArticleDto articleDto, final BigInteger userId) throws Throwable, ValidationException {
		Map<BigInteger, String> countryMap = new HashMap<>();
		Map<BigInteger, String> cityMap = new HashMap<>();

		SeekaArticles article = new SeekaArticles();

		if (articleDto != null && articleDto.getId() != null) {
			article = articleDAO.findById(articleDto.getId());
			if (article != null) {
				article = setEntityArticle(articleDto, article);
				article.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
				iArticleUserDemographicDao.deleteByArticleId(articleDto.getId());
			} else {
				throw new ValidationException("No Article found with Id : " + articleDto.getId());
			}
		} else {
			article = setEntityArticle(articleDto, article);
			article.setCreatedAt(DateUtil.getUTCdatetimeAsDate());
		}

		article = articleDAO.save(article);
		if (article.getCountry() != null) {
			countryMap.put(article.getCountry().getId(), article.getCountry().getName());
		}
		if (article.getCity() != null) {
			cityMap.put(article.getCity().getId(), article.getCity().getName());
		}

		StringBuilder countryCSVString = new StringBuilder();
		StringBuilder cityCSVString = new StringBuilder();

		List<ArticleUserDemographicDto> articleUserDemoDtoList = articleDto.getUserDemographic();

		if (articleUserDemoDtoList != null && !articleUserDemoDtoList.isEmpty()) {
			for (ArticleUserDemographicDto articleUserDemographicDto : articleUserDemoDtoList) {
				Country country = new Country();
				BeanUtils.copyProperties(articleUserDemographicDto.getCitizenship(), country);
				List<ArticleCityDto> cityDtoList = articleUserDemographicDto.getCities();
				if (cityDtoList != null) {
					for (ArticleCityDto cityDto : cityDtoList) {
						City city = new City();
						BeanUtils.copyProperties(cityDto, city);
						ArticleUserDemographic articleUserDemographic = new ArticleUserDemographic();
						articleUserDemographic.setArticle(article);
						articleUserDemographic.setCountry(country);
						articleUserDemographic.setCity(city);
						articleUserDemographic.setGender(articleUserDemographicDto.getGender());
						articleUserDemographic.setCreatedAt(DateUtil.getUTCdatetimeAsDate());
						iArticleUserDemographicDao.save(articleUserDemographic);
						articleUserDemographicDto.setId(articleUserDemographic.getId());
					}
				} else {
					ArticleUserDemographic articleUserDemographic = new ArticleUserDemographic();
					articleUserDemographic.setCountry(country);
					articleUserDemographic.setCity(null);
					articleUserDemographic.setGender(articleUserDemographicDto.getGender());
					articleUserDemographic.setCreatedAt(DateUtil.getUTCdatetimeAsDate());
					iArticleUserDemographicDao.save(articleUserDemographic);
				}

//				countryMap.put(articleUserDemographicDto.getCountry(), country.getName());
//				countryCSVString.append(country.getName()+", ");
//				cityMap.put(cityId, city.getName());
//				cityCSVString.append(city.getName()+", ");
			}
		}
		articleDto.setId(article.getId());

		ArticleElasticSearchDto articleElasticSearchDTO = new ArticleElasticSearchDto();
		BeanUtils.copyProperties(articleDto, articleElasticSearchDTO);
		articleElasticSearchDTO.setCategory(article.getCategory() != null ? article.getCategory().getName() : null);
		articleElasticSearchDTO.setSubCategory(article.getSubcategory() != null ? article.getSubcategory().getName() : null);
		articleElasticSearchDTO.setCountry(article.getCountry() != null ? article.getCountry().getName() : null);
		articleElasticSearchDTO.setCity(article.getCity() != null ? article.getCity().getName() : null);
		articleElasticSearchDTO.setFaculty(article.getFaculty() != null ? article.getFaculty().getName() : null);
		articleElasticSearchDTO.setInstitute(article.getInstitute() != null ? article.getInstitute().getName() : null);
		articleElasticSearchDTO.setCourse(article.getCourse() != null ? article.getCourse().getName() : null);
//		saveArticleOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_ARTICLE, IConstant.ELASTIC_SEARCH_ARTICLE_TYPE,
//				articleElasticSearchDTO, IConstant.ELASTIC_SEARCH);
		return articleDto;
	}

	private SeekaArticles setEntityArticle(final SeekaArticleDto articleDto, final SeekaArticles article) throws ParseException {
		BeanUtils.copyProperties(articleDto, article);
		if (articleDto.getCategoryId() != null) {
			Category category = categoryDAO.findCategoryById(articleDto.getCategoryId());
			if (category != null) {
				article.setCategory(category);
			}
		}
		if (articleDto.getSubcategoryId() != null) {
			SubCategory subCategory = subCategoryDAO.findById(articleDto.getSubcategoryId());
			if (subCategory != null) {
				article.setSubcategory(subCategory);
			}
		}
		if (articleDto.getCountryId() != null) {
			Country country = countryDAO.get(articleDto.getCountryId());
			if (country != null) {
				article.setCountry(country);
			}
		}
		if (articleDto.getCityId() != null) {
			City city = cityDAO.get(articleDto.getCityId());
			if (city != null) {
				article.setCity(city);
			}
		}
		if (articleDto.getCourseId() != null) {
			Course course = courseDAO.get(articleDto.getCourseId());
			if (course != null) {
				article.setCourse(course);
			}
		}
		if (articleDto.getFacultyId() != null) {
			Faculty faculty = facultyDAO.get(articleDto.getFacultyId());
			if (faculty != null) {
				article.setFaculty(faculty);
			}
		}
		if (articleDto.getInstituteId() != null) {
			Institute institute = instituteDAO.get(articleDto.getInstituteId());
			if (institute != null) {
				article.setInstitute(institute);
			}
		}

		if (articleDto.getPostDate() != null) {
			article.setPostDate(articleDto.getPostDate());
		}
		if (articleDto.getExpireDate() != null) {
			article.setExpireDate(articleDto.getExpireDate());
		}

		return article;
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
//			String imageName = iImageService.uploadImage(file, articleId, "ARTICLE", null);
//			article.setImagepath(s3URL + imageName);
//			articleDAO.save(article);
//			response.put("status", HttpStatus.OK.value());
//			response.put("message", "file uploading successfully.");
//			response.put("data", imageName);
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
//		List<ArticleResposeDto> articleDto3s = new ArrayList<>();
//		try {
//			List<ArticleFolderMap> articleFolderMaps = articleFolderMapDao.getArticleByFolderId(folderId);
//			if (!articleFolderMaps.isEmpty()) {
//				for (ArticleFolderMap articleFolder : articleFolderMaps) {
//					Map<String, Object> articleResponse = getArticleById(String.valueOf(articleFolder.getArticleId()));
//					articleDto3s.add((ArticleResposeDto) articleResponse.get("articleDto"));
//				}
//				response.put("message", IConstant.GET_ARTICLE_FOLDER_SUCCESS);
//				response.put("status", HttpStatus.OK.value());
//			} else {
//				response.put("status", HttpStatus.NOT_FOUND.value());
//				response.put("message", IConstant.GET_ARTICLE_FOLDER_NOT_FOUND);
//			}
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//			response.put("message", IConstant.SQL_ERROR);
//		}
//		response.put("data", articleDto3s);
		return response;
	}

	public void saveArticleOnElasticSearch(final String elasticSearchIndex, final String type, final ArticleElasticSearchDto articleDto,
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
