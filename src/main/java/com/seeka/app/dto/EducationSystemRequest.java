package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class EducationSystemRequest {

    private BigInteger userId;

    private EducationDetailDto educationDetail;

    private List<EnglishScoresDto> englishScoresList;

    private List<EducationAOLevelSubjectDto> educationAOLevelSubjectList;
    
    private String createdBy;
    private String updatedBy;

    /**
     * @return the userId
     */
    public BigInteger getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    /**
     * @return the educationDetail
     */
    public EducationDetailDto getEducationDetail() {
        return educationDetail;
    }

    /**
     * @param educationDetail
     *            the educationDetail to set
     */
    public void setEducationDetail(EducationDetailDto educationDetail) {
        this.educationDetail = educationDetail;
    }

    /**
     * @return the englishScoresList
     */
    public List<EnglishScoresDto> getEnglishScoresList() {
        return englishScoresList;
    }

    /**
     * @param englishScoresList
     *            the englishScoresList to set
     */
    public void setEnglishScoresList(List<EnglishScoresDto> englishScoresList) {
        this.englishScoresList = englishScoresList;
    }

    /**
     * @return the educationAOLevelSubjectList
     */
    public List<EducationAOLevelSubjectDto> getEducationAOLevelSubjectList() {
        return educationAOLevelSubjectList;
    }

    /**
     * @param educationAOLevelSubjectList
     *            the educationAOLevelSubjectList to set
     */
    public void setEducationAOLevelSubjectList(List<EducationAOLevelSubjectDto> educationAOLevelSubjectList) {
        this.educationAOLevelSubjectList = educationAOLevelSubjectList;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}