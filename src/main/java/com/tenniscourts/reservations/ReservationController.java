package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "Reservations of the tennis court" })
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(value = "/reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Book tennis court", notes = "Add a new reservation data to tennis court book system.")
    @PostMapping(value = "/book", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> bookReservation(
            @ApiParam(value = "Reservation data", required = true, example = "1")
            @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find reservation", notes = "Find by reservation id")
    @GetMapping(value="/find/{id}", produces = "application/json")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel reservation", notes = "Cancel by reservation id.")
    @PostMapping(value = "/cancel/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule reservation", notes = "Reschedule by reservation id and schedule id.")
    @PostMapping(value = "/reschedule/{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(
            @PathVariable("reservationId") Long reservationId,
            @PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
