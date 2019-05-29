package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seeka.app.dto.CategoryDto;
import com.seeka.app.dto.SubCategoryDto;

@Entity
@Table(name="seeka_articles")
public class Article implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private UUID userId;
	private String addType;
	private String heading;
	private String content;
	private String url;
	private String imagePath;
	private Integer type;
	private Boolean active;
	@JsonIgnore
	private Date deleted;
	@JsonIgnore
	private Date createdDate;
	private Integer shared;
	private Integer reviewed;
	private Integer likes;
	private Integer totalCount;
	private String link;
    private Date updatedAt;

    private UUID country;
    private UUID city;
    private UUID faculty;
    private UUID institute;
    private UUID courses;
    private String gender;
    private Category category;
    private UUID subCategory;
    private Date createdAt;
    private SubCategoryDto subCategoryDropDownDto;
    private CategoryDto categoryobj;
    private String compnayName;
    private String companyWebsite;
    private String articleType;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	@Column(name="user_id")
	@Type(type = "uuid-char")
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	@Column(name="add_type")
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	@Column(name="heading")
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="imagePath")
	public String getImagePath() {
		//imagePath = "https://www.seoclerk.com/pics/645213-1jFXX81544832051.jpg";
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Column(name="type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name="active")
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	@Column(name="deleted_on")
	public Date getDeleted() {
		return deleted;
	}
	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}
	@Column(name="created_at")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="shared")
	public Integer getShared() {
		return shared;
	}
	public void setShared(Integer shared) {
		this.shared = shared;
	}
	@Column(name="reviewed")
	public Integer getReviewed() {
		return reviewed;
	}
	public void setReviewed(Integer reviewed) {
		this.reviewed = reviewed;
	}
	@Column(name="likes")
	public Integer getLikes() {
		return likes;
	}
	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	@Transient
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	@Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name = "updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "country")
    @Type(type = "uuid-char")
    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }

    @Column(name = "city")
    @Type(type = "uuid-char")
    public UUID getCity() {
        return city;
    }

    public void setCity(UUID city) {
        this.city = city;
    }

    @Column(name = "faculty")
    @Type(type = "uuid-char")
    public UUID getFaculty() {
        return faculty;
    }

    public void setFaculty(UUID faculty) {
        this.faculty = faculty;
    }

    @Column(name = "courses")
    @Type(type = "uuid-char")
    public UUID getCourses() {
        return courses;
    }

    public void setCourses(UUID courses) {
        this.courses = courses;
    }

    /**
     * @return the institute
     */
    @Column(name = "institute")
    @Type(type = "uuid-char")
    public UUID getInstitute() {
        return institute;
    }

    /**
     * @param institute the institute to set
     */
    public void setInstitute(UUID institute) {
        this.institute = institute;
    }

    /**
     * @return the gender
     */
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "subcategory_id")
    @Type(type = "uuid-char")
    public UUID getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(UUID subCategory) {
        this.subCategory = subCategory;
    }
    /**
     * @return the createdAt
     */
    @Transient
    public Date getCreatedAt() {
        return createdAt;
    }
    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    /**
     * @return the subCategoryDropDownDto
     */
    @Transient
    public SubCategoryDto getSubCategoryDropDownDto() {
        return subCategoryDropDownDto;
    }
    /**
     * @param subCategoryDropDownDto the subCategoryDropDownDto to set
     */
    public void setSubCategoryDropDownDto(SubCategoryDto subCategoryDropDownDto) {
        this.subCategoryDropDownDto = subCategoryDropDownDto;
    }
    /**
     * @return the categoryobj
     */
    @Transient
    public CategoryDto getCategoryobj() {
        return categoryobj;
    }
    /**
     * @param categoryobj the categoryobj to set
     */
    public void setCategoryobj(CategoryDto categoryobj) {
        this.categoryobj = categoryobj;
    }
    /**
     * @return the compnayName
     */
    @Column(name = "company_name")
    public String getCompnayName() {
        return compnayName;
    }
    /**
     * @param compnayName the compnayName to set
     */
    public void setCompnayName(String compnayName) {
        this.compnayName = compnayName;
    }
    /**
     * @return the companyWebsite
     */
    @Column(name = "company_website")
    public String getCompanyWebsite() {
        return companyWebsite;
    }
    /**
     * @param companyWebsite the companyWebsite to set
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }
    /**
     * @return the articleType
     */
    @Column(name = "article_type")
    public String getArticleType() {
        return articleType;
    }
    /**
     * @param articleType the articleType to set
     */
    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }
}
