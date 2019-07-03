package com.seeka.app.bean;import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CountryDetails generated by hbm2java
 */
@Entity
@Table(name = "country_details")
public class CountryDetails implements java.io.Serializable {

	private int id;
	private Country country;
	private String capitalCity;
	private String countryFacts;
	private String shortSkilledIndustry;
	private String intHealthCover;
	private String cntSafetyRating;
	private String visa;
	private String jobProspect;
	private String eduRecognition;
	private String workAndStudyRules;
	private String livingNAcomd;
	private String costOfLiving;
	private String climate;
	private String polHelp;
	private String fireHelp;
	private String ambHelp;
	private String eduRecgLink;
	private String studentVisaLink;
	private String area;
	private String population;
	private String people;
	private String language;
	private String religion;
	private String hog;
	private String gdpPc;
	private String anualGrowth;
	private String inflation;
	private String majorIndus;
	private String majorTrdPartnr;
	private String countryVideo;
	private String gdp;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;

	public CountryDetails() {
	}

	public CountryDetails(int id, Country country) {
		this.id = id;
		this.country = country;
	}

	public CountryDetails(int id, Country country, String capitalCity,
			String countryFacts, String shortSkilledIndustry,
			String intHealthCover, String cntSafetyRating, String visa,
			String jobProspect, String eduRecognition,
			String workAndStudyRules, String livingNAcomd, String costOfLiving,
			String climate, String polHelp, String fireHelp, String ambHelp,
			String eduRecgLink, String studentVisaLink, String area,
			String population, String people, String language, String religion,
			String hog, String gdpPc, String anualGrowth, String inflation,
			String majorIndus, String majorTrdPartnr, String countryVideo,
			String gdp, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		this.id = id;
		this.country = country;
		this.capitalCity = capitalCity;
		this.countryFacts = countryFacts;
		this.shortSkilledIndustry = shortSkilledIndustry;
		this.intHealthCover = intHealthCover;
		this.cntSafetyRating = cntSafetyRating;
		this.visa = visa;
		this.jobProspect = jobProspect;
		this.eduRecognition = eduRecognition;
		this.workAndStudyRules = workAndStudyRules;
		this.livingNAcomd = livingNAcomd;
		this.costOfLiving = costOfLiving;
		this.climate = climate;
		this.polHelp = polHelp;
		this.fireHelp = fireHelp;
		this.ambHelp = ambHelp;
		this.eduRecgLink = eduRecgLink;
		this.studentVisaLink = studentVisaLink;
		this.area = area;
		this.population = population;
		this.people = people;
		this.language = language;
		this.religion = religion;
		this.hog = hog;
		this.gdpPc = gdpPc;
		this.anualGrowth = anualGrowth;
		this.inflation = inflation;
		this.majorIndus = majorIndus;
		this.majorTrdPartnr = majorTrdPartnr;
		this.countryVideo = countryVideo;
		this.gdp = gdp;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id", nullable = false)
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Column(name = "capital_city", length = 100)
	public String getCapitalCity() {
		return this.capitalCity;
	}

	public void setCapitalCity(String capitalCity) {
		this.capitalCity = capitalCity;
	}

	@Column(name = "country_facts", length = 100)
	public String getCountryFacts() {
		return this.countryFacts;
	}

	public void setCountryFacts(String countryFacts) {
		this.countryFacts = countryFacts;
	}

	@Column(name = "short_skilled_industry", length = 100)
	public String getShortSkilledIndustry() {
		return this.shortSkilledIndustry;
	}

	public void setShortSkilledIndustry(String shortSkilledIndustry) {
		this.shortSkilledIndustry = shortSkilledIndustry;
	}

	@Column(name = "int_health_cover", length = 100)
	public String getIntHealthCover() {
		return this.intHealthCover;
	}

	public void setIntHealthCover(String intHealthCover) {
		this.intHealthCover = intHealthCover;
	}

	@Column(name = "cnt_safety_rating", length = 100)
	public String getCntSafetyRating() {
		return this.cntSafetyRating;
	}

	public void setCntSafetyRating(String cntSafetyRating) {
		this.cntSafetyRating = cntSafetyRating;
	}

	@Column(name = "visa", length = 50)
	public String getVisa() {
		return this.visa;
	}

	public void setVisa(String visa) {
		this.visa = visa;
	}

	@Column(name = "job_prospect", length = 100)
	public String getJobProspect() {
		return this.jobProspect;
	}

	public void setJobProspect(String jobProspect) {
		this.jobProspect = jobProspect;
	}

	@Column(name = "edu_recognition", length = 100)
	public String getEduRecognition() {
		return this.eduRecognition;
	}

	public void setEduRecognition(String eduRecognition) {
		this.eduRecognition = eduRecognition;
	}

	@Column(name = "work_and_study_rules", length = 100)
	public String getWorkAndStudyRules() {
		return this.workAndStudyRules;
	}

	public void setWorkAndStudyRules(String workAndStudyRules) {
		this.workAndStudyRules = workAndStudyRules;
	}

	@Column(name = "living_n_acomd", length = 100)
	public String getLivingNAcomd() {
		return this.livingNAcomd;
	}

	public void setLivingNAcomd(String livingNAcomd) {
		this.livingNAcomd = livingNAcomd;
	}

	@Column(name = "cost_of_living", length = 100)
	public String getCostOfLiving() {
		return this.costOfLiving;
	}

	public void setCostOfLiving(String costOfLiving) {
		this.costOfLiving = costOfLiving;
	}

	@Column(name = "climate", length = 100)
	public String getClimate() {
		return this.climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	@Column(name = "pol_help", length = 100)
	public String getPolHelp() {
		return this.polHelp;
	}

	public void setPolHelp(String polHelp) {
		this.polHelp = polHelp;
	}

	@Column(name = "fire_help", length = 100)
	public String getFireHelp() {
		return this.fireHelp;
	}

	public void setFireHelp(String fireHelp) {
		this.fireHelp = fireHelp;
	}

	@Column(name = "amb_help", length = 100)
	public String getAmbHelp() {
		return this.ambHelp;
	}

	public void setAmbHelp(String ambHelp) {
		this.ambHelp = ambHelp;
	}

	@Column(name = "edu_recg_link", length = 100)
	public String getEduRecgLink() {
		return this.eduRecgLink;
	}

	public void setEduRecgLink(String eduRecgLink) {
		this.eduRecgLink = eduRecgLink;
	}

	@Column(name = "student_visa_link", length = 100)
	public String getStudentVisaLink() {
		return this.studentVisaLink;
	}

	public void setStudentVisaLink(String studentVisaLink) {
		this.studentVisaLink = studentVisaLink;
	}

	@Column(name = "area", length = 100)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "population", length = 100)
	public String getPopulation() {
		return this.population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	@Column(name = "people", length = 100)
	public String getPeople() {
		return this.people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	@Column(name = "Language", length = 100)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "religion", length = 100)
	public String getReligion() {
		return this.religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	@Column(name = "hog", length = 100)
	public String getHog() {
		return this.hog;
	}

	public void setHog(String hog) {
		this.hog = hog;
	}

	@Column(name = "gdp_pc", length = 100)
	public String getGdpPc() {
		return this.gdpPc;
	}

	public void setGdpPc(String gdpPc) {
		this.gdpPc = gdpPc;
	}

	@Column(name = "anual_growth", length = 100)
	public String getAnualGrowth() {
		return this.anualGrowth;
	}

	public void setAnualGrowth(String anualGrowth) {
		this.anualGrowth = anualGrowth;
	}

	@Column(name = "inflation", length = 100)
	public String getInflation() {
		return this.inflation;
	}

	public void setInflation(String inflation) {
		this.inflation = inflation;
	}

	@Column(name = "major_indus", length = 100)
	public String getMajorIndus() {
		return this.majorIndus;
	}

	public void setMajorIndus(String majorIndus) {
		this.majorIndus = majorIndus;
	}

	@Column(name = "major_trd_partnr", length = 100)
	public String getMajorTrdPartnr() {
		return this.majorTrdPartnr;
	}

	public void setMajorTrdPartnr(String majorTrdPartnr) {
		this.majorTrdPartnr = majorTrdPartnr;
	}

	@Column(name = "country_video", length = 100)
	public String getCountryVideo() {
		return this.countryVideo;
	}

	public void setCountryVideo(String countryVideo) {
		this.countryVideo = countryVideo;
	}

	@Column(name = "gdp", length = 100)
	public String getGdp() {
		return this.gdp;
	}

	public void setGdp(String gdp) {
		this.gdp = gdp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "created_by", length = 50)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_by", length = 50)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ambHelp == null) ? 0 : ambHelp.hashCode());
        result = prime * result + ((anualGrowth == null) ? 0 : anualGrowth.hashCode());
        result = prime * result + ((area == null) ? 0 : area.hashCode());
        result = prime * result + ((capitalCity == null) ? 0 : capitalCity.hashCode());
        result = prime * result + ((climate == null) ? 0 : climate.hashCode());
        result = prime * result + ((cntSafetyRating == null) ? 0 : cntSafetyRating.hashCode());
        result = prime * result + ((costOfLiving == null) ? 0 : costOfLiving.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((countryFacts == null) ? 0 : countryFacts.hashCode());
        result = prime * result + ((countryVideo == null) ? 0 : countryVideo.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
        result = prime * result + ((eduRecgLink == null) ? 0 : eduRecgLink.hashCode());
        result = prime * result + ((eduRecognition == null) ? 0 : eduRecognition.hashCode());
        result = prime * result + ((fireHelp == null) ? 0 : fireHelp.hashCode());
        result = prime * result + ((gdp == null) ? 0 : gdp.hashCode());
        result = prime * result + ((gdpPc == null) ? 0 : gdpPc.hashCode());
        result = prime * result + ((hog == null) ? 0 : hog.hashCode());
        result = prime * result + id;
        result = prime * result + ((inflation == null) ? 0 : inflation.hashCode());
        result = prime * result + ((intHealthCover == null) ? 0 : intHealthCover.hashCode());
        result = prime * result + ((jobProspect == null) ? 0 : jobProspect.hashCode());
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        result = prime * result + ((livingNAcomd == null) ? 0 : livingNAcomd.hashCode());
        result = prime * result + ((majorIndus == null) ? 0 : majorIndus.hashCode());
        result = prime * result + ((majorTrdPartnr == null) ? 0 : majorTrdPartnr.hashCode());
        result = prime * result + ((people == null) ? 0 : people.hashCode());
        result = prime * result + ((polHelp == null) ? 0 : polHelp.hashCode());
        result = prime * result + ((population == null) ? 0 : population.hashCode());
        result = prime * result + ((religion == null) ? 0 : religion.hashCode());
        result = prime * result + ((shortSkilledIndustry == null) ? 0 : shortSkilledIndustry.hashCode());
        result = prime * result + ((studentVisaLink == null) ? 0 : studentVisaLink.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
        result = prime * result + ((visa == null) ? 0 : visa.hashCode());
        result = prime * result + ((workAndStudyRules == null) ? 0 : workAndStudyRules.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CountryDetails other = (CountryDetails) obj;
        if (ambHelp == null) {
            if (other.ambHelp != null)
                return false;
        } else if (!ambHelp.equals(other.ambHelp))
            return false;
        if (anualGrowth == null) {
            if (other.anualGrowth != null)
                return false;
        } else if (!anualGrowth.equals(other.anualGrowth))
            return false;
        if (area == null) {
            if (other.area != null)
                return false;
        } else if (!area.equals(other.area))
            return false;
        if (capitalCity == null) {
            if (other.capitalCity != null)
                return false;
        } else if (!capitalCity.equals(other.capitalCity))
            return false;
        if (climate == null) {
            if (other.climate != null)
                return false;
        } else if (!climate.equals(other.climate))
            return false;
        if (cntSafetyRating == null) {
            if (other.cntSafetyRating != null)
                return false;
        } else if (!cntSafetyRating.equals(other.cntSafetyRating))
            return false;
        if (costOfLiving == null) {
            if (other.costOfLiving != null)
                return false;
        } else if (!costOfLiving.equals(other.costOfLiving))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (countryFacts == null) {
            if (other.countryFacts != null)
                return false;
        } else if (!countryFacts.equals(other.countryFacts))
            return false;
        if (countryVideo == null) {
            if (other.countryVideo != null)
                return false;
        } else if (!countryVideo.equals(other.countryVideo))
            return false;
        if (createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!createdBy.equals(other.createdBy))
            return false;
        if (createdOn == null) {
            if (other.createdOn != null)
                return false;
        } else if (!createdOn.equals(other.createdOn))
            return false;
        if (eduRecgLink == null) {
            if (other.eduRecgLink != null)
                return false;
        } else if (!eduRecgLink.equals(other.eduRecgLink))
            return false;
        if (eduRecognition == null) {
            if (other.eduRecognition != null)
                return false;
        } else if (!eduRecognition.equals(other.eduRecognition))
            return false;
        if (fireHelp == null) {
            if (other.fireHelp != null)
                return false;
        } else if (!fireHelp.equals(other.fireHelp))
            return false;
        if (gdp == null) {
            if (other.gdp != null)
                return false;
        } else if (!gdp.equals(other.gdp))
            return false;
        if (gdpPc == null) {
            if (other.gdpPc != null)
                return false;
        } else if (!gdpPc.equals(other.gdpPc))
            return false;
        if (hog == null) {
            if (other.hog != null)
                return false;
        } else if (!hog.equals(other.hog))
            return false;
        if (id != other.id)
            return false;
        if (inflation == null) {
            if (other.inflation != null)
                return false;
        } else if (!inflation.equals(other.inflation))
            return false;
        if (intHealthCover == null) {
            if (other.intHealthCover != null)
                return false;
        } else if (!intHealthCover.equals(other.intHealthCover))
            return false;
        if (jobProspect == null) {
            if (other.jobProspect != null)
                return false;
        } else if (!jobProspect.equals(other.jobProspect))
            return false;
        if (language == null) {
            if (other.language != null)
                return false;
        } else if (!language.equals(other.language))
            return false;
        if (livingNAcomd == null) {
            if (other.livingNAcomd != null)
                return false;
        } else if (!livingNAcomd.equals(other.livingNAcomd))
            return false;
        if (majorIndus == null) {
            if (other.majorIndus != null)
                return false;
        } else if (!majorIndus.equals(other.majorIndus))
            return false;
        if (majorTrdPartnr == null) {
            if (other.majorTrdPartnr != null)
                return false;
        } else if (!majorTrdPartnr.equals(other.majorTrdPartnr))
            return false;
        if (people == null) {
            if (other.people != null)
                return false;
        } else if (!people.equals(other.people))
            return false;
        if (polHelp == null) {
            if (other.polHelp != null)
                return false;
        } else if (!polHelp.equals(other.polHelp))
            return false;
        if (population == null) {
            if (other.population != null)
                return false;
        } else if (!population.equals(other.population))
            return false;
        if (religion == null) {
            if (other.religion != null)
                return false;
        } else if (!religion.equals(other.religion))
            return false;
        if (shortSkilledIndustry == null) {
            if (other.shortSkilledIndustry != null)
                return false;
        } else if (!shortSkilledIndustry.equals(other.shortSkilledIndustry))
            return false;
        if (studentVisaLink == null) {
            if (other.studentVisaLink != null)
                return false;
        } else if (!studentVisaLink.equals(other.studentVisaLink))
            return false;
        if (updatedBy == null) {
            if (other.updatedBy != null)
                return false;
        } else if (!updatedBy.equals(other.updatedBy))
            return false;
        if (updatedOn == null) {
            if (other.updatedOn != null)
                return false;
        } else if (!updatedOn.equals(other.updatedOn))
            return false;
        if (visa == null) {
            if (other.visa != null)
                return false;
        } else if (!visa.equals(other.visa))
            return false;
        if (workAndStudyRules == null) {
            if (other.workAndStudyRules != null)
                return false;
        } else if (!workAndStudyRules.equals(other.workAndStudyRules))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CountryDetails [id=" + id + ", country=" + country + ", capitalCity=" + capitalCity + ", countryFacts=" + countryFacts + ", shortSkilledIndustry="
                        + shortSkilledIndustry + ", intHealthCover=" + intHealthCover + ", cntSafetyRating=" + cntSafetyRating + ", visa=" + visa + ", jobProspect=" + jobProspect
                        + ", eduRecognition=" + eduRecognition + ", workAndStudyRules=" + workAndStudyRules + ", livingNAcomd=" + livingNAcomd + ", costOfLiving=" + costOfLiving
                        + ", climate=" + climate + ", polHelp=" + polHelp + ", fireHelp=" + fireHelp + ", ambHelp=" + ambHelp + ", eduRecgLink=" + eduRecgLink
                        + ", studentVisaLink=" + studentVisaLink + ", area=" + area + ", population=" + population + ", people=" + people + ", language=" + language + ", religion="
                        + religion + ", hog=" + hog + ", gdpPc=" + gdpPc + ", anualGrowth=" + anualGrowth + ", inflation=" + inflation + ", majorIndus=" + majorIndus
                        + ", majorTrdPartnr=" + majorTrdPartnr + ", countryVideo=" + countryVideo + ", gdp=" + gdp + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
                        + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
    }

}
