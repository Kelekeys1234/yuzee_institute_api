package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Help;
import com.yuzee.app.bean.HelpAnswer;
import com.yuzee.app.bean.HelpCategory;
import com.yuzee.app.bean.HelpSubCategory;
import com.yuzee.app.exception.NotFoundException;

public interface IHelpDAO {

	void save(Help seekaHelp);

	HelpCategory getHelpCategory(String id);

	HelpSubCategory getHelpSubCategory(String id);

	Help get(String id);

	void update(Help seekaHelp);

	int findTotalHelpRecord(String userId, Boolean isArchive);

	List<Help> getAll(Integer startIndex, Integer pageSize, String userId, Boolean isArchive);

	void save(HelpCategory helpCategory);

	void save(HelpSubCategory helpSubCategory);

	List<HelpSubCategory> getSubCategoryByCategory(String id, Integer startIndex, Integer pageSize);

	Integer findTotalHelpRecordBySubCategory(String sub_category_id);

	List<HelpSubCategory> getAllHelpSubCategories();

	List<Help> getHelpByCategory(String id);

	HelpAnswer save(HelpAnswer helpAnswer);

	List<HelpAnswer> getAnswerByHelpId(String userId);

	List<HelpCategory> getAllCategory(Integer startIndex, Integer pageSize);

	List<Help> findByStatus(String status, String categoryId);

	List<Help> findByMostRecent(String status, String categoryId);

	List<Help> findByStatusAndMostRecent(String status, String mostRecent, String categoryId);

	void updateAnwser(HelpAnswer helpAnswer);

	void setIsFavouriteFlag(String id, boolean isFavourite) throws NotFoundException;

	int getCategoryCount();

	int getSubCategoryCount(String categoryId);

	List<String> getRelatedSearchQuestions(List<String> searchKeywords);

}
