package org.javaschool.rest.controller;


import org.javaschool.service.service.EmployeesService;
import org.javaschool.service.service.UserService;
import org.javaschool.service.service.dto.EmployeeDto;
import org.javaschool.service.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("employee")
public class EmployeeRestController {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeesService employeeService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long employeeId) {
        if (employeeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EmployeeDto employeeDto = this.employeeService.get(employeeId);

        if (employeeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        if (!checkPost(employeeDto.getDepartmentId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (employeeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.employeeService.save(employeeDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody EmployeeDto employeeDto) {
        if (!checkValidDepartment(employeeId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (employeeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (employeeService.update(employeeDto, employeeId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable("id") Long employeeId) {
        if (!checkValidDepartment(employeeId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        UserDto user = userService.getByUsername(httpServletRequest.getRemoteUser());
        if (user.getRole().equals("ROLE_ADMINISTRATOR")) {
            if (employeeService.adminDelete(employeeId)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            if (employeeService.moderatorDelete(employeeId)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    private boolean checkPost(Long departmentToPost) {
        UserDto user = userService.getByUsername(httpServletRequest.getRemoteUser());
        if ("ROLE_MODERATOR".equals(user.getRole())) {
            if (user.getValidDepartments().contains(departmentToPost)) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    private boolean checkValidDepartment(Long id) {
        Long departmentId = employeeService.get(id).getDepartmentId();
        UserDto user = userService.getByUsername(httpServletRequest.getRemoteUser());
        if ("ROLE_MODERATOR".equals(user.getRole())) {
            if (user.getValidDepartments().contains(departmentId)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
