package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Slf4j // lombok
@RestController
@CrossOrigin
@Api(tags = {"All schedules operations"})
@RequestMapping(value = "/schedule")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation(value = "Add schedule", notes = "Add schedule to a tennis court")
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Void> addScheduleTennisCourt(
            @ApiParam(value = "Schedule data", required = true, example = "1")
            @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find schedules by dates", notes = "Find all schedules between startDate and endDate")
    @GetMapping(value = "/find", produces = "application/json")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(startDate, endDate));
    }

    @ApiOperation(value = "Find schedule", notes = "Find schedule by id")
    @GetMapping(value = "/find/{id}", produces = "application/json")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("id") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
