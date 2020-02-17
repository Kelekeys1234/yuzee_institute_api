package com.seeka.app.dto;

import java.util.List;

public class CountryRequestDto {

    private String id;

    private String name;

    private String description;

    private String countryCode;

    private CountryDetailsDto countryDetailsDto;

    private List<CountryImageDto> countryImageDtos;
	
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
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
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode
     *            the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the countryDetailsDto
     */
    public CountryDetailsDto getCountryDetailsDto() {
        return countryDetailsDto;
    }

    /**
     * @param countryDetailsDto
     *            the countryDetailsDto to set
     */
    public void setCountryDetailsDto(CountryDetailsDto countryDetailsDto) {
        this.countryDetailsDto = countryDetailsDto;
    }

    /**
     * @return the countryImageDtos
     */
    public List<CountryImageDto> getCountryImageDtos() {
        return countryImageDtos;
    }

    /**
     * @param countryImageDtos
     *            the countryImageDtos to set
     */
    public void setCountryImageDtos(List<CountryImageDto> countryImageDtos) {
        this.countryImageDtos = countryImageDtos;
    }

}
