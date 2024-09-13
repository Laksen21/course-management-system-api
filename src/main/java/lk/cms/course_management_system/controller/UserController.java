package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.LoginResponseDto;
import lk.cms.course_management_system.dto.RegisterResponseDto;
import lk.cms.course_management_system.dto.UserDto;
import lk.cms.course_management_system.service.UserService;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    JwtAuthenticator jwtAuthenticator;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody UserDto userDto, @RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)){
            RegisterResponseDto register = userService.register(userDto);
            return new ResponseEntity<>(register, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserDto userDto) {
        LoginResponseDto login = userService.login(userDto);
        if (login.getToken() == null) {
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}
