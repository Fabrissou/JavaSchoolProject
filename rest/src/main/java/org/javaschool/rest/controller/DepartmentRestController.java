package org.javaschool.rest.controller;

import org.javaschool.service.service.DepartmentsService;
import org.javaschool.service.service.UserService;
import org.javaschool.service.service.dto.DepartmentDto;
import org.javaschool.service.service.dto.EmployeeDto;
import org.javaschool.service.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentRestController {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentsService departmentsService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable("id") Long departmentId) {
        if (departmentId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DepartmentDto departmentDto = this.departmentsService.get(departmentId);

        if (departmentDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(departmentDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<DepartmentDto>> getAll() {
        List<DepartmentDto> departmentDtos = departmentsService.getAll();

        if (departmentDtos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(departmentDtos);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        if (departmentDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.departmentsService.save(departmentDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody DepartmentDto departmentDto) {
        if (!checkPut(departmentId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (departmentDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (departmentsService.update(departmentDto, departmentId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable("id") Long departmentId) {
        if (departmentsService.delete(departmentId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean checkPut(Long departmentToPut) {
        UserDto user = userService.getByUsername(httpServletRequest.getRemoteUser());
        if ("ROLE_MODERATOR".equals(user.getRole())) {
            if (user.getValidDepartments().contains(departmentToPut)) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

}
