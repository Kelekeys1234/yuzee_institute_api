package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobLevel;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingActivity;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.RelatedCareer;
import com.yuzee.app.dao.CareerUploadDao;
import com.yuzee.app.repository.CareerJobCourseSearchKeywordRepository;
import com.yuzee.app.repository.CareerJobLevelRepository;
import com.yuzee.app.repository.CareerJobRepository;
import com.yuzee.app.repository.CareerJobSkillRepository;
import com.yuzee.app.repository.CareerJobSubjectRepository;
import com.yuzee.app.repository.CareerJobTypeRepository;
import com.yuzee.app.repository.CareerJobWorkingActivityRepository;
import com.yuzee.app.repository.CareerJobWorkingStyleRepository;
import com.yuzee.app.repository.CareerRepository;
import com.yuzee.app.repository.RelatedCareerRepository;

@Component
@SuppressWarnings({ "deprecation", "unchecked" })
public class CareerUploadDaoImpl implements CareerUploadDao {
    @Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CareerRepository careerRepository;
	@Autowired
	private RelatedCareerRepository relatedCareerRepository;
	@Autowired
	private CareerJobRepository careerJobRepository;
	@Autowired
	private CareerJobCourseSearchKeywordRepository careerJobCourseSearchKeywordRepository;
	@Autowired
	private CareerJobLevelRepository careerJobLevelRepository;
	@Autowired
	private CareerJobSkillRepository careerJobSkillRepository;
	@Autowired
	private CareerJobSubjectRepository careerJobSubjectRepository;
	@Autowired
	private CareerJobTypeRepository careerobTypeRepository;
	@Autowired
	private CareerJobWorkingActivityRepository careerJobWorkingActivities;
	@Autowired
	private CareerJobWorkingStyleRepository careerJobWorkingStyleRepository;

	@Override
	public void saveCareerList(Careers careerList) {
		careerRepository.save(careerList);
	}

	@Override
	public void saveRelatedCareers(RelatedCareer relatedCareer) {
		relatedCareerRepository.save(relatedCareer);
	}

	@Override
	public void saveJobsData(CareerJob careerJobs) {
		careerJobRepository.save(careerJobs);
	}

	@Override
	public void saveJobsCourseSearchKeyword(CareerJobCourseSearchKeyword careerJobCourseSearchKeyword) {
		careerJobCourseSearchKeywordRepository.save(careerJobCourseSearchKeyword);
	}

	@Override
	public void saveJobsLevel(CareerJobLevel careerJobLevel) {
		careerJobLevelRepository.save(careerJobLevel);
	}

	@Override
	public void saveJobsSkill(CareerJobSkill careerJobSkill) {
		careerJobSkillRepository.save(careerJobSkill);
	}

	@Override
	public void saveJobSubject(CareerJobSubject careerJobSubject) {
		careerJobSubjectRepository.save(careerJobSubject);
	}

	@Override
	public void saveJobType(CareerJobType careerJobType) {
		careerobTypeRepository.save(careerJobType);
	}

	@Override
	public void saveJobWorkingActivity(CareerJobWorkingActivity careerJobWorkingActivity) {
		careerJobWorkingActivities.save(careerJobWorkingActivity);
	}

	@Override
	public void saveJobWorkingStyle(CareerJobWorkingStyle careerJobWorkingStyle) {
		careerJobWorkingStyleRepository.save(careerJobWorkingStyle);
	}

	@Override
	public Careers getCareer(String careerName) {
		Careers careerr = new Careers();
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("career").is(careerName));
		List<Careers>career= mongoTemplate.find(query, Careers.class, "career_list");
		 for(Careers careers:career) {
			 careerr= careers;
		 }
		return careerr;
         
	}

	@Override
	public RelatedCareer getRelatedCareer(String careerId, String relatedCareerName) {
		RelatedCareer relatedCareer = null;
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careers.id").is(careerId));
		List<RelatedCareer> relatedCareerFromDB = mongoTemplate.find(query, RelatedCareer.class, "relatedCareer");
		if (!CollectionUtils.isEmpty(relatedCareerFromDB)) {
			relatedCareer = relatedCareerFromDB.get(0);
		}
		return relatedCareer;
	}

	@Override
	public CareerJob getJob(String job) {
		CareerJob careerJob = null;
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("job").is(job));
		List<CareerJob> careerJobsFromDB = mongoTemplate.find(query, CareerJob.class, "jobs");
		if (!CollectionUtils.isEmpty(careerJobsFromDB)) {
			careerJob = careerJobsFromDB.get(0);
		}
		return careerJob;
	}

	@Override
	public CareerJobCourseSearchKeyword getJobCourseSearchKeyword(String jobId, String courseSearchKeyword) {
		CareerJobCourseSearchKeyword careerJobCourseSearchKeyword = null;
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria
				.where("careerJobCourseSearchKeyword.courseSearchKeyword").is(courseSearchKeyword));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobs.id").is(jobId));
		List<CareerJobCourseSearchKeyword> careerJobCourseSearchKeywordFromDB = mongoTemplate.find(query,
				CareerJobCourseSearchKeyword.class, "job_course_search_keyword");
		if (!CollectionUtils.isEmpty(careerJobCourseSearchKeywordFromDB)) {
			careerJobCourseSearchKeyword = careerJobCourseSearchKeywordFromDB.get(0);
		}
		return careerJobCourseSearchKeyword;
	}

	@Override
	public CareerJobLevel getJobLevel(String jobId, String levelId) {
		Query query = new Query();
		CareerJobLevel careerJobLevel = new CareerJobLevel();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("level.id").is(levelId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobs.id").is(jobId));
		List<CareerJobLevel> careerJobLevelFromDB = mongoTemplate.find(query, CareerJobLevel.class, "careerJobs");
		if (!CollectionUtils.isEmpty(careerJobLevelFromDB)) {
			careerJobLevel = careerJobLevelFromDB.get(0);
		}
		return careerJobLevel;
	}

	@Override
	public CareerJobSkill getJobSkill(String jobId, String skillName) {
		Query query = new Query();
		CareerJobSkill careerJobSkill = null;
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobs.id").is(jobId));
		query.addCriteria(
				org.springframework.data.mongodb.core.query.Criteria.where("careerJobSkill.careerJobs").is(skillName));
		List<CareerJobSkill> careerJobSkillFromDB = mongoTemplate.find(query, CareerJobSkill.class, "careerJobSkill");
		if (!CollectionUtils.isEmpty(careerJobSkillFromDB)) {
			careerJobSkill = careerJobSkillFromDB.get(0);
		}
		return careerJobSkill;
	}

	@Override
	public CareerJobSubject getJobSubject(String jobId, String subjectName) {
		Query query = new Query();
		CareerJobSubject careerJobSubject = null;
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobs.id").is(jobId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("subject").is(subjectName));
		List<CareerJobSubject> careerJobSubjectFromDB = mongoTemplate.find(query, CareerJobSubject.class,
				"CareerJobSubject");
		if (!CollectionUtils.isEmpty(careerJobSubjectFromDB)) {
			careerJobSubject = careerJobSubjectFromDB.get(0);
		}
		return careerJobSubject;
	}

	@Override
	public CareerJobType getJobType(String jobId, String jobType) {
		Query query = new Query();
		CareerJobType careerJobType = null;
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobs.id").is(jobId));
		query.addCriteria(
				org.springframework.data.mongodb.core.query.Criteria.where("careerJobType.jobType").is(jobType));
		List<CareerJobType> careerJobTypeFromDB = mongoTemplate.find(query, CareerJobType.class, "job_type");
		if (!CollectionUtils.isEmpty(careerJobTypeFromDB)) {
			careerJobType = careerJobTypeFromDB.get(0);
		}
		return careerJobType;
	}

	@Override
	public CareerJobWorkingActivity getJobWorkingActivity(String jobId, String workingActivity) {
		Query query = new Query();
		CareerJobWorkingActivity careerJobWorkingActivity = null;
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobs.id").is(jobId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria
				.where("careerJobWorkingActivity.workActivities").is(workingActivity));
		List<CareerJobWorkingActivity> careerJobWorkingActivityFromDB = mongoTemplate.find(query,
				CareerJobWorkingActivity.class, "careerJobWorkingActivity");

		if (!CollectionUtils.isEmpty(careerJobWorkingActivityFromDB)) {
			careerJobWorkingActivity = careerJobWorkingActivityFromDB.get(0);
		}
		return careerJobWorkingActivity;
	}

	@Override
	public CareerJobWorkingStyle getJobWorkingStyle(String jobId, String workingStyle) {
		Query query = new Query();
		CareerJobWorkingStyle careerJobWorkingStyle = null;
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobs.id").is(jobId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("careerJobWorkingStyle.workStyle")
				.is(workingStyle));
		List<CareerJobWorkingStyle> careerJobWorkingStyleFromDB = mongoTemplate.find(query, CareerJobWorkingStyle.class,
				"careerJobWorkingStyle");
		if (!CollectionUtils.isEmpty(careerJobWorkingStyleFromDB)) {
			careerJobWorkingStyle = careerJobWorkingStyleFromDB.get(0);
		}
		return careerJobWorkingStyle;
	}
}
