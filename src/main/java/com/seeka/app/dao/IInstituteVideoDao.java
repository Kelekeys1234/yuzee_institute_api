package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteVideos;
import com.seeka.app.dto.InstituteMedia;

public interface IInstituteVideoDao {

    public void save(InstituteVideos instituteVideo);

    public List<InstituteMedia> findByInstituteId(BigInteger id);

}
