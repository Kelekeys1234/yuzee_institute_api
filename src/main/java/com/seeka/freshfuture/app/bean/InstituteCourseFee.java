package com.seeka.freshfuture.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="institute_course_fees")
public class InstituteCourseFee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="institute_course_id")
	private Integer courseId; //Course Details
	
	@Column(name="institute_id")
	private Integer instId; //Institute Details
	
	@Column(name="int_fees")
	private Double intFees; //International Fees
	
	@Column(name="currency")
	private String currency; //Currency
	
	@Column(name="currency_time")
	private String currencyTime; //Currency Time
	
	@Column(name="local_fees")
	private Double localFees; //Local Fees
	
	@Column(name="union_fees")
	private Double unionFees; //Union Fees
	
	@Column(name="cost_savings")
	private Double costSavings; //Cost Savings
	
	@Column(name="cost_range")
	private Double costRange; //Cost Range
	
	@Column(name="remarks")
	private String remarks; //Remarks
	

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

	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public Double getIntFees() {
		return intFees;
	}

	public void setIntFees(Double intFees) {
		this.intFees = intFees;
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

	public Double getLocalFees() {
		return localFees;
	}

	public void setLocalFees(Double localFees) {
		this.localFees = localFees;
	}

	public Double getUnionFees() {
		return unionFees;
	}

	public void setUnionFees(Double unionFees) {
		this.unionFees = unionFees;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
