package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TennisCourtRepository tennisCourtRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourt tennisCourt = tennisCourtRepository.findById(createScheduleRequestDTO.getTennisCourtId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });

        Schedule schedule = new Schedule();
        schedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        schedule.setTennisCourt(tennisCourt);
        schedule.setEndDateTime(createScheduleRequestDTO.getEndDateTime());

        return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    /**
     * Find schedules by start date and end date
     *
     * @param startDate
     * @param endDate
     * @return List of schedules
     */
    public List<ScheduleDTO> findSchedulesByDates(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59));

        List<Schedule> schedules = this.scheduleRepository.findByStartDateTimeAndEndDateTime(startDateTime, endDateTime);
        List<ScheduleDTO> list = new ArrayList<>();
        schedules.stream().forEach(item -> {
            ScheduleDTO schedule = new ScheduleDTO();
            schedule.setStartDateTime(item.getStartDateTime());
            schedule.setEndDateTime(item.getEndDateTime());
            schedule.setId(item.getId());
            schedule.setTennisCourtId(item.getTennisCourt().getId());
            schedule.setTennisCourt(new TennisCourtDTO(item.getTennisCourt().getId(), item.getTennisCourt().getName(), null));
            list.add(schedule);
        });
        return list;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).orElse(null));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
