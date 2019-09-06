package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "institute_intake")
public class InstituteIntake implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 748946079064801464L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;

    @Column(name = "entity_id", nullable = false)
    private BigInteger entityId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "intake", nullable = false)
    private String intake;

    /**
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * @return the entityId
     */
    public BigInteger getEntityId() {
        return entityId;
    }

    /**
     * @param entityId
     *            the entityId to set
     */
    public void setEntityId(BigInteger entityId) {
        this.entityId = entityId;
    }

    /**
     * @return the entityType
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * @param entityType
     *            the entityType to set
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * @return the intake
     */
    public String getIntake() {
        return intake;
    }

    /**
     * @param intake the intake to set
     */
    public void setIntake(String intake) {
        this.intake = intake;
    }

    
}