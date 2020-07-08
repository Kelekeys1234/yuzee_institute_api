package com.seeka.app.dto;

import java.io.Serializable;
import java.util.List;

import com.seeka.app.enumeration.EntityType;

public class GlobalFilterSearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8396029582988011591L;

	private List<String> ids;
	private EntityType seekaEntityType;
	private List<String> faculties;
	private List<String> levelIds;
	private List<String> serviceIds;
	private List<String> countryIds;
	private Double minCost;
	private Double maxCost;
	private Integer minDuration;
	private Integer maxDuration;
	private boolean sortAsscending;
	private String sortBy;
	private Integer maxSizePerPage;
	private Integer pageNumber;
	private String currencyCode;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public EntityType getSeekaEntityType() {
		return seekaEntityType;
	}

	public void setSeekaEntityType(EntityType seekaEntityType) {
		this.seekaEntityType = seekaEntityType;
	}

	public List<String> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<String> faculties) {
		this.faculties = faculties;
	}

	public List<String> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(List<String> levelIds) {
		this.levelIds = levelIds;
	}

	public List<String> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<String> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public List<String> getCountryIds() {
		return countryIds;
	}

	public void setCountryIds(List<String> countryIds) {
		this.countryIds = countryIds;
	}

	public Double getMinCost() {
		return minCost;
	}

	public void setMinCost(Double minCost) {
		this.minCost = minCost;
	}

	public Double getMaxCost() {
		return maxCost;
	}

	public void setMaxCost(Double maxCost) {
		this.maxCost = maxCost;
	}

	public Integer getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(Integer minDuration) {
		this.minDuration = minDuration;
	}

	public Integer getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(Integer maxDuration) {
		this.maxDuration = maxDuration;
	}

	public boolean isSortAsscending() {
		return sortAsscending;
	}

	public void setSortAsscending(boolean sortAsscending) {
		this.sortAsscending = sortAsscending;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Integer getMaxSizePerPage() {
		return maxSizePerPage;
	}

	public void setMaxSizePerPage(Integer maxSizePerPage) {
		this.maxSizePerPage = maxSizePerPage;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryIds == null) ? 0 : countryIds.hashCode());
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((faculties == null) ? 0 : faculties.hashCode());
		result = prime * result + ((ids == null) ? 0 : ids.hashCode());
		result = prime * result + ((levelIds == null) ? 0 : levelIds.hashCode());
		result = prime * result + ((maxCost == null) ? 0 : maxCost.hashCode());
		result = prime * result + ((maxDuration == null) ? 0 : maxDuration.hashCode());
		result = prime * result + ((maxSizePerPage == null) ? 0 : maxSizePerPage.hashCode());
		result = prime * result + ((minCost == null) ? 0 : minCost.hashCode());
		result = prime * result + ((minDuration == null) ? 0 : minDuration.hashCode());
		result = prime * result + ((pageNumber == null) ? 0 : pageNumber.hashCode());
		result = prime * result + ((seekaEntityType == null) ? 0 : seekaEntityType.hashCode());
		result = prime * result + ((serviceIds == null) ? 0 : serviceIds.hashCode());
		result = prime * result + (sortAsscending ? 1231 : 1237);
		result = prime * result + ((sortBy == null) ? 0 : sortBy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlobalFilterSearchDto other = (GlobalFilterSearchDto) obj;
		if (countryIds == null) {
			if (other.countryIds != null)
				return false;
		} else if (!countryIds.equals(other.countryIds))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (faculties == null) {
			if (other.faculties != null)
				return false;
		} else if (!faculties.equals(other.faculties))
			return false;
		if (ids == null) {
			if (other.ids != null)
				return false;
		} else if (!ids.equals(other.ids))
			return false;
		if (levelIds == null) {
			if (other.levelIds != null)
				return false;
		} else if (!levelIds.equals(other.levelIds))
			return false;
		if (maxCost == null) {
			if (other.maxCost != null)
				return false;
		} else if (!maxCost.equals(other.maxCost))
			return false;
		if (maxDuration == null) {
			if (other.maxDuration != null)
				return false;
		} else if (!maxDuration.equals(other.maxDuration))
			return false;
		if (maxSizePerPage == null) {
			if (other.maxSizePerPage != null)
				return false;
		} else if (!maxSizePerPage.equals(other.maxSizePerPage))
			return false;
		if (minCost == null) {
			if (other.minCost != null)
				return false;
		} else if (!minCost.equals(other.minCost))
			return false;
		if (minDuration == null) {
			if (other.minDuration != null)
				return false;
		} else if (!minDuration.equals(other.minDuration))
			return false;
		if (pageNumber == null) {
			if (other.pageNumber != null)
				return false;
		} else if (!pageNumber.equals(other.pageNumber))
			return false;
		if (seekaEntityType != other.seekaEntityType)
			return false;
		if (serviceIds == null) {
			if (other.serviceIds != null)
				return false;
		} else if (!serviceIds.equals(other.serviceIds))
			return false;
		if (sortAsscending != other.sortAsscending)
			return false;
		if (sortBy == null) {
			if (other.sortBy != null)
				return false;
		} else if (!sortBy.equals(other.sortBy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GlobalFilterSearchDto [ids=").append(ids).append(", seekaEntityType=").append(seekaEntityType)
				.append(", faculties=").append(faculties).append(", levelIds=").append(levelIds).append(", serviceIds=")
				.append(serviceIds).append(", countryIds=").append(countryIds).append(", minCost=").append(minCost)
				.append(", maxCost=").append(maxCost).append(", minDuration=").append(minDuration)
				.append(", maxDuration=").append(maxDuration).append(", sortAsscending=").append(sortAsscending)
				.append(", sortBy=").append(sortBy).append(", maxSizePerPage=").append(maxSizePerPage)
				.append(", pageNumber=").append(pageNumber).append(", currencyCode=").append(currencyCode).append("]");
		return builder.toString();
	}

}
