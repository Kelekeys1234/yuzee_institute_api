package com.yuzee.app.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import com.yuzee.app.constant.InstituteJoinStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "institute_join_request")
//@CompoundIndexes({@CompoundIndex(name = "IDX_JR_INSTITUTE_NAME_USER_ID", def = "{'instituteName' : 1}, {'userId' : 1}", unique = true),
//                                    @CompoundIndex(name = "UK_IJOIN_REQUEST_INSTITUTE_NAME_USER_ID", def = "{'instituteName' : 1}, {'userId' : 1}", unique = true),
//        							@CompoundIndex(name = "IDX_JR_STATUS", def = "{'instituteJoinStatus' : 1}", unique = false)})
@EqualsAndHashCode
public class InstituteJoinRequest {

    @Id
    private String id;

    private String userId;

    private String instituteName;

    private String instituteCountry;

    private String typeOfInstitute;

    private String type;

    private String firstName;

    private String lastName;

    private String title;

    private String workEmail;

    private String workPhoneNumber;

    private String managementName;

    private String managementEmail;

    private String managementPhoneNumber;

    private InstituteJoinStatus instituteJoinStatus;

    private Date createdOn;

    private Date updatedOn;

    private String createdBy;

    private String updatedBy;

    public InstituteJoinRequest(String userId, String instituteName, String instituteCountry, String typeOfInstitute,
                                String type, String firstName, String lastName, String title, String workEmail, String workPhoneNumber,
                                String managementName, String managementEmail, String managementPhoneNumber,
                                InstituteJoinStatus instituteJoinStatus, Date createdOn, Date updatedOn, String createdBy,
                                String updatedBy) {
        super();
        this.userId = userId;
        this.instituteName = instituteName;
        this.instituteCountry = instituteCountry;
        this.typeOfInstitute = typeOfInstitute;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.workEmail = workEmail;
        this.workPhoneNumber = workPhoneNumber;
        this.managementName = managementName;
        this.managementEmail = managementEmail;
        this.managementPhoneNumber = managementPhoneNumber;
        this.instituteJoinStatus = instituteJoinStatus;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
