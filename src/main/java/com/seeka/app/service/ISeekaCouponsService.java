package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.SeekaCoupons;

public interface ISeekaCouponsService {
	public List<SeekaCoupons> getAll();
	public SeekaCoupons get(BigInteger id);
	public void save(SeekaCoupons obj);
}
