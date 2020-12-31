package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.dto.TimingResponseDto;
import com.yuzee.app.enumeration.DaysEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.TimingType;
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

	public List<TimingRequestDto> saveUpdateTimings(String loggedInUserId, List<TimingRequestDto> timingRequestDtos,
			String entityId) throws NotFoundException {
		log.info("inside TimingProcessor.saveUpdateTimings");
		List<Timing> dbTimings = timingDao.findByEntityTypeAndEntityId(EntityTypeEnum.COURSE, entityId);
		Map<String, Timing> dbTimingsMap = dbTimings.stream().collect(Collectors.toMap(Timing::getId, e -> e));
		if (!CollectionUtils.isEmpty(timingRequestDtos)) {
			Set<String> idsToBeUpdated = timingRequestDtos.stream().map(TimingRequestDto::getId)
					.collect(Collectors.toSet());
			List<Timing> timingsToBeRemoved = dbTimings.stream().filter(e -> !idsToBeUpdated.contains(e.getId()))
					.collect(Collectors.toList());
			timingDao.deleteAll(timingsToBeRemoved);
			dbTimings.removeAll(timingsToBeRemoved);

			for (TimingRequestDto courseTimingRequestDto : timingRequestDtos) {
				Timing timing = new Timing();
				timing.setAuditFields(loggedInUserId, null);
				if (!StringUtils.isEmpty(courseTimingRequestDto.getId())) {
					timing = dbTimingsMap.get(courseTimingRequestDto.getId());
					if (timing == null) {
						log.error("invalid timing found against");
						throw new NotFoundException("invalid timing found against");
					}
				}
				timing.setAuditFields(loggedInUserId, timing);
				timing.setEntityId(entityId);
				timing.setEntityType(EntityTypeEnum.valueOf(courseTimingRequestDto.getEntityType()));
				timing.setTimingType(TimingType.valueOf(courseTimingRequestDto.getTimingType()));
				Map<String, DayTimingDto> dayWiseTimingMap = courseTimingRequestDto.getTimings().stream()
						.collect(Collectors.toMap(DayTimingDto::getDay, e -> e));
				for (String day : dayWiseTimingMap.keySet()) {
					DayTimingDto dayTimingDto = dayWiseTimingMap.get(day);
					if (!ObjectUtils.isEmpty(dayTimingDto)) {
						if (DaysEnum.MONDAY.toString().equalsIgnoreCase(dayTimingDto.getDay())) {
							log.info("Adding Monday's opening and closing time");
							timing.setMonday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
						}
						if (DaysEnum.TUESDAY.toString().equalsIgnoreCase(dayTimingDto.getDay())) {
							log.info("Adding Tuesday's opening and closing time");
							timing.setTuesday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
						}
						if (DaysEnum.WEDNESDAY.toString().equalsIgnoreCase(dayTimingDto.getDay())) {
							log.info("Adding Wednesday's opening and closing time");
							timing.setWednesday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
						}
						if (DaysEnum.THURSDAY.toString().equalsIgnoreCase(dayTimingDto.getDay())) {
							log.info("Adding Thursday's opening and closing time");
							timing.setThursday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
						}
						if (DaysEnum.FRIDAY.toString().equalsIgnoreCase(dayTimingDto.getDay())) {
							log.info("Adding Friday's opening and closing time");
							timing.setFriday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
						}
						if (DaysEnum.SATURDAY.toString().equalsIgnoreCase(dayTimingDto.getDay())) {
							log.info("Adding Saturday's opening and closing time");
							timing.setSaturday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
						}
						if (DaysEnum.SUNDAY.toString().equalsIgnoreCase(dayTimingDto.getDay())) {
							log.info("Adding Sunday's opening and closing time");
							timing.setSunday(dayTimingDto.getOpeningFrom() + "-" + dayTimingDto.getOpeningTo());
						}
					}
				}
				if (StringUtils.isEmpty(timing.getId())) {
					dbTimings.add(timing);
				}
			}

			dbTimings = timingDao.saveAll(dbTimings);
			return dbTimings.stream().map(e -> convertTimingToTimingRequestDto(e)).collect(Collectors.toList());
		} else if (!dbTimings.isEmpty()) {
			timingDao.deleteAll(dbTimings);
		}
		return new ArrayList<>();
	}

	public List<TimingRequestDto> getTimingRequestDtoByEntityTypeAndEntityId(EntityTypeEnum entityType,
			String entityId) {
		log.info("inside TimingProcessor.getTimingRequestDtoByEntityTypeAndEntityId");
		List<Timing> timings = timingDao.findByEntityTypeAndEntityId(entityType, entityId);
		return timings.stream().map(e -> convertTimingToTimingRequestDto(e)).collect(Collectors.toList());
	}

	private TimingRequestDto convertTimingToTimingRequestDto(Timing timing) {
		TimingRequestDto timingRequestDto = modelMapepr.map(timing, TimingRequestDto.class);
		timingRequestDto.setTimings(
				CommonUtil.convertTimingResponseDtoToDayTimingDto(modelMapepr.map(timing, TimingResponseDto.class)));
		return timingRequestDto;
	}

	public TimingResponseDto getTimingResponseDtoByInstituteId(String instituteId) {
		log.debug("Inside getInstituteTimeByInstituteId() method");
		log.info("fetching isntitute timing from DB for instituteId " + instituteId);
		List<Timing> timings = timingDao.findByEntityTypeAndEntityId(EntityTypeEnum.INSTITUTE, instituteId);
		Timing timing = CollectionUtils.isEmpty(timings) ? null : timings.get(0);
		TimingResponseDto instituteTimingResponseDto = new TimingResponseDto();
		if (!ObjectUtils.isEmpty(timing)) {
			log.info("Institute timing is not null, hence coping bean to DTO class");
			BeanUtils.copyProperties(timing, instituteTimingResponseDto);
		}
		return instituteTimingResponseDto;
	}
}
