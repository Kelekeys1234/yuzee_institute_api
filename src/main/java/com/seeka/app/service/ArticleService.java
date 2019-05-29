package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Article;
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
import com.seeka.app.bean.Faculty;
import com.seeka.app.dao.ArticleCityDAO;
import com.seeka.app.dao.ArticleCountryDAO;
import com.seeka.app.dao.ArticleCourseDAO;
import com.seeka.app.dao.ArticleFacultyDAO;
import com.seeka.app.dao.ArticleFolderDao;
import com.seeka.app.dao.ArticleFolderMapDao;
import com.seeka.app.dao.ArticleGenderDAO;
import com.seeka.app.dao.ArticleInstituteDAO;
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
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class ArticleService implements IArticleService {

    @Autowired
    IArticleDAO articleDAO;

    @Autowired
    ICategoryDAO categoryDAO;

    @Autowired
    IUserArticleDAO userArticleDAO;

    @Autowired
    ArticleCountryDAO articleCountryDAO;

    @Autowired
    ArticleCityDAO articleCityDAO;

    @Autowired
    ArticleCourseDAO articleCourseDAO;

    @Autowired
    ArticleFacultyDAO articleFacultyDAO;

    @Autowired
    ArticleInstituteDAO articleInstituteDAO;

    @Autowired
    ArticleGenderDAO articleGenderDAO;

    @Autowired
    ArticleFolderDao articleFolderDao;

    @Autowired
    ArticleFolderMapDao articleFolderMapDao;

    @Override
    public List<Article> getAll() {
        return articleDAO.getAll();
    }

    @Override
    public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto) {
        return articleDAO.getArticlesByLookup(pageLookupDto);
    }

    @Override
    public Map<String, Object> deleteArticle(String articleId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.DELETE_SUCCESS;
        try {
            Article article = articleDAO.findById(UUID.fromString(articleId));
            if (article != null) {
                article.setActive(false);
                article.setDeleted(DateUtil.getUTCdatetimeAsDate());
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
            Article article = articleDAO.findById(UUID.fromString(articleId));
            if (article != null) {
                articleDto = new ArticleDto3();
                articleDto.setId(article.getId());
                if (article.getCategory() != null) {
                    articleDto.setCategory(article.getCategory().getId());
                }
                articleDto.setSubcategory(article.getSubCategory());
                articleDto.setHeading(article.getHeading());
                articleDto.setContent(article.getContent());
                articleDto.setLink(article.getLink());
                articleDto.setImageUrl(article.getImagePath());
                articleDto.setCompnayName(article.getCompnayName());
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
                if (userCitizenship.getCountry() != null && userCitizenship.getCity() != null) {
                    articleDto.setUserCity(userCitizenship.getCity());
                    articleDto.setUserCountry(userCitizenship.getCountry());
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
    public Map<String, Object> fetchAllArticleByPage(Integer page, Integer size, String query, boolean status) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        List<Article> articles = null;
        int totalCount = 0;
        try {
            totalCount = articleDAO.findTotalCount();
            articles = articleDAO.fetchAllArticleByPage(page, size, query, status);
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
    public Map<String, Object> saveArticle(MultipartFile file, ArticleDto articledto) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        try {
            // save data
            Article article = new Article();
            if (articledto != null && articledto.getId() != null) {
                article = articleDAO.findById(articledto.getId());
                if (article != null) {
                    article.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
                }
            } else {
                article = new Article();
                article.setCreatedDate(DateUtil.getUTCdatetimeAsDate());
            }
            article.setImagePath(articledto.getImageUrl());
            article.setHeading(articledto.getHeading());
            article.setContent(articledto.getContent());
            article.setActive(true);
            if (articledto.getCategory() != null) {
                Category category = categoryDAO.findCategoryById(articledto.getCategory());
                if (category != null) {
                    article.setCategory(category);
                }
            }
            article.setSubCategory(articledto.getSubcategory());
            // article.setImagePath(fileDownloadUri);
            article.setLink(articledto.getLink());
            article.setCountry(articledto.getCountry());
            article.setCity(articledto.getCity());
            article.setInstitute(articledto.getInstitute());
            article.setFaculty(articledto.getFaculty());
            article.setCourses(articledto.getCourses());
            article.setGender(articledto.getGender());
            article = articleDAO.save(article);
            UUID subCAtegory = article.getSubCategory();
            articleDAO.updateArticle(subCAtegory, article.getId());
            if (articledto.getUserCountry() != null && articledto.getUserCity() != null) {
                ArticleUserCitizenship userCitizenship = new ArticleUserCitizenship();
                userCitizenship.setCity(articledto.getUserCity());
                userCitizenship.setCountry(articledto.getUserCountry());
                userCitizenship.setArticleId(article.getId());
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
    public Map<String, Object> searchArticle(ArticleDto article) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        List<Article> articles = null;
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
            Article article = new Article();
            if (articledto != null && articledto.getId() != null) {
                article = articleDAO.findById(articledto.getId());
                if (article != null) {
                    article.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
                }
            } else {
                article = new Article();
                article.setCreatedDate(DateUtil.getUTCdatetimeAsDate());
            }
            article.setImagePath(articledto.getImageUrl());
            article.setHeading(articledto.getHeading());
            article.setContent(articledto.getContent());
            article.setActive(true);
            article.setCompnayName(articledto.getCompnayName());
            article.setCompanyWebsite(articledto.getCompanyWebsite());
            article.setArticleType(articledto.getCeekaArticleType() + "," + articledto.getRecArticleType());
            if (articledto.getCategory() != null) {
                Category category = categoryDAO.findCategoryById(articledto.getCategory());
                if (category != null) {
                    article.setCategory(category);
                }
            }
            article.setSubCategory(articledto.getSubcategory());
            // article.setImagePath(fileDownloadUri);
            article.setLink(articledto.getLink());
            // article.setCountry(articledto.getCountry());
            // article.setCity(articledto.getCity());
            // article.setInstitute(articledto.getInstitute());
            // article.setFaculty(articledto.getFaculty());
            // article.setCourses(articledto.getCourses());
            // article.setGender(articledto.getGender());
            article = articleDAO.save(article);
            UUID subCAtegory = article.getSubCategory();
            articleDAO.updateArticle(subCAtegory, article.getId());
            if (articledto.getUserCountry() != null && articledto.getUserCity() != null) {
                ArticleUserCitizenship userCitizenship = new ArticleUserCitizenship();
                userCitizenship.setCity(articledto.getUserCity());
                userCitizenship.setCountry(articledto.getUserCountry());
                userCitizenship.setArticleId(article.getId());
                userCitizenship.setCreatedDate(DateUtil.getUTCdatetimeAsDate());
                userCitizenship.setUpdatedDate(DateUtil.getUTCdatetimeAsDate());
                userArticleDAO.saveArticleUserCitizenship(userCitizenship);
            }
            if (articledto.getCountry() != null && !articledto.getCountry().isEmpty()) {
                List<ArticleCountry> list = new ArrayList<>();
                for (UUID id : articledto.getCountry()) {
                    ArticleCountry bean = new ArticleCountry();
                    bean.setArticleId(article.getId());
                    bean.setCountry(id);
                    list.add(bean);
                }
                articleCountryDAO.saveArticleCountry(list, article.getId());
            }
            if (articledto.getCity() != null && !articledto.getCity().isEmpty()) {
                List<ArticleCity> list = new ArrayList<>();
                for (UUID id : articledto.getCity()) {
                    ArticleCity bean = new ArticleCity();
                    bean.setArticleId(article.getId());
                    bean.setCity(id);
                    list.add(bean);
                }
                articleCityDAO.saveArticleCity(list, article.getId());
            }
            if (articledto.getCourses() != null && !articledto.getCourses().isEmpty()) {
                List<ArticleCourse> list = new ArrayList<>();
                for (UUID id : articledto.getCourses()) {
                    ArticleCourse bean = new ArticleCourse();
                    bean.setArticleId(article.getId());
                    bean.setCourseId(id);
                    list.add(bean);
                }
                articleCourseDAO.saveArticleCorses(list, article.getId());
            }
            if (articledto.getFaculty() != null && !articledto.getFaculty().isEmpty()) {
                List<ArticleFaculty> list = new ArrayList<>();
                for (UUID id : articledto.getFaculty()) {
                    ArticleFaculty bean = new ArticleFaculty();
                    bean.setArticleId(article.getId());
                    bean.setFacultyId(id);
                    list.add(bean);
                }
                articleFacultyDAO.saveArticleFaculty(list, article.getId());
            }
            if (articledto.getInstitute() != null && !articledto.getInstitute().isEmpty()) {
                List<ArticleInstitute> list = new ArrayList<>();
                for (UUID id : articledto.getInstitute()) {
                    ArticleInstitute bean = new ArticleInstitute();
                    bean.setArticleId(article.getId());
                    bean.setInstituteId(id);
                    list.add(bean);
                }
                articleInstituteDAO.saveArticleInstitute(list, article.getId());
            }
            if (articledto.getGender() != null && !articledto.getGender().isEmpty()) {
                List<ArticleGender> list = new ArrayList<>();
                for (String id : articledto.getGender()) {
                    ArticleGender bean = new ArticleGender();
                    bean.setArticleId(article.getId());
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
                articleFolderDao.save(result);
            } else {
                ArticleFolder articleFolder = new ArticleFolder();
                articleFolder.setFolderName(articleFolderDto.getFolderName());
                articleFolder.setCreatedAt(new Date());
                articleFolder.setDeleted(true);
                articleFolder.setUpdatedAt(new Date());
                articleFolder.setUserId(UUID.fromString("62E9623B-3C96-C649-B599-111201E7FB91"));
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
    public Map<String, Object> getArticleFolderById(UUID articleFolderId) {
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
    public Map<String, Object> deleteArticleFolderById(UUID articleFolderId) {
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
    public Map<String, Object> getFolderWithArticle(UUID userId) {
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
}
