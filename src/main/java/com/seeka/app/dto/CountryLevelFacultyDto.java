package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Level;

public class CountryLevelFacultyDto {

    private String id;
    private String name;
    private String countryCode;
    private List<Level> levels;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }
    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @return the levels
     */
    public List<Level> getLevels() {
        return levels;
    }
    /**
     * @param levels the levels to set
     */
    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

}
