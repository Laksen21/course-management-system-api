package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.*;
import lk.cms.course_management_system.service.UserService;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtAuthenticator jwtAuthenticator;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserDto userDto) {
        LoginResponseDto login = userService.login(userDto);
        if (login.getToken() == null) {
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<RegisterResponseDto> addUser(@RequestBody UserDto userDto, @RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            RegisterResponseDto register = userService.addUser(userDto);
            return new ResponseEntity<>(register, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(@RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            List<UserDto> allUsers = userService.getAllUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            UserDto update = userService.updateUser(userId,userDto);
            if(update == null){
                return new ResponseEntity<>("No user found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer userId, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            if(userService.deleteUser(userId)){
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("No data found !", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("change_password/{userId}")
    public ResponseEntity<Object> updatePassword(@PathVariable Integer userId, @RequestBody PasswordResetDto passwordResetDto, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            try {
                PasswordChangeDto updatePassword = userService.updatePassword(userId, passwordResetDto);
                return new ResponseEntity<>(updatePassword, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>("No user found", HttpStatus.NOT_FOUND);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Current password is incorrect", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
