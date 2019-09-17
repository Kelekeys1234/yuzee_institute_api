package com.seeka.app.dao;

import java.math.BigInteger;

import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;

public interface IHelpDAO {

    public void save(SeekaHelp seekaHelp);
    public HelpCategory getHelpCategory(BigInteger id);
    public HelpSubCategory getHelpSubCategory(BigInteger id);
    public SeekaHelp get(BigInteger id);
    public void update(SeekaHelp seekaHelp);
}
