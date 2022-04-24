package org.javaschool.rest.controller;

import org.javaschool.data.model.role.User;
import org.javaschool.service.service.UserService;
import org.javaschool.service.service.dto.TypeDto;
import org.javaschool.service.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserDto> getEmployee(@PathVariable("id") Long typeId) {
        if (typeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = this.userService.get(typeId);

        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userDto);
    }
}
