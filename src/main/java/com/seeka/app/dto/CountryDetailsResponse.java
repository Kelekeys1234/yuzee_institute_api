package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.CountryDetails;

public class CountryDetailsResponse {

    private BigInteger countryId;
    private CountryDetails countryDetails;
    private List<String> images;

    /**
     * @return the countryId
     */
    public BigInteger getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(BigInteger countryId) {
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
}