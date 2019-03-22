package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.Gson;

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
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course_id;//CourseId
		
	@Column(name="pricing_name")
	private String pricingName; // Pricing Name
	
	@Column(name="amount")
	private Double amount; // Amount
	
	@Column(name="currency")
	private String currency; // Currency
	
	@Column(name="currency_time")
	private String currencyTime; // Currency Time
	
	@Column(name="cost_savings")
	private Double costSavings; // Cost Savings
	
	@Column(name="cost_range")
	private Double costRange; // Cost Range
	
	@Column(name="remarks")
	private String remarks; // Website
	
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Course getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Course course_id) {
		this.course_id = course_id;
	}

	public String getPricingName() {
		return pricingName;
	}

	public void setPricingName(String pricingName) {
		this.pricingName = pricingName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public static void main(String[] args) {
		   
		CoursePricing obj = new CoursePricing();
		
		Course couObj = new Course();
		couObj.setId(1);
	    
	   
		obj.setCourse_id(couObj);
		obj.setPricingName("International Fees");
		obj.setAmount(31084.00);
		obj.setCurrency("USD");
		obj.setCurrencyTime("Years");
		obj.setCostSavings(200.00);
		obj.setCostRange(5000.00);
		obj.setRemarks("Remarks");
		obj.setIsActive(true);
		obj.setCreatedOn(new Date());
		obj.setUpdatedOn(new Date());
		obj.setDeletedOn(new Date());
		obj.setCreatedBy("Own");
		obj.setUpdatedBy("Own");
		obj.setIsDeleted(false);					
		Gson gson = new Gson();
		
		String value = gson.toJson(obj);
		
		 System.out.println(value);
		 
		System.out.println(new Date().getTime());
	}

    
	
  
}
