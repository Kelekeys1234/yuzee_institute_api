package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.bean.UserEducationDetails;
import com.seeka.app.bean.UserEnglishScore;

public class EducationSystemResponse {

    private BigInteger userId;

    private UserEducationDetails educationDetail;

    private List<UserEnglishScore> englishScoresList;

    private List<UserEducationAOLevelSubjects> educationAOLevelSubjectList;

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
    public UserEducationDetails getEducationDetail() {
        return educationDetail;
    }

    /**
     * @param educationDetail
     *            the educationDetail to set
     */
    public void setEducationDetail(UserEducationDetails educationDetail) {
        this.educationDetail = educationDetail;
    }

    /**
     * @return the englishScoresList
     */
    public List<UserEnglishScore> getEnglishScoresList() {
        return englishScoresList;
    }

    /**
     * @param englishScoresList
     *            the englishScoresList to set
     */
    public void setEnglishScoresList(List<UserEnglishScore> englishScoresList) {
        this.englishScoresList = englishScoresList;
    }

    /**
     * @return the educationAOLevelSubjectList
     */
    public List<UserEducationAOLevelSubjects> getEducationAOLevelSubjectList() {
        return educationAOLevelSubjectList;
    }

    /**
     * @param educationAOLevelSubjectList
     *            the educationAOLevelSubjectList to set
     */
    public void setEducationAOLevelSubjectList(List<UserEducationAOLevelSubjects> educationAOLevelSubjectList) {
        this.educationAOLevelSubjectList = educationAOLevelSubjectList;
    }

}