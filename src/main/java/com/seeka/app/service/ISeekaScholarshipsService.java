package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.SeekaScholarships;

public interface ISeekaScholarshipsService {
	public List<SeekaScholarships> getAll();
	public SeekaScholarships get(String id);
	public void save(SeekaScholarships obj);
}
