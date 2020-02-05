package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.CountryDetails;
import com.seeka.app.bean.YoutubeVideo;

public class CountryDetailsResponse {

    private String countryId;
    private CountryDetails countryDetails;
    private List<String> images;
    private List<YoutubeVideo> youtubeVideos;

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the countryDetails
     */
    public CountryDetails getCountryDetails() {
        return countryDetails;
    }

    /**
     * @param countryDetails
     *            the countryDetails to set
     */
    public void setCountryDetails(CountryDetails countryDetails) {
        this.countryDetails = countryDetails;
    }

    /**
     * @return the images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * @param images
     *            the images to set
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * @return the youtubeVideos
     */
    public List<YoutubeVideo> getYoutubeVideos() {
        return youtubeVideos;
    }

    /**
     * @param youtubeVideos the youtubeVideos to set
     */
    public void setYoutubeVideos(List<YoutubeVideo> youtubeVideos) {
        this.youtubeVideos = youtubeVideos;
    }
}