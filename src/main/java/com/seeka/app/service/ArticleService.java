package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.seeka.app.dao.CourseDAO;
import com.seeka.app.dao.IArticleDAO;
import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dao.IUserArticleDAO;
import com.seeka.app.dto.ArticleDto;
import com.seeka.app.dto.ArticleDto2;
import com.seeka.app.dto.ArticleDto3;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.ArticleNameDto;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseDto;
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

    @Override
    public List<SeekaArticles> getAll() {
        return articleDAO.getAll();
    }

    @Override
    public List<SeekaArticles> getArticlesByLookup(PageLookupDto pageLookupDto) {
        return articleDAO.getArticlesByLookup(pageLookupDto);
    }

    @Override
    public Map<String, Object> deleteArticle(String articleId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.DELETE_SUCCESS;
        try {
            SeekaArticles article = articleDAO.findById(new BigInteger(articleId));
            if (article != null) {
                article.setActive(false);
                article.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
                articleDAO.deleteArticle(article);
            } else {
                status = IConstant.DELETE_FAILURE_ID_NOT_FOUND;
            }
        } catch (Exception exception) {
            status = IConstant.DELETE_FAILURE;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getArticleById(String articleId) {
        Map<String, Object> response = new HashMap<String, Object>();
        ArticleDto3 articleDto = null;
        String status = IConstant.SUCCESS;
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
            } else {
                status = IConstant.DELETE_FAILURE_ID_NOT_FOUND;
            }
        } catch (Exception exception) {
            status = IConstant.DELETE_FAILURE;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("articleDto", articleDto);
        return response;
    }

    @Override
    public Map<String, Object> fetchAllArticleByPage(BigInteger page, BigInteger size, String query, boolean status, BigInteger categoryId, String tag, String status2) {
        Map<String, Object> response = new HashMap<String, Object>();
        String responseStatus = IConstant.SUCCESS;
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
        }
        response.put("status", 1);
        response.put("message", responseStatus);
        response.put("articles", articles);
        response.put("totalCount", totalCount);
        response.put("pageNumber", paginationUtilDto.getPageNumber());
        response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
        response.put("hasNextPage", paginationUtilDto.isHasNextPage());
        response.put("totalPages", paginationUtilDto.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> saveArticle(MultipartFile file, ArticleDto articledto) {
        Map<String, Object> response = new HashMap<String, Object>();
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
    public Map<String, Object> searchArticle(SearchDto article) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        List<SeekaArticles> articles = null;
        int totalCount = 0;
        try {
            totalCount = articleDAO.findTotalCount();
            articles = articleDAO.searchArticle(article);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("status", 1);
        response.put("message", ResponseStatus);
        response.put("articles", articles);
        response.put("totalCount", totalCount);
        return response;
    }

    @Override
    public Map<String, Object> saveMultiArticle(ArticleDto2 articledto) {
        Map<String, Object> response = new HashMap<String, Object>();
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
            // article.setImagePath(fileDownloadUri);
            article.setLink(articledto.getLink());
            // article.setCountry(articledto.getCountry());
            // article.setCity(articledto.getCity());
            // article.setInstitute(articledto.getInstitute());
            // article.setFaculty(articledto.getFaculty());
            // article.setCourses(articledto.getCourses());
            // article.setGender(articledto.getGender());

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
            if (articledto.getCountry() != null && !articledto.getCountry().isEmpty()) {
                List<ArticleCountry> list = new ArrayList<>();
                for (BigInteger id : articledto.getCountry()) {
                    ArticleCountry bean = new ArticleCountry();
                    bean.setSeekaArticles(article);
                    Country country = new Country();
                    country.setId(id);
                    bean.setCountry(country);
                    list.add(bean);
                }
                articleCountryDAO.saveArticleCountry(list, article.getId());
            }
            if (articledto.getCity() != null && !articledto.getCity().isEmpty()) {
                List<ArticleCity> list = new ArrayList<>();
                for (BigInteger id : articledto.getCity()) {
                    ArticleCity bean = new ArticleCity();
                    bean.setSeekaArticles(article);
                    City city = new City();
                    city.setId(id);
                    bean.setCity(city);
                    list.add(bean);
                }
                articleCityDAO.saveArticleCity(list, article.getId());
            }
            if (articledto.getCourses() != null && !articledto.getCourses().isEmpty()) {
                List<ArticleCourse> list = new ArrayList<>();
                for (BigInteger id : articledto.getCourses()) {
                    ArticleCourse bean = new ArticleCourse();
                    bean.setSeekaArticles(article);
                    bean.setCourse(courseDAO.get(id));
                    list.add(bean);
                }
                articleCourseDAO.saveArticleCorses(list, article.getId());
            }
            if (articledto.getFaculty() != null && !articledto.getFaculty().isEmpty()) {
                List<ArticleFaculty> list = new ArrayList<>();
                for (BigInteger id : articledto.getFaculty()) {
                    ArticleFaculty bean = new ArticleFaculty();
                    bean.setSeekaArticles(article);

                    Faculty faculty = new Faculty();
                    faculty.setId(id);
                    bean.setFaculty(faculty);
                    list.add(bean);
                }
                articleFacultyDAO.saveArticleFaculty(list, article.getId());
            }
            if (articledto.getInstitute() != null && !articledto.getInstitute().isEmpty()) {
                List<ArticleInstitute> list = new ArrayList<>();
                for (BigInteger id : articledto.getInstitute()) {
                    ArticleInstitute bean = new ArticleInstitute();
                    bean.setSeekaArticles(article);
                    Institute institute = new Institute();
                    institute.setId(id);
                    bean.setInstitute(institute);
                    list.add(bean);
                }
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
                articleGenderDAO.saveArticleGender(list, article.getId());
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
    public Map<String, Object> saveArticleFolder(ArticleFolderDto articleFolderDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
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
            } else {
                ArticleFolder articleFolder = new ArticleFolder();
                articleFolder.setFolderName(articleFolderDto.getFolderName());
                articleFolder.setCreatedAt(new Date());
                articleFolder.setDeleted(true);
                articleFolder.setUpdatedAt(new Date());
                articleFolder.setUserId(articleFolderDto.getUserId());
                articleFolderDao.save(articleFolder);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseStatus = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", ResponseStatus);
        return response;
    }

    @Override
    public Map<String, Object> getArticleFolderById(BigInteger articleFolderId) {
        Map<String, Object> response = new HashMap<String, Object>();
        ArticleFolder result = null;
        try {
            result = articleFolderDao.findById(articleFolderId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("status", 1);
        response.put("result", result);
        return response;
    }

    @Override
    public Map<String, Object> getAllArticleFolder() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<ArticleFolder> articleFolders = new ArrayList<>();
        try {
            articleFolders = articleFolderDao.getAllArticleFolder();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("status", 1);
        response.put("articleFolders", articleFolders);
        return response;
    }

    @Override
    public Map<String, Object> deleteArticleFolderById(BigInteger articleFolderId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        try {
            ArticleFolder result = articleFolderDao.findById(articleFolderId);
            if (result != null) {
                result.setDeleted(false);
                result.setUpdatedAt(new Date());
                articleFolderDao.save(result);
            } else {
                ResponseStatus = IConstant.FAIL;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseStatus = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", ResponseStatus);
        return response;
    }

    @Override
    public Map<String, Object> mapArticleFolder(ArticleFolderMapDto articleFolderMapDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        try {
            ArticleFolderMap articleFolderMap = new ArticleFolderMap();
            articleFolderMap.setFolderId(articleFolderMapDto.getFolderId());
            articleFolderMap.setArticleId(articleFolderMapDto.getArticleId());
            articleFolderMap.setUserId(articleFolderMapDto.getUserId());
            articleFolderMapDao.save(articleFolderMap);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseStatus = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", ResponseStatus);
        return response;
    }

    @Override
    public Map<String, Object> getFolderWithArticle(BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<ArticleFolder> articleFolders = new ArrayList<>();
        try {
            articleFolders = articleFolderDao.getAllArticleFolderByUserId(userId);
            for (ArticleFolder articleFolder : articleFolders) {
                List<ArticleNameDto> articles = articleFolderMapDao.getFolderArticles(articleFolder.getId());
                articleFolder.setArticles(articles);
                articleFolder.setUserId(userId);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("status", 1);
        response.put("articleFolders", articleFolders);
        return response;
    }

    @Override
    public Map<String, Object> searchBasedOnNameAndContent(String searchText) {
        Map<String, Object> response = new HashMap<String, Object>();
        String responseStatus = IConstant.SUCCESS;
        List<SeekaArticles> articles = null;
        try {
            articles = articleDAO.searchBasedOnNameAndContent(searchText);
        } catch (Exception exception) {
            exception.printStackTrace();
            responseStatus = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", responseStatus);
        response.put("articles", articles);
        return response;
    }
}
