package org.javaschool.rest.controller;

import org.javaschool.service.service.TypesService;
import org.javaschool.service.service.dto.EmployeeDto;
import org.javaschool.service.service.dto.TypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("type")
public class TypeRestController {

    @Autowired
    private TypesService typeService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TypeDto> getEmployee(@PathVariable("id") Long typeId) {
        if (typeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TypeDto typeDto = this.typeService.get(typeId);

        if (typeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(typeDto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeDto> createEmployee(@RequestBody TypeDto typeDto) {
        if (typeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.typeService.save(typeDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeDto> updateEmployee(@PathVariable("id") Long typeId, @RequestBody TypeDto typeDto) {
        if (typeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (typeService.update(typeDto, typeId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeDto> deleteEmployee(@PathVariable("id") Long typeId) {
        if (typeService.delete(typeId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
