package com.seeka.app.bean;import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seeka.app.enumeration.EnglishType;

@Entity
@Table(name="user_edu_iel_tof_score")
public class UserEnglishScore extends RecordModifier implements Serializable {	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private BigInteger id;
	
	@JsonIgnore
	@Column(name = "user_id")
	private BigInteger userId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="english_type")
	private EnglishType englishType;  
	
	@Column(name="reading")
	private	Double reading;  
	
	@Column(name="writing")
	private	Double writing;  
	
	@Column(name="speaking")
	private	Double speaking;  
	
	@Column(name="listening")
	private	Double listening;  
	
	@Column(name="overall")
	private	Double overall;  
	
	@JsonIgnore
	@Column(name="is_active")
	private	Boolean isActive;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public EnglishType getEnglishType() {
		return englishType;
	}

	public void setEnglishType(EnglishType englishType) {
		this.englishType = englishType;
	}

	public Double getReading() {
		return reading;
	}

	public void setReading(Double reading) {
		this.reading = reading;
	}

	public Double getWriting() {
		return writing;
	}

	public void setWriting(Double writing) {
		this.writing = writing;
	}

	public Double getSpeaking() {
		return speaking;
	}

	public void setSpeaking(Double speaking) {
		this.speaking = speaking;
	}

	public Double getListening() {
		return listening;
	}

	public void setListening(Double listening) {
		this.listening = listening;
	}

	public Double getOverall() {
		return overall;
	}

	public void setOverall(Double overall) {
		this.overall = overall;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}  
	
}
