package com.yuzee.app.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.Scholarship_;

@Service
public class ScholarshipSpecification {
	private ScholarshipSpecification() {
	}

	public static Specification<Scholarship> getScholarshipsBasedOnFilters(String countryName, String instituteId,
			String searchKeyword) {
		return new Specification<Scholarship>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Scholarship> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				if (!StringUtils.isEmpty(countryName)) {
					predicates.add(criteriaBuilder.equal(root.get(Scholarship_.countryName), countryName));
				}

				if (!StringUtils.isEmpty(instituteId)) {
					Join<Scholarship, Institute> instituteJoin = root.join(Scholarship_.INSTITUTE);
					predicates.add(criteriaBuilder.equal(instituteJoin.<String>get("id"), instituteId));
				}

				if (!StringUtils.isEmpty(searchKeyword)) {
					predicates.add(criteriaBuilder.like(root.get(Scholarship_.name), "%" + searchKeyword + "%"));
					predicates.add(
							criteriaBuilder.or(criteriaBuilder.like(root.get(Scholarship_.name), searchKeyword + "%")));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}
