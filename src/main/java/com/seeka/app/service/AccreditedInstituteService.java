package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.AccreditedInstitute;
import com.seeka.app.dao.IAccreditedInstituteDao;
import com.seeka.app.dto.AccreditedInstituteRequestDto;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AccreditedInstituteService implements IAccreditedInstituteService {

	@Autowired
	private IAccreditedInstituteDao iAccreditedInstituteDao;

	@Autowired
	private IImageService iImageService;

	@Value("${s3.url}")
	private String s3URL;

	@Override
	public AccreditedInstitute addAccreditedInstitute(final AccreditedInstituteRequestDto accreditedInstituteRequestDto) throws ValidationException {
		AccreditedInstitute existingAccreditedInstitute = iAccreditedInstituteDao
				.getAccreditedInstituteDetailBasedOnName(accreditedInstituteRequestDto.getName(), null);
		if (existingAccreditedInstitute != null) {
			throw new ValidationException("Same Accredited Institute is already exists");
		}

		AccreditedInstitute accreditedInstitute = new AccreditedInstitute();
		BeanUtils.copyProperties(accreditedInstituteRequestDto, accreditedInstitute);
		accreditedInstitute.setIsActive(true);
		accreditedInstitute.setCreatedBy("API");
		accreditedInstitute.setCreatedOn(new Date());
		iAccreditedInstituteDao.save(accreditedInstitute);
		return accreditedInstitute;
	}

	@Override
	public List<AccreditedInstitute> getAccreditedInstituteList(final Integer pageNumber, final Integer pageSize) {
		int startIndex = (pageNumber - 1) * pageSize;
		List<AccreditedInstitute> accreditedInstitutes = iAccreditedInstituteDao.getAccreditedInstituteList(startIndex, pageSize);
		List<AccreditedInstitute> resultList = new ArrayList<>();

		for (AccreditedInstitute accreditedInstitute : accreditedInstitutes) {
			if (accreditedInstitute.getLogoImage() != null) {
				accreditedInstitute.setLogoUrl(s3URL + "" + accreditedInstitute.getLogoImage());
			}
			resultList.add(accreditedInstitute);
		}
		return resultList;
	}

	@Override
	public AccreditedInstitute getAccreditedInstituteDetail(final BigInteger accreditedInstituteId) {
		AccreditedInstitute accreditedInstitute = iAccreditedInstituteDao.getAccreditedInstituteDetail(accreditedInstituteId);
		if (accreditedInstitute.getLogoImage() != null) {
			accreditedInstitute.setLogoUrl(s3URL + "" + accreditedInstitute.getLogoImage());
		}
		return accreditedInstitute;
	}

	@Override
	public void addAccreditedLogo(final MultipartFile file, final AccreditedInstitute accreditedInstitute) {
		String logoName = iImageService.uploadImage(file, accreditedInstitute.getId(), "ACCREDITED_INSTITUTE");
		accreditedInstitute.setLogoImage(logoName);
		iAccreditedInstituteDao.update(accreditedInstitute);
	}

	@Override
	public AccreditedInstitute updateAccreditedInstitute(final BigInteger accreditedInstituteId,
			final AccreditedInstituteRequestDto accreditedInstituteRequestDto) throws ValidationException {
		AccreditedInstitute existingAccreditedInstitute = iAccreditedInstituteDao.getAccreditedInstituteDetail(accreditedInstituteId);
		if (existingAccreditedInstitute == null) {
			throw new ValidationException("Accredited institute not found for id" + accreditedInstituteId);
		}

		AccreditedInstitute existingAccreditedInstitute1 = iAccreditedInstituteDao
				.getAccreditedInstituteDetailBasedOnName(accreditedInstituteRequestDto.getName(), accreditedInstituteId);
		if (existingAccreditedInstitute1 != null) {
			throw new ValidationException("Same Accredited Institute is already exists");
		}
		existingAccreditedInstitute.setDescription(accreditedInstituteRequestDto.getDescription());
		existingAccreditedInstitute.setName(accreditedInstituteRequestDto.getName());
		existingAccreditedInstitute.setUpdatedBy("API");
		existingAccreditedInstitute.setUpdatedOn(new Date());
		iAccreditedInstituteDao.update(existingAccreditedInstitute);
		return existingAccreditedInstitute;
	}

	@Override
	public void deleteAccreditedInstitute(final BigInteger accreditedInstituteId) throws ValidationException {
		AccreditedInstitute existingAccreditedInstitute = iAccreditedInstituteDao.getAccreditedInstituteDetail(accreditedInstituteId);
		if (existingAccreditedInstitute == null) {
			throw new ValidationException("Accredited institute not found for id" + accreditedInstituteId);
		}
		existingAccreditedInstitute.setIsActive(false);
		existingAccreditedInstitute.setDeletedBy("API");
		existingAccreditedInstitute.setDeletedOn(new Date());
		iAccreditedInstituteDao.update(existingAccreditedInstitute);
	}

}
