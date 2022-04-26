package org.javaschool.rest.controller;

import org.javaschool.data.model.employee.Position;
import org.javaschool.service.service.PositionService;
import org.javaschool.service.service.dto.DepartmentDto;
import org.javaschool.service.service.dto.PositionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("position")
public class PositionRestController {

    @Autowired
    private PositionService positionService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PositionDto> getPosition(@PathVariable("id") Long positionId) {
        if (positionId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PositionDto positionDto = this.positionService.get(positionId);

        if (positionDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(positionDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PositionDto>> getAll() {
        List<PositionDto> positionDtos = positionService.getAll();

        if (positionDtos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(positionDtos);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionDto> createPosition(@RequestBody PositionDto positionDto) {
        if (positionDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.positionService.save(positionDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionDto> updatePosition(@PathVariable("id") Long positionId, @RequestBody PositionDto positionDto) {
        if (positionDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (positionService.update(positionDto, positionId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionDto> deletePosition(@PathVariable("id") Long positionId) {
        if (positionService.delete(positionId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
