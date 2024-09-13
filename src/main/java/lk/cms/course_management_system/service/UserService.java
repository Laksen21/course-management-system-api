package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.LoginResponseDto;
import lk.cms.course_management_system.dto.RegisterResponseDto;
import lk.cms.course_management_system.dto.UserDto;
import lk.cms.course_management_system.entity.User;
import lk.cms.course_management_system.repository.UserRepository;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtAuthenticator jwtAuthenticator;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterResponseDto register(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword()); //Bcrypt
        User save = userRepository.save(new User(userDto.getUsername(), encodedPassword));
        return new RegisterResponseDto(save.getUsername(), "User Registered");
    }

    public LoginResponseDto login(UserDto userDto) {
        User userByUsername = userRepository.getUserByUsername(userDto.getUsername());
        if (passwordEncoder.matches(userDto.getPassword(), userByUsername.getPassword())) {
            String jwtToken = jwtAuthenticator.generateJwtToken(userByUsername);
            return new LoginResponseDto(userByUsername.getUsername(), "Login Success !", jwtToken);
        }
        return new LoginResponseDto(userByUsername.getUsername(), "Login Failed !", null);
    }
}
