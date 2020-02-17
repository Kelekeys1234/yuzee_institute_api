package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Hobbies;

public interface IHobbyDAO {
    public void save(Hobbies hobbiesObj);

    public void update(Hobbies hobbiesObj);

    public Hobbies get(String id);

    public List<Hobbies> searchByHobbies(String hobbyTxt);

    public int findTotalCount();

    public List<Hobbies> getAll();

    public List<Hobbies> searchHobbies(String searchText);

    public List<Hobbies> autoSearch(int i, int j, String searchKey);

}
