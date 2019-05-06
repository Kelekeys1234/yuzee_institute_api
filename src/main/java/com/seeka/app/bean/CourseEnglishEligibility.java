package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.seeka.app.enumeration.EnglishType;

@Entity
@Table(name="course_english_eligibility")
public class CourseEnglishEligibility  extends RecordModifier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Type(type = "uuid-char")
	@Column(name = "course_id")
	private UUID courseId;
	
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
	
	@Column(name="is_active")
	private	Boolean isActive;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getCourseId() {
		return courseId;
	}

	public void setCourseId(UUID courseId) {
		this.courseId = courseId;
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
