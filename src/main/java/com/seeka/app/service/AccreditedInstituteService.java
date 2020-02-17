package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.AccreditedInstitute;
import com.seeka.app.dao.IAccreditedInstituteDao;
import com.seeka.app.dto.AccreditedInstituteDto;
import com.seeka.app.dto.AccreditedInstituteRequestDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AccreditedInstituteService implements IAccreditedInstituteService {

	@Autowired
	private IAccreditedInstituteDao iAccreditedInstituteDao;

	@Autowired
	private IMediaService iMediaService;

	@Autowired
	private IStorageService iStorageService;

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
	public List<AccreditedInstituteDto> getAccreditedInstituteList(final Integer pageNumber, final Integer pageSize) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<AccreditedInstitute> accreditedInstitutes = iAccreditedInstituteDao.getAccreditedInstituteList(startIndex, pageSize);
		List<AccreditedInstituteDto> resultList = new ArrayList<>();

		for (AccreditedInstitute accreditedInstitute : accreditedInstitutes) {
			AccreditedInstituteDto accreditedInstituteDto = new AccreditedInstituteDto();
			BeanUtils.copyProperties(accreditedInstitute, accreditedInstituteDto);
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(accreditedInstitute.getId(), ImageCategory.ACCREDITED_INSTITUTE.name(),
					null, "en");
			if (storageDTOList != null && !storageDTOList.isEmpty()) {
				accreditedInstituteDto.setStorageDto(storageDTOList.get(0));
			}
			resultList.add(accreditedInstituteDto);
		}
		return resultList;
	}

	@Override
	public AccreditedInstituteDto getAccreditedInstituteDetail(final String accreditedInstituteId) throws ValidationException {
		AccreditedInstitute accreditedInstitute = iAccreditedInstituteDao.getAccreditedInstituteDetail(accreditedInstituteId);
		AccreditedInstituteDto accreditedInstituteDto = new AccreditedInstituteDto();
		BeanUtils.copyProperties(accreditedInstitute, accreditedInstituteDto);
		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(accreditedInstitute.getId(), ImageCategory.ACCREDITED_INSTITUTE.name(), null,
				"en");
		if (storageDTOList != null && !storageDTOList.isEmpty()) {
			accreditedInstituteDto.setStorageDto(storageDTOList.get(0));
		}
		return accreditedInstituteDto;
	}

	@Override
	public void addAccreditedLogo(final MultipartFile file, final AccreditedInstitute accreditedInstitute) {
		String logoName = iMediaService.uploadImage(file, accreditedInstitute.getId(), ImageCategory.ACCREDITED_INSTITUTE.name(), null);
		System.out.println("Accredited institute media upload for id - >" + accreditedInstitute.getId() + " and Image name :" + logoName);
	}

	@Override
	public AccreditedInstitute updateAccreditedInstitute(final String accreditedInstituteId,
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
	public void deleteAccreditedInstitute(final String accreditedInstituteId) throws ValidationException {
		AccreditedInstitute existingAccreditedInstitute = iAccreditedInstituteDao.getAccreditedInstituteDetail(accreditedInstituteId);
		if (existingAccreditedInstitute == null) {
			throw new ValidationException("Accredited institute not found for id" + accreditedInstituteId);
		}
		existingAccreditedInstitute.setIsActive(false);
		existingAccreditedInstitute.setDeletedBy("API");
		existingAccreditedInstitute.setDeletedOn(new Date());
		iAccreditedInstituteDao.update(existingAccreditedInstitute);
	}

	@Override
	public List<AccreditedInstituteRequestDto> getAllAccreditedInstitutes() {
		List<AccreditedInstituteRequestDto> accreditedInstituteRequestDto = new ArrayList<>();
		List<AccreditedInstitute> accreditedInstitutes = iAccreditedInstituteDao.getAllAccreditedInstitutes();
		for (AccreditedInstitute accreditedInstitute : accreditedInstitutes) {
			AccreditedInstituteRequestDto instituteRequestDto = new AccreditedInstituteRequestDto();
			BeanUtils.copyProperties(accreditedInstitute, instituteRequestDto);
			accreditedInstituteRequestDto.add(instituteRequestDto);
		}
		return accreditedInstituteRequestDto;
	}

}
