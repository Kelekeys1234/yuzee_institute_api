package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.SeekaPopup;

public interface ISeekaPopupService {
	public List<SeekaPopup> getAll();
	public SeekaPopup get(String id);
	public void save(SeekaPopup obj);
}
	