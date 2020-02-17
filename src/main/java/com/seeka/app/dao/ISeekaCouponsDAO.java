package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.SeekaCoupons;

public interface ISeekaCouponsDAO {
	public List<SeekaCoupons> getAll();
	public SeekaCoupons get(String id);
	public void save(SeekaCoupons obj); 
}
