package com.seeka.app.dto;

import java.math.BigInteger;

public class CountryImageDto {

    private BigInteger id;

    private BigInteger countryId; // Country Id

    private BigInteger imageIndex; // Image Index

    private String imageName; // Image Name

    private String imagePath; // Image Path

    private String description; // Description

    /**
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

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
     * @return the imageIndex
     */
    public BigInteger getImageIndex() {
        return imageIndex;
    }

    /**
     * @param imageIndex
     *            the imageIndex to set
     */
    public void setImageIndex(BigInteger imageIndex) {
        this.imageIndex = imageIndex;
    }

    /**
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName
     *            the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath
     *            the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
}
