package org.javaschool.service.service.impl;

import org.javaschool.data.model.role.User;
import org.javaschool.data.repository.UserRepository;
import org.javaschool.service.service.DepartmentsService;
import org.javaschool.service.service.UserService;
import org.javaschool.service.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentsService departmentsService;

    @Transactional
    @Override
    public UserDto get(Long id) {
        return mapperUserDto(userRepository.findById(id).get());
    }

    @Transactional
    @Override
    public UserDto getByUsername(String username) {
        return mapperUserDto(userRepository.findByUsername(username));
    }

    @Transactional
    public UserDto mapperUserDto(User user) {
        UserDto userDto = null;

        if (user != null) {
            userDto = new UserDto();
            Set<Long> validDepartments = new HashSet<>();

            userDto.setId(user.getId());
            userDto.setPassword(user.getPassword());
            userDto.setRole(user.getRole());
            userDto.setUsername(user.getUsername());

            if (user.getEmployeeId() != null) {
                userDto.setEmployeeId(user.getEmployeeId().getId());

                if ("ROLE_MODERATOR".equals(userDto.getRole())) {
                    departmentsService.addHierarchyToSet(validDepartments, user.getEmployeeId().getDepartmentId());
                }
            } else {
                userDto.setEmployeeId(null);
            }


            userDto.setValidDepartments(validDepartments);
        }
        System.out.println(userDto.toString());
        return userDto;
    }
}
