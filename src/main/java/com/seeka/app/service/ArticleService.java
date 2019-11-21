package com.seeka.app.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
import com.seeka.app.dao.ArticleFolderMapDao;
import com.seeka.app.dao.CityDAO;
import com.seeka.app.dao.CountryDAO;
import com.seeka.app.dao.CourseDAO;
import com.seeka.app.dao.FacultyDAO;
import com.seeka.app.dao.IArticleDAO;
import com.seeka.app.dao.IArticleFolderDao;
import com.seeka.app.dao.IArticleUserDemographicDao;
import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dao.ISubCategoryDAO;
import com.seeka.app.dao.InstituteDAO;
import com.seeka.app.dto.ArticleCityDto;
import com.seeka.app.dto.ArticleCountryDto;
import com.seeka.app.dto.ArticleElasticSearchDto;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.ArticleResponseDetailsDto;
import com.seeka.app.dto.ArticleUserDemographicDto;
import com.seeka.app.dto.ElasticSearchDTO;
import com.seeka.app.dto.SeekaArticleDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ArticleService implements IArticleService {

	@Value("${s3.url}")
	private String s3URL;

	@Autowired
	private IArticleDAO articleDAO;

	@Autowired
	private ICategoryDAO categoryDAO;

	@Autowired
	private ISubCategoryDAO subCategoryDAO;

	@Autowired
	private IArticleFolderDao iArticleFolderDao;

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
	
	@Autowired
	private IStorageService iStorageService;
	
	@Autowired
	private MessageByLocaleService messageByLocalService;
	
	@Autowired
	private ElasticSearchService elasticSearchService;



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
	public ArticleResponseDetailsDto getArticleById(final String articleId) throws ValidationException{
		SeekaArticles article = articleDAO.findById(new BigInteger(articleId));
		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(article.getId(), ImageCategory.ARTICLE.toString(),
				null, "en");
		ArticleResponseDetailsDto articleResponseDetailsDto = getResponseObject(article);
		articleResponseDetailsDto.setStorageList(storageDTOList);
		return articleResponseDetailsDto;
	}

	@Override
	public List<ArticleResponseDetailsDto> getArticleList(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, BigInteger categoryId, String tags, Boolean status, Date date) throws ValidationException {
		
		List<BigInteger> categoryIdList = new ArrayList<>();
		List<String> tagList = new ArrayList<>();
		
		if(categoryId != null) {
			categoryIdList.add(categoryId);
		}
		if(tags != null) {
			tagList.add(tags);
		}
		return getArticleList(startIndex, pageSize, sortByField, sortByType, searchKeyword, categoryIdList, tagList, status, date);
	}
	
	@Override
	public List<ArticleResponseDetailsDto> getArticleList(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<BigInteger> categoryIdList, List<String> tagList, Boolean status, Date filterDate) throws ValidationException {
		
		List<SeekaArticles> articleList = articleDAO.getAll(startIndex, pageSize, sortByField, sortByType, searchKeyword, 
				categoryIdList, tagList, status, null);
		List<ArticleResponseDetailsDto> articleResponseDetailsDtoList = new ArrayList<>();
		for (SeekaArticles article : articleList) {
			ArticleResponseDetailsDto articleResponseDetailsDto = getResponseObject(article);
			articleResponseDetailsDtoList.add(articleResponseDetailsDto);
			/**
			 * Remove this once there is API available to get storage based on all articles in list with a single API.
			 */
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(article.getId(), ImageCategory.ARTICLE.toString(),
					null, "en");
			articleResponseDetailsDto.setStorageList(storageDTOList);
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
	public SeekaArticleDto saveMultiArticle(final SeekaArticleDto articleDto, final BigInteger userId) throws ValidationException, ParseException {
		Map<BigInteger, String> countryMap = new HashMap<>();
		Map<BigInteger, String> cityMap = new HashMap<>();
		SeekaArticles article = new SeekaArticles();
		Boolean updateCase = false;
		if (articleDto != null && articleDto.getId() != null) {
			article = articleDAO.findById(articleDto.getId());
			updateCase = true;
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
		articleElasticSearchDTO.setPostDate(CommonUtil.getDateWithoutTime(articleDto.getPostDate()));
		if(updateCase) {
			elasticSearchService.updateArticleOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_ARTICLE, IConstant.ELASTIC_SEARCH_ARTICLE_TYPE,
					articleElasticSearchDTO, IConstant.ELASTIC_SEARCH);
		} else {
			elasticSearchService.saveArticleOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_ARTICLE, IConstant.ELASTIC_SEARCH_ARTICLE_TYPE,
					articleElasticSearchDTO, IConstant.ELASTIC_SEARCH);
		}
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
	public ArticleFolderDto saveArticleFolder(final ArticleFolderDto articleFolderDto, String language) throws NotFoundException, ValidationException {
			/**
			 * Update case
			 */
			if(articleFolderDto.getFolderName() == null || "".equals(articleFolderDto.getFolderName().trim())) {
				throw new ValidationException("Invalid Folder Name");
			}
		
			if (articleFolderDto.getId() != null) {
				ArticleFolder articleFolder = iArticleFolderDao.findById(articleFolderDto.getId());
				if(articleFolder == null) {
					throw new NotFoundException(messageByLocalService.getMessage("article.not.found", new Object[] {articleFolderDto.getId()}, language));
				}
				articleFolder.setFolderName(articleFolderDto.getFolderName());
				articleFolder.setUpdatedAt(new Date());
				articleFolder.setUserId(articleFolderDto.getUserId());
				iArticleFolderDao.save(articleFolder);
				return articleFolderDto;
			}
			/**
			 * Insert case
			 */
			ArticleFolder articleFolder = new ArticleFolder();
			BeanUtils.copyProperties(articleFolderDto, articleFolder);
			articleFolder.setCreatedAt(DateUtil.getUTCdatetimeAsDate());
			articleFolder.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
			articleFolder.setDeleted(true);
			iArticleFolderDao.save(articleFolder);
			articleFolderDto.setId(articleFolder.getId());
			
		return articleFolderDto;
	}
	
	@Override
	public List<ArticleFolder> getFolderByUserId(final BigInteger userId) throws ValidationException {
		List<ArticleFolder> articleFolders = new ArrayList<>();
			articleFolders = iArticleFolderDao.getAllFolderByUserId(userId);
			if (!articleFolders.isEmpty()) {
					return articleFolders;
				}
			throw new ValidationException(messageByLocalService.getMessage("article.folder.user.not.found", new Object[] {userId}, "en"));
	}

	@Override
	public ArticleFolder getArticleFolderById(final BigInteger articleFolderId) throws ValidationException {
		ArticleFolder articleFolder  = iArticleFolderDao.findById(articleFolderId);
		if (articleFolder == null)
			throw new ValidationException(messageByLocalService.getMessage("article.folder.not.found", new Object[] {articleFolderId}, "en"));
		return articleFolder;
		
	}

	@Override
	public List<ArticleFolder> getAllArticleFolder() {
	return iArticleFolderDao.getAllArticleFolder();
	}

	@Override
	public ArticleFolder deleteArticleFolderById(final BigInteger articleFolderId) throws ValidationException {
			ArticleFolder articleFolder = iArticleFolderDao.findById(articleFolderId);
			if (articleFolder != null) {
				articleFolder.setDeleted(false);
				articleFolder.setUpdatedAt(new Date());
				iArticleFolderDao.save(articleFolder);
				return articleFolder;
			}
            throw new ValidationException(messageByLocalService.getMessage("article.folder.not.found", new Object[] {articleFolderId}, "en"));
	}

	@Override
	public ArticleFolderMapDto mapArticleFolder(final ArticleFolderMapDto articleFolderMapDto, String language) throws ValidationException{
		try {
			ArticleFolderMap articleFolderMap = new ArticleFolderMap();
			articleFolderMap.setFolderId(articleFolderMapDto.getFolderId());
			articleFolderMap.setArticleId(articleFolderMapDto.getArticleId());
			articleFolderMapDao.save(articleFolderMap);
			articleFolderMapDto.setId(articleFolderMap.getId());
			return articleFolderMapDto;// ConstraintViolationException
		}catch (DataIntegrityViolationException e) {
			throw new ValidationException(messageByLocalService.getMessage("article.exists.in.folder", new Object[] {}, language));
		}
	}

	@Override
	public List<ArticleResponseDetailsDto> getArticleByFolderId(Integer startIndex,Integer pageSize,final BigInteger folderId) throws ValidationException {
			List<ArticleFolderMap> articleFolderMaps = articleFolderMapDao.getArticleByFolderId(startIndex, pageSize,folderId);
			List<SeekaArticles> articleList = new ArrayList<>();
           for (ArticleFolderMap articleFolderMap : articleFolderMaps) {      	   
        	   articleList.add(articleDAO.findById(articleFolderMap.getArticleId()));
           }   
       		List<ArticleResponseDetailsDto> articleResponseDetailsDtoList = new ArrayList<>();
       		for (SeekaArticles article : articleList) {
       			ArticleResponseDetailsDto articleResponseDetailsDto = getResponseObject(article);
       			articleResponseDetailsDtoList.add(articleResponseDetailsDto);
       		}
       		return articleResponseDetailsDtoList;
		}
	

	

	@Override
	public Integer getTotalSearchCount(String searchKeyword) {
		return articleDAO.getTotalSearchCount(searchKeyword);
	}

	@Override
	public Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, BigInteger categoryId, String tags, Boolean status, Date filterDate) {
		List<BigInteger> categoryIdList = new ArrayList<>();
		List<String> tagList = new ArrayList<>();
		
		if(categoryId != null) {
			categoryIdList.add(categoryId);
		}
		if(tags != null) {
			tagList.add(tags);
		}
		return articleDAO.getTotalSearchCount(startIndex, pageSize, sortByField, sortByType, searchKeyword, categoryIdList, tagList, status, filterDate);
	}
	
	@Override
	public Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<BigInteger> categoryIdList, List<String> tagList, Boolean status, Date date) {
		return articleDAO.getTotalSearchCount(startIndex, pageSize, sortByField, sortByType, searchKeyword, categoryIdList, tagList, status, date);
	}

}	
	

	

