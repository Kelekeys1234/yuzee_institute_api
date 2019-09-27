package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.HelpAnswer;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;

public interface IHelpDAO {

    public void save(SeekaHelp seekaHelp);

    public HelpCategory getHelpCategory(BigInteger id);

    public HelpSubCategory getHelpSubCategory(BigInteger id);

    public SeekaHelp get(BigInteger id);

    public void update(SeekaHelp seekaHelp);

    public int findTotalHelpRecord();

    public List<SeekaHelp> getAll(int startIndex, Integer pageSize);

    public void save(HelpCategory helpCategory);

    public void save(HelpSubCategory helpSubCategory);

    public List<HelpSubCategory> getSubCategoryByCategory(BigInteger id);

    public Integer findTotalHelpRecordBySubCategory(BigInteger sub_category_id);

    public List<HelpSubCategory> getAllHelpSubCategories();

    public List<SeekaHelp> getHelpByCategory(BigInteger id);

    public void save(HelpAnswer helpAnswer);

    public List<HelpAnswer> getAnswerByHelpId(BigInteger userId);

    public List<HelpCategory> getAllCategory();
}
