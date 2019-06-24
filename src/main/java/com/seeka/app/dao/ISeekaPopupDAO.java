package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.SeekaPopup;

public interface ISeekaPopupDAO {
	public List<SeekaPopup> getAll();
	public SeekaPopup get(BigInteger id);
	public void save(SeekaPopup obj); 
}
