package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.*;
import lk.cms.course_management_system.entity.User;
import lk.cms.course_management_system.repository.UserRepository;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtAuthenticator jwtAuthenticator;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterResponseDto addUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword()); //Bcrypt
        User save = userRepository.save(new User(userDto.getUsername(), encodedPassword));
        return new RegisterResponseDto(save.getUsername(), "User Registered");
    }

    public LoginResponseDto login(UserDto userDto) {
        User userByUsername = userRepository.getUserByUsername(userDto.getUsername());
        if(userByUsername == null) {
            return new LoginResponseDto(null,null, "No User Found", null);
        }
        if (passwordEncoder.matches(userDto.getPassword(), userByUsername.getPassword())) {
            String jwtToken = jwtAuthenticator.generateJwtToken(userByUsername);
            return new LoginResponseDto(userByUsername.getId(),userByUsername.getUsername(), "Login Success !", jwtToken);
        }
        return new LoginResponseDto(userByUsername.getId(),userByUsername.getUsername(), "Login Failed !", null);
    }

    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<UserDto> dtoList = new ArrayList<>();
        for (User users : allUsers) {
            dtoList.add(new UserDto(users.getId(), users.getUsername()));
        }
        return dtoList;
    }

    public UserDto updateUser(Integer userId, UserDto userDto) {
        if (userRepository.existsById(userId)) {
            User update = userRepository.save(new User(userId, userDto.getUsername(), userDto.getPassword()));
            return new UserDto(update.getId(), update.getUsername());
        }
        return null;
    }

    public boolean deleteUser(Integer userId) {
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public PasswordChangeDto updatePassword(Integer userId, PasswordResetDto passwordResetDto) {
        Optional<User> existUser = userRepository.findById(userId);
        if (existUser.isPresent()) {
            User user = existUser.get();
            if (passwordEncoder.matches(passwordResetDto.getCurrentPassword(), user.getPassword())) {
                String encodedNewPassword = passwordEncoder.encode(passwordResetDto.getNewPassword());
                User updatePassword = userRepository.save(new User(userId, user.getUsername(), encodedNewPassword));
                return new PasswordChangeDto(updatePassword.getId(), updatePassword.getUsername(), "Password successfully changed !");
            }
            throw new IllegalArgumentException("Current password is incorrect");
        }
        throw new NoSuchElementException("User not found");
    }

}
