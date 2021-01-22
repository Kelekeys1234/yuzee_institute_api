package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Timing;
import com.yuzee.app.dao.TimingDao;
import com.yuzee.app.dto.DayTimingDto;
import com.yuzee.app.dto.TimingDto;
import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.enumeration.DaysEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.TimingType;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TimingProcessor {

	@Autowired
	private TimingDao timingDao;

	@Autowired
	private ModelMapper modelMapepr;

	public List<TimingRequestDto> saveUpdateDeleteTimings(String loggedInUserId, EntityTypeEnum entityType,
			List<TimingRequestDto> timingRequestDtos, String entityId) throws NotFoundException {
		log.info("inside TimingProcessor.saveUpdateTimings");
		List<Timing> dbTimings = timingDao.findByEntityTypeAndEntityIdIn(entityType, Arrays.asList(entityId));
		Map<String, Timing> dbTimingsMap = dbTimings.stream().collect(Collectors.toMap(Timing::getId, e -> e));
		if (!CollectionUtils.isEmpty(timingRequestDtos)) {
			Set<String> idsToBeUpdated = timingRequestDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
					.map(TimingRequestDto::getId).collect(Collectors.toSet());
			List<Timing> timingsToBeRemoved = dbTimings.stream().filter(e -> !idsToBeUpdated.contains(e.getId()))
					.collect(Collectors.toList());
			timingDao.deleteAll(timingsToBeRemoved);
			dbTimings.removeAll(timingsToBeRemoved);
			for (TimingRequestDto timingRequestDto : timingRequestDtos) {
				Timing timing = new Timing();
				if (!StringUtils.isEmpty(timingRequestDto.getId())) {
					timing = dbTimingsMap.get(timingRequestDto.getId());
					if (timing == null) {
						log.error("invalid timing found against");
						throw new NotFoundException("invalid timing found against");
					}
				}
				timing.setAuditFields(loggedInUserId);
				timing.setEntityId(entityId);
				timing.setEntityType(EntityTypeEnum.valueOf(timingRequestDto.getEntityType()));
				timing.setTimingType(TimingType.valueOf(timingRequestDto.getTimingType()));
				setTimingFromDayTimingDtoList(timing, timingRequestDto.getTimings());
			}

			dbTimings = timingDao.saveAll(dbTimings);
			return dbTimings.stream().map(e -> convertTimingToTimingRequestDto(e)).collect(Collectors.toList());
		} else if (!dbTimings.isEmpty()) {
			timingDao.deleteAll(dbTimings);
		}
		return new ArrayList<>();
	}

	public TimingRequestDto saveUpdateTiming(String loggedInUserId, TimingRequestDto timingRequestDto)
			throws NotFoundException {
		log.info("inside TimingProcessor.saveUpdateTimings");

		Timing timing = modelMapepr.map(timingRequestDto, Timing.class);
		if (!StringUtils.isEmpty(timingRequestDto.getId())) {
			Optional<Timing> timingO = timingDao.findById(timingRequestDto.getId());
			if (!timingO.isPresent()) {
				log.error("invalid timing found against id: {}", timingRequestDto.getId());
				throw new NotFoundException("invalid timing found against id: " + timingRequestDto.getId());
			}
			timing = timingO.get();

		}
		timing.setAuditFields(loggedInUserId);
		setTimingFromDayTimingDtoList(timing, timingRequestDto.getTimings());

		List<Timing> dbTimings = timingDao.saveAll(Arrays.asList(timing));
		return convertTimingToTimingRequestDto(dbTimings.get(0));

	}

	private void setTimingFromDayTimingDtoList(Timing timing, List<DayTimingDto> dayTimingDtos) {
		log.info("inside TimingProcessor.setTimingFromDayTimingDtoList");
		dayTimingDtos.stream().forEach(e -> e.setDay(e.getDay().toUpperCase()));
		Map<String, DayTimingDto> dayWiseTimingMap = dayTimingDtos.stream()
				.collect(Collectors.toMap(DayTimingDto::getDay, e -> e));
		final String closed = "CLOSED";
		DayTimingDto dayTimingDto = dayWiseTimingMap.get(DaysEnum.MONDAY.name());
		if (dayTimingDto != null) {
			log.info("Adding Monday's opening and closing time");
			timing.setMonday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
		} else {
			timing.setMonday(closed);
		}

		dayTimingDto = dayWiseTimingMap.get(DaysEnum.TUESDAY.name());
		if (dayTimingDto != null) {
			log.info("Adding Tuesday's opening and closing time");
			timing.setTuesday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
		} else {
			timing.setTuesday(closed);
		}

		dayTimingDto = dayWiseTimingMap.get(DaysEnum.WEDNESDAY.name());
		if (dayTimingDto != null) {
			log.info("Adding Wednesday's opening and closing time");
			timing.setWednesday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
		} else {
			timing.setWednesday(closed);
		}

		dayTimingDto = dayWiseTimingMap.get(DaysEnum.THURSDAY.name());
		if (dayTimingDto != null) {
			log.info("Adding Thursday's opening and closing time");
			timing.setThursday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
		} else {
			timing.setThursday(closed);
		}

		dayTimingDto = dayWiseTimingMap.get(DaysEnum.FRIDAY.name());
		if (dayTimingDto != null) {
			log.info("Adding Friday's opening and closing time");
			timing.setFriday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
		} else {
			timing.setFriday(closed);
		}

		dayTimingDto = dayWiseTimingMap.get(DaysEnum.SATURDAY.name());
		if (dayTimingDto != null) {
			log.info("Adding Saturday's opening and closing time");
			timing.setSaturday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
		} else {
			timing.setSaturday(closed);
		}

		dayTimingDto = dayWiseTimingMap.get(DaysEnum.SUNDAY.name());
		if (dayTimingDto != null) {
			log.info("Adding Sunday's opening and closing time");
			timing.setSunday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
		} else {
			timing.setSunday(closed);
		}
	}

	public List<TimingRequestDto> getTimingRequestDtoByEntityTypeAndEntityIdIn(EntityTypeEnum entityType,
			List<String> entityIds) {
		log.info("inside TimingProcessor.getTimingRequestDtoByEntityTypeAndEntityIdIn");
		List<Timing> timings = timingDao.findByEntityTypeAndEntityIdIn(entityType, entityIds);
		return timings.stream().map(e -> convertTimingToTimingRequestDto(e)).collect(Collectors.toList());
	}

	private TimingRequestDto convertTimingToTimingRequestDto(Timing timing) {
		log.info("inside TimingProcessor.saveUpdateCourseSubjects");
		TimingRequestDto timingRequestDto = modelMapepr.map(timing, TimingRequestDto.class);
		timingRequestDto.setTimings(
				CommonUtil.convertTimingResponseDtoToDayTimingDto(modelMapepr.map(timing, TimingDto.class)));
		return timingRequestDto;
	}

	public TimingDto getTimingResponseDtoByInstituteId(String instituteId) {
		log.debug("Inside getTimingResponseDtoByInstituteId() method");
		log.info("fetching isntitute timing from DB for instituteId " + instituteId);
		List<Timing> timings = timingDao.findByEntityTypeAndEntityIdIn(EntityTypeEnum.INSTITUTE,
				Arrays.asList(instituteId));
		Timing timing = CollectionUtils.isEmpty(timings) ? null : timings.get(0);
		TimingDto instituteTimingResponseDto = new TimingDto();
		if (!ObjectUtils.isEmpty(timing)) {
			log.info("Institute timing is not null, hence coping bean to DTO class");
			BeanUtils.copyProperties(timing, instituteTimingResponseDto);
		}
		return instituteTimingResponseDto;
	}

	public void deleteTiming(String userId, EntityTypeEnum entityType, String entityId, String timingId)
			throws NotFoundException, ForbiddenException {
		log.info("inside TimingProcessor.deleteTiming");
		Timing timing = timingDao.findByEntityTypeAndEntityIdAndId(entityType, entityId, timingId);
		if (ObjectUtils.isEmpty(timing)) {
			log.error("invalid timing found against id: {}", timingId);
			throw new NotFoundException("invalid timing found against id: " + timingId);
		} else {
			if (!timing.getCreatedBy().equals(userId)) {
				log.error("user dont have access to delete the timing");
				throw new ForbiddenException("user dont have access to delete the timing");
			}
			timingDao.deleteByEntityTypeAndEntityIdAndId(entityType, entityId, timingId);
		}

	}
}
