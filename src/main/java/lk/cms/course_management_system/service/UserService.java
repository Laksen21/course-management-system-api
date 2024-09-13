package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.LoginResponseDto;
import lk.cms.course_management_system.dto.RegisterResponseDto;
import lk.cms.course_management_system.dto.UserDto;
import lk.cms.course_management_system.entity.User;
import lk.cms.course_management_system.repo.UserRepo;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtAuthenticator jwtAuthenticator;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterResponseDto register(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword()); //Bcrypt
        User save = userRepo.save(new User(userDto.getUsername(), encodedPassword));
        return new RegisterResponseDto(save.getUsername(), "User Registered");
    }

    public LoginResponseDto login(UserDto userDto) {
        User userByUsername = userRepo.getUserByUsername(userDto.getUsername());
        if (passwordEncoder.matches(userDto.getPassword(), userByUsername.getPassword())) {
            String jwtToken = jwtAuthenticator.generateJwtToken(userByUsername);
            return new LoginResponseDto(userByUsername.getUsername(), "Login Success !", jwtToken);
        }
        return new LoginResponseDto(userByUsername.getUsername(), "Login Failed !", null);
    }
}
