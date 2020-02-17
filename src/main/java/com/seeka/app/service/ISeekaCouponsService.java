package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.SeekaCoupons;

public interface ISeekaCouponsService {
	public List<SeekaCoupons> getAll();
	public SeekaCoupons get(String id);
	public void save(SeekaCoupons obj);
}
