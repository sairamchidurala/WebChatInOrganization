package com.example.sms.service;

import com.example.sms.dto.UsersDTO;
import com.example.sms.model.User;
import com.example.sms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;

@Service
@Qualifier("userService")
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer userId) {
        Long tempId = userId.longValue();
        return userRepository.findById(tempId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer userId, User userDetails) {
        Long tempId = userId.longValue();
        User user = userRepository.findById(tempId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setOrgId(userDetails.getOrgId());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setActive(userDetails.isActive());
        user.setOnline(userDetails.isOnline());
        user.setUpdatedOn(new Date());

        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        Long tempId = userId.longValue();
        userRepository.deleteById(tempId);
    }

    public void updateOnlineStatusByEmail(String emailId, boolean status) {
        System.out.println("For UserEmail: " + emailId + " marking online status as:" + status);
        userRepository.updateOnlineStatusByEmail(emailId, status);
    }

    public User findByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId);
    }

    public List<UsersDTO> findAllOnlineUsers(String emailId) {
        List<User> users = userRepository.findUsersByOrgIdForEmail(emailId);
        return users.stream().map(this::convertToUsersDTO).collect(Collectors.toList());
    }

    private UsersDTO convertToUsersDTO(User user) {
        UsersDTO dto = new UsersDTO();
        dto.setUserId(user.getUserId() + "");
        dto.setEmailId(user.getEmailId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

}
