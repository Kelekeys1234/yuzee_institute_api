package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.HelpAnswer;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.exception.NotFoundException;

public interface IHelpDAO {

	void save(SeekaHelp seekaHelp);

	HelpCategory getHelpCategory(String id);

	HelpSubCategory getHelpSubCategory(String id);

	SeekaHelp get(String id);

	void update(SeekaHelp seekaHelp);

	int findTotalHelpRecord(String userId, Boolean isArchive);

	List<SeekaHelp> getAll(Integer startIndex, Integer pageSize, String userId, Boolean isArchive);

	void save(HelpCategory helpCategory);

	void save(HelpSubCategory helpSubCategory);

	List<HelpSubCategory> getSubCategoryByCategory(String id, Integer startIndex, Integer pageSize);

	Integer findTotalHelpRecordBySubCategory(String sub_category_id);

	List<HelpSubCategory> getAllHelpSubCategories();

	List<SeekaHelp> getHelpByCategory(String id);

	HelpAnswer save(HelpAnswer helpAnswer);

	List<HelpAnswer> getAnswerByHelpId(String userId);

	List<HelpCategory> getAllCategory(Integer startIndex, Integer pageSize);

	List<SeekaHelp> findByStatus(String status, String categoryId);

	List<SeekaHelp> findByMostRecent(String status, String categoryId);

	List<SeekaHelp> findByStatusAndMostRecent(String status, String mostRecent, String categoryId);

	void updateAnwser(HelpAnswer helpAnswer);

	void setIsFavouriteFlag(String id, boolean isFavourite) throws NotFoundException;

	int getCategoryCount();

	int getSubCategoryCount(String categoryId);

	List<String> getRelatedSearchQuestions(List<String> searchKeywords);

}
