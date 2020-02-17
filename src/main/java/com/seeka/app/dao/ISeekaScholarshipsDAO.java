package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.SeekaScholarships;

public interface ISeekaScholarshipsDAO {
	public List<SeekaScholarships> getAll();
	public SeekaScholarships get(String id);
	public void save(SeekaScholarships obj); 
}
