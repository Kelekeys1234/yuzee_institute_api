package com.seeka.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="course_pricing")
public class CoursePricing extends RecordModifier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="course_id")
	private Integer courseId; //CourseId
	 
	@Column(name="local_fees")
	private Double localFees; // Amount
	
	@Column(name="intl_fees")
	private Double internationFees; // Amount
	
	@Column(name="union_fees")
	private Double unionFees; // Amount
	
	@Column(name="currency")
	private String currency; // Currency
	
	@Column(name="currency_time")
	private String currencyTime; // Currency Time
	
	@Column(name="cost_savings")
	private Double costSavings; // Cost Savings
	
	@Column(name="cost_range")
	private Double costRange; // Cost Range 
	
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Double getLocalFees() {
		return localFees;
	}

	public void setLocalFees(Double localFees) {
		this.localFees = localFees;
	}

	public Double getInternationFees() {
		return internationFees;
	}

	public void setInternationFees(Double internationFees) {
		this.internationFees = internationFees;
	}

	public Double getUnionFees() {
		return unionFees;
	}

	public void setUnionFees(Double unionFees) {
		this.unionFees = unionFees;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyTime() {
		return currencyTime;
	}

	public void setCurrencyTime(String currencyTime) {
		this.currencyTime = currencyTime;
	}

	public Double getCostSavings() {
		return costSavings;
	}

	public void setCostSavings(Double costSavings) {
		this.costSavings = costSavings;
	}

	public Double getCostRange() {
		return costRange;
	}

	public void setCostRange(Double costRange) {
		this.costRange = costRange;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
  
}
