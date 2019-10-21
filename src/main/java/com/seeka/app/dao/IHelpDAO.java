package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.HelpAnswer;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.exception.NotFoundException;

public interface IHelpDAO {

	void save(SeekaHelp seekaHelp);

	HelpCategory getHelpCategory(BigInteger id);

	HelpSubCategory getHelpSubCategory(BigInteger id);

	SeekaHelp get(BigInteger id);

	void update(SeekaHelp seekaHelp);

	int findTotalHelpRecord(BigInteger userId);

	List<SeekaHelp> getAll(Integer startIndex, Integer pageSize, BigInteger userId);

	void save(HelpCategory helpCategory);

	void save(HelpSubCategory helpSubCategory);

	List<HelpSubCategory> getSubCategoryByCategory(BigInteger id, Integer startIndex, Integer pageSize);

	Integer findTotalHelpRecordBySubCategory(BigInteger sub_category_id);

	List<HelpSubCategory> getAllHelpSubCategories();

	List<SeekaHelp> getHelpByCategory(BigInteger id);

	HelpAnswer save(HelpAnswer helpAnswer);

	List<HelpAnswer> getAnswerByHelpId(BigInteger userId);

	List<HelpCategory> getAllCategory(Integer startIndex, Integer pageSize);

	List<SeekaHelp> findByStatus(String status, BigInteger categoryId);

	List<SeekaHelp> findByMostRecent(String status, BigInteger categoryId);

	List<SeekaHelp> findByStatusAndMostRecent(String status, String mostRecent, BigInteger categoryId);

	void updateAnwser(HelpAnswer helpAnswer);

	void setIsFavouriteFlag(BigInteger id, boolean isFavourite) throws NotFoundException;

	int getCategoryCount();

	int getSubCategoryCount(BigInteger categoryId);

}
