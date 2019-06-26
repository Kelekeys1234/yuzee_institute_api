package com.seeka.app.dto;

import java.util.List;

public class UpdateCityDto {

    private CountryCityDto country;
    private String name;
    private String tripAdvisorLink;
    private int cityImgCnt;
    private String description;
    private Integer availableJobs;
    private List<CityImageDto> cityImageDtos;

    /**
     * @return the country
     */
    public CountryCityDto getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(CountryCityDto country) {
        this.country = country;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tripAdvisorLink
     */
    public String getTripAdvisorLink() {
        return tripAdvisorLink;
    }

    /**
     * @param tripAdvisorLink
     *            the tripAdvisorLink to set
     */
    public void setTripAdvisorLink(String tripAdvisorLink) {
        this.tripAdvisorLink = tripAdvisorLink;
    }

    /**
     * @return the cityImgCnt
     */
    public int getCityImgCnt() {
        return cityImgCnt;
    }

    /**
     * @param cityImgCnt
     *            the cityImgCnt to set
     */
    public void setCityImgCnt(int cityImgCnt) {
        this.cityImgCnt = cityImgCnt;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the availableJobs
     */
    public Integer getAvailableJobs() {
        return availableJobs;
    }

    /**
     * @param availableJobs
     *            the availableJobs to set
     */
    public void setAvailableJobs(Integer availableJobs) {
        this.availableJobs = availableJobs;
    }

    /**
     * @return the cityImageDtos
     */
    public List<CityImageDto> getCityImageDtos() {
        return cityImageDtos;
    }

    /**
     * @param cityImageDtos
     *            the cityImageDtos to set
     */
    public void setCityImageDtos(List<CityImageDto> cityImageDtos) {
        this.cityImageDtos = cityImageDtos;
    }

}
