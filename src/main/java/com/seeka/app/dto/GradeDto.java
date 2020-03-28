package com.seeka.app.dto;

import java.util.List;

public class GradeDto {

	private String id;
    private String countryName;
    private String educationSystemId;
    private List<String> subjectGrades;
    private String grade;
	private String gpaGrade;


    /**
     * @return the educationSystemId
     */
    public String getEducationSystemId() {
        return educationSystemId;
    }

    /**
     * @param educationSystemId
     *            the educationSystemId to set
     */
    public void setEducationSystemId(String educationSystemId) {
        this.educationSystemId = educationSystemId;
    }

    /**
     * @return the subjectGrades
     */
    public List<String> getSubjectGrades() {
        return subjectGrades;
    }

    /**
     * @param subjectGrades
     *            the subjectGrades to set
     */
    public void setSubjectGrades(List<String> subjectGrades) {
        this.subjectGrades = subjectGrades;
    }

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGpaGrade() {
		return gpaGrade;
	}

	public void setGpaGrade(String gpaGrade) {
		this.gpaGrade = gpaGrade;
	}

}
