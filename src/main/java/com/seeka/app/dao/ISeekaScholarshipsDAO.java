package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.SeekaScholarships;

public interface ISeekaScholarshipsDAO {
	public List<SeekaScholarships> getAll();
	public SeekaScholarships get(BigInteger id);
	public void save(SeekaScholarships obj); 
}
