package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserCourseReview;
import com.seeka.app.bean.UserInfo;
import com.seeka.app.dto.UserReviewRequestDto;
import com.seeka.app.enumeration.ReviewCategory;
import com.seeka.app.enumeration.StudentType;

@Repository
@SuppressWarnings("unchecked")
public class UserInstCourseReviewDAO implements IUserInstCourseReviewDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(UserCourseReview reviewObj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(reviewObj);
    }

    @Override
    public void update(UserCourseReview reviewObj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(reviewObj);
    }

    @Override
    public UserCourseReview get(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        UserCourseReview user = session.get(UserCourseReview.class, userId);
        return user;
    }

    @Override
    public List<UserCourseReview> getAllReviewsByFilter(UserReviewRequestDto filterObj) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT id, review_category, student_type, r.user_id, full_name, "
                        + "is_anonymous,overall_course_rating,comments,review_star, r.created_on ,u.citizenship FROM "
                        + "user_course_review r  inner join user_info u  on u.user_id = r.user_id where is_active=1 ";
        if (null != filterObj.getCourseId()) {
            sqlQuery += " and course_id = '" + filterObj.getCourseId() + "'";
        } else if (null != filterObj.getInstituteId()) {
            sqlQuery += " and instiute_id ='" + filterObj.getInstituteId() + "'";
        }
        String sortingQuery = " order by overall_course_rating desc,created_on desc";
        String sizeQuery = sqlQuery + " " + sortingQuery;

        Query query1 = session.createSQLQuery(sizeQuery);
        List<Object[]> rows1 = query1.list();
        sqlQuery += sortingQuery + " LIMIT " + filterObj.getPageNumber() + " ," + filterObj.getMaxSizePerPage();
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<UserCourseReview> list = new ArrayList<UserCourseReview>();
        UserCourseReview obj = null;
        for (Object[] row : rows) {
            try {
                obj = new UserCourseReview();
                obj.setId(new BigInteger((String.valueOf(row[0]))));
                obj.setReviewCategory(ReviewCategory.valueOf(String.valueOf(row[1])).toString());
                obj.setStudentType(StudentType.valueOf(String.valueOf(row[2])).toString());
                UserInfo info = new UserInfo();
                info.setUserId(new BigInteger((String.valueOf(row[3]))));
                obj.setUserInfo(info);
                obj.setFullName(String.valueOf(row[4]));
                obj.setIsAnonymous(Boolean.valueOf(String.valueOf(row[5])));
                obj.setOverallCourseRating(Double.valueOf(String.valueOf(row[6])));
                obj.setComments(String.valueOf(row[7]));
                obj.setReviewStar(Double.valueOf(String.valueOf(row[8])));
                obj.setCountry(String.valueOf(row[10]));
                obj.setTotalCount(rows1.size());
                list.add(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @SuppressWarnings("unused")
    @Override
    public Boolean findReviewByFilters(UserReviewRequestDto filterObj) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT id, review_category, student_type, user_id, full_name, "
                        + "is_anonymous,overall_course_rating,comments,review_star, created_on FROM user_course_review  where is_active=1 " + "and user_id="
                        + filterObj.getUserId();
        if (null != filterObj.getCourseId()) {
            sqlQuery += " and course_id =" + filterObj.getCourseId();
        } else if (null != filterObj.getInstituteId()) {
            sqlQuery += " and instiute_id =" + filterObj.getInstituteId();
        }

        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        Boolean reviewThere = false;
        for (Object[] row : rows) {
            reviewThere = true;
            break;
        }
        return reviewThere;
    }

    @Override
    public UserCourseReview getOverAllReview(UserReviewRequestDto filterObj) {

        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT Avg(lecturers) as lecturers , Avg(learning_materials) as learning_materials ,Avg(content) as content, "
                        + "Avg(workload) as workload, Avg(skill_development) as skill_development, Avg(future_studies_career_palnning)  as future_studies_career_palnning,"
                        + "Avg(future_studies_career_prepration)  as future_studies_career_prepration, Avg(overall_course_rating)  as overall_course_rating, "
                        + "AVG(review_star)  as review_star  FROM user_course_review r  where is_active=1 ";
        if (null != filterObj.getCourseId()) {
            sqlQuery += " and course_id =" + filterObj.getCourseId();
        } else if (null != filterObj.getInstituteId()) {
            sqlQuery += " and instiute_id =" + filterObj.getInstituteId();
        }
        sqlQuery += " group by lecturers,learning_materials,content,workload,skill_development,future_studies_career_palnning,future_studies_career_prepration,review_star";

        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        UserCourseReview obj = null;
        for (Object[] row : rows) {
            try {
                obj = new UserCourseReview();
                obj.setLecturers(Double.valueOf(String.valueOf(row[0])));
                obj.setLearningMaterials(Double.valueOf(String.valueOf(row[1])));
                obj.setContent(Double.valueOf(String.valueOf(row[2])));
                obj.setWorkload(Double.valueOf(String.valueOf(row[3])));
                obj.setSkillDevelopment(Double.valueOf(String.valueOf(row[4])));
                obj.setFutureStudiesCareerPalnning(Double.valueOf(String.valueOf(row[5])));
                obj.setFutureStudiesCareerPrepration(Double.valueOf(String.valueOf(row[6])));
                obj.setOverallCourseRating(Double.valueOf(String.valueOf(row[7])));
                obj.setReviewStar(Double.valueOf(String.valueOf(row[8])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    @Override
    public List<UserCourseReview> getTopReviewsByFilter(BigInteger courseId, BigInteger instituteId) {

        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT id, review_category, student_type, r.user_id, full_name, "
                        + "is_anonymous,overall_course_rating,comments,review_star, r.created_on, u.citizenship FROM "
                        + "user_course_review r  inner join user_info u  on u.user_id = r.user_id where is_active=1 ";
        sqlQuery += " and (r.course_id ='" + courseId + "' or r.instiute_id ='" + instituteId + "' )";
        String sortingQuery = " order by overall_course_rating desc";
        sqlQuery += sortingQuery + " OFFSET 0 ROWS FETCH NEXT 2 ROWS ONLY";
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<UserCourseReview> list = new ArrayList<UserCourseReview>();
        UserCourseReview obj = null;
        for (Object[] row : rows) {
            try {
                obj = new UserCourseReview();
                obj.setId(new BigInteger((String.valueOf(row[0]))));
                obj.setReviewCategory(ReviewCategory.valueOf(String.valueOf(row[1])).toString());
                obj.setStudentType(StudentType.valueOf(String.valueOf(row[2])).toString());

                UserInfo info = new UserInfo();
                info.setUserId(new BigInteger((String.valueOf(row[3]))));
                obj.setUserInfo(info);
                obj.setFullName(String.valueOf(row[4]));
                obj.setIsAnonymous(Boolean.valueOf(String.valueOf(row[5])));
                obj.setOverallCourseRating(Double.valueOf(String.valueOf(row[6])));
                obj.setComments(String.valueOf(row[7]));
                obj.setReviewStar(Double.valueOf(String.valueOf(row[8])));
                obj.setCountry(String.valueOf(row[10]));
                list.add(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
