package org.javaschool.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.pro.packaged.A;
import org.javaschool.data.model.role.User;
import org.javaschool.data.repository.UserRepository;
import org.javaschool.service.service.UserService;
import org.javaschool.service.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("init")
public class InitRestController {
    @Autowired
    private TypeRestController typeRestController;

    @Autowired
    private PositionRestController positionRestController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRestController departmentRestController;

    @Autowired
    private EmployeeRestController employeeRestController;

    @Autowired
    private PostRestController postRestController;

    @PostMapping(value = "{type}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<String> initTypeOrPose(@PathVariable("type") String type, @RequestBody String path) {

        if (type == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            File file = new File(path);
            ObjectMapper mapper = new ObjectMapper();

            if (type.equals("position")) {
                PostDto postDto = new PostDto();
                postDto.setId(1L);
                postDto.setPost("something@mail.ru");
                postRestController.createPost(postDto);
                List<PositionDto> positionDtos = Arrays.asList(mapper.readValue(file, PositionDto[].class));

                if (positionDtos != null) {
                    positionDtos.forEach(positionDto -> {
                        positionRestController.createPosition(positionDto);
                    });
                }
            } else if (type.equals("type")) {
                List<TypeDto> typeDtos = Arrays.asList(mapper.readValue(file, TypeDto[].class));

                if (typeDtos != null) {
                    typeDtos.forEach(typeDto -> {
                        typeRestController.createType(typeDto);
                    });
                }
            } else if (type.equals("department")) {
                System.out.println("Я ТУУУУТ");
                List<DepartmentDto> departmentDtos = Arrays.asList(mapper.readValue(file, DepartmentDto[].class));
                List<EmployeeDto> employeeDtos = new ArrayList<>();

                if (departmentDtos != null) {
                    departmentDtos.forEach(departmentDto -> {
                        employeeDtos.addAll(departmentDto.getEmployeeDtoList());
                        departmentDto.setEmployeeDtoList(new ArrayList<>());
                        departmentRestController.createDepartment(departmentDto);
                    });
                }

                employeeDtos.sort(new SortByEmployeeId());

                employeeDtos.forEach(employeeDto -> {
                    employeeRestController.createEmployee(employeeDto);
                });

                User admin = new User();
                admin.setUsername("admin");
                admin.setRole("ROLE_ADMIN");
                admin.setPassword("$2a$10$MsmL9LkIvVlxXIPOZhJJiu.3g4b/EdKmxdWt6DTT47mkTn56EE/6.");
                userRepository.save(admin);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return ResponseEntity.ok("ok");
    }



    class SortByEmployeeId implements Comparator<EmployeeDto> {
        public int compare(EmployeeDto a, EmployeeDto b) {
            if ( a.getId() < b.getId() ) return -1;
            else if ( a.getId() == b.getId() ) return 0;
            else return 1;
        }
    }

}
