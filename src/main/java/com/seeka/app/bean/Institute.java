package com.seeka.app.bean;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institute", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "country_name", "city_name", "campus_name" }, 
	 name = "UK_NA_CN_CN"), indexes = {@Index(name = "IDX_INSTITUTE_NAME", columnList = "name", unique = false) })
public class Institute {

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "institute_type")
	private String instituteType;

	@Column(name = "name")
	private String name;

	@Column(name = "is_active")
	private Boolean isActive;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on")
	private Date deletedOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "world_ranking")
	private Integer worldRanking;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "website")
	private String website;

	@Column(name = "total_student")
	private Integer totalStudent;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "address")
	private String address;

	@Column(name = "description")
	private String description;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "avg_cost_of_living")
	private String avgCostOfLiving;

	@Column(name = "tution_fees_plan")
	private String tuitionFessPaymentPlan;

	@Column(name = "enrolment_link")
	private String enrolmentLink;

	@Column(name = "about_us_info")
	private String aboutInfo;

	@Column(name = "course_start")
	private String courseStart;

	@Column(name = "whatsapp_no")
	private String whatsNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_category_type_id")
	private InstituteCategoryType instituteCategoryType;

	@Column(name = "campus_name")
	private String campusName;

	@Column(name = "scholarship_financing_assistance")
	private String scholarshipFinancingAssistance;

	@Column(name = "domestic_ranking")
	private Integer domesticRanking;

	@Column(name = "admission_email")
	private String admissionEmail;

	@Column(name = "boarding_available")
	private String boardingAvailable;

	@Column(name = "boarding")
	private String boarding;

	@Column(name = "state")
	private String state;

	@Column(name = "postal_code")
	private Integer postalCode;

	@Column(name = "english_partners")
	private String englishPartners;

	@Column(name = "img_count")
	private String imageCount;

	@Column(name = "climate")
	private String climate;

	@Column(name = "youtube_link")
	private String youtubeLink;

	@Column(name = "international_phone_number")
	private String internationalPhoneNumber;

	@Column(name = "domestic_phone_number")
	private String domesticPhoneNumber;

	@Column(name = "accreditation")
	private String accreditation;
	
	@JsonIgnore
	@OneToMany(mappedBy = "institute" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InstituteEnglishRequirements> instituteEnglishRequirements = new ArrayList<InstituteEnglishRequirements>();
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "institute")
	private InstituteAdditionalInfo instituteAdditionalInfo;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "institute")
	private InstituteTiming instituteTiming;
}