package org.javaschool.service.service;

import org.javaschool.data.model.role.User;
import org.javaschool.service.service.dto.UserDto;

public interface UserService {

    UserDto get(Long id);

    UserDto getByUsername(String username);
}
