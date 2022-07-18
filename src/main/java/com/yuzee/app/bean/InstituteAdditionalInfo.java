package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "InstituteAdditionalInfo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteAdditionalInfo{

	private Integer numberOfStudent=0;

	private Integer numberOfEmployee=0;

	private Integer numberOfTeacher=0;

	private Integer numberOfClassRoom=0;

	private Integer sizeOfCampus=0;

	private Integer numberOfLectureHall=0;

	private Integer numberOfFaculty=0;

	private Integer rateOfEmployment=0;

}
