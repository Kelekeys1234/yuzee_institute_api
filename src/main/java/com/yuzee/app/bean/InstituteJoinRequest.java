package com.yuzee.app.bean;

import java.util.Date;
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
@EqualsAndHashCode
public class InstituteJoinRequest {

    @org.springframework.data.annotation.Id
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
