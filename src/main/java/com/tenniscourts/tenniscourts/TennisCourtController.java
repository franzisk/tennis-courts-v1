package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "Tennis court registrations" })
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(value = "/tennisCourt")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiOperation(value = "Add tennis court", notes = "Add a new tennis court entry")
    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> addTennisCourt(@ApiParam(value = "Tennis court data", required = true, example = "1") @RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation(value = "Find tennis court", notes = "Recover a tennis court by its id")
    @GetMapping("/find/{id}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@ApiParam(value = "Tennis court id", required = true, example = "1") @PathVariable("id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(value = "Find tennis court", notes = "Recover a tennis with schedules by its id")
    @GetMapping("/find-with-schedules/{id}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@ApiParam(value = "Tennis court id", required = true, example = "1") @PathVariable("id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

}
