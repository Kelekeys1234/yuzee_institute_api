package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class ImageResponseDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3198093215326324489L;

	private BigInteger id;
	private BigInteger categoryId;
	private String imageName;
	private String baseUrl;
	private String category;
	private String subCategory;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(final BigInteger categoryId) {
		this.categoryId = categoryId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(final String imageName) {
		this.imageName = imageName;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(final String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(final String subCategory) {
		this.subCategory = subCategory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (baseUrl == null ? 0 : baseUrl.hashCode());
		result = prime * result + (category == null ? 0 : category.hashCode());
		result = prime * result + (categoryId == null ? 0 : categoryId.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (imageName == null ? 0 : imageName.hashCode());
		result = prime * result + (subCategory == null ? 0 : subCategory.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ImageResponseDto other = (ImageResponseDto) obj;
		if (baseUrl == null) {
			if (other.baseUrl != null) {
				return false;
			}
		} else if (!baseUrl.equals(other.baseUrl)) {
			return false;
		}
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		if (categoryId == null) {
			if (other.categoryId != null) {
				return false;
			}
		} else if (!categoryId.equals(other.categoryId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (imageName == null) {
			if (other.imageName != null) {
				return false;
			}
		} else if (!imageName.equals(other.imageName)) {
			return false;
		}
		if (subCategory == null) {
			if (other.subCategory != null) {
				return false;
			}
		} else if (!subCategory.equals(other.subCategory)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageResponseDto [id=").append(id).append(", categoryId=").append(categoryId).append(", imageName=").append(imageName)
				.append(", baseUrl=").append(baseUrl).append(", category=").append(category).append(", subCategory=").append(subCategory).append("]");
		return builder.toString();
	}

}
