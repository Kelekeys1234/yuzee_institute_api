package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.AccreditedInstituteDetail;
import com.seeka.app.dao.IAccreditedInstituteDetailDao;
import com.seeka.app.dto.AccreditedInstituteDto;
import com.seeka.app.enumeration.ReviewCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AccreditedInstituteDetailService implements IAccreditedInstituteDetailService {

	@Autowired
	private IAccreditedInstituteDetailDao iAccreditedInstituteDetailDao;

	@Autowired
	private IAccreditedInstituteService iAccreditedInstituteService;

	@Override
	public AccreditedInstituteDetail addAccreditedInstituteDetail(final AccreditedInstituteDetail accreditedInstituteDetail) throws ValidationException {
		if (!ReviewCategory.COURSE.name().equals(accreditedInstituteDetail.getEntityType())
				&& !ReviewCategory.INSTITUTE.name().equals(accreditedInstituteDetail.getEntityType())) {
			throw new ValidationException("entity type Either COURSE or INSTITUTE");
		}
		AccreditedInstituteDto accreditedInstitute = iAccreditedInstituteService
				.getAccreditedInstituteDetail(accreditedInstituteDetail.getAccreditedInstituteId());
		if (accreditedInstitute == null) {
			throw new ValidationException("Accredited Institute is not found for id : " + accreditedInstituteDetail.getAccreditedInstituteId());
		}
		AccreditedInstituteDetail accreditedInstituteDetail2 = iAccreditedInstituteDetailDao.getAccreditedInstituteDetailbasedOnParams(
				accreditedInstituteDetail.getAccreditedInstituteId(), accreditedInstituteDetail.getEntityId(), accreditedInstituteDetail.getEntityType());

		if (accreditedInstituteDetail2 != null) {
			throw new ValidationException("Details is already available for same Accredited Institute, entityId and entityType");
		}

		iAccreditedInstituteDetailDao.addAccreditedInstituteDetail(accreditedInstituteDetail);
		return accreditedInstituteDetail;
	}

	@Override
	public List<AccreditedInstituteDetail> getAccreditedInstituteDetailList(final String entityId, final String entityType, final Integer pageNumber,
			final Integer pageSize) {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		return iAccreditedInstituteDetailDao.getAccreditedInstituteDetailList(entityId, entityType, startIndex, pageSize);
	}

	@Override
	public List<AccreditedInstituteDetail> getAccreditedInstituteDetail(final String accreditedInstituteId) {
		return iAccreditedInstituteDetailDao.getAccreditedInstituteDetail(accreditedInstituteId);
	}

}
