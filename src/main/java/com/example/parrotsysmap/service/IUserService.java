package com.example.parrotsysmap.service;

import com.example.parrotsysmap.model.Post;
import com.example.parrotsysmap.model.User;
import com.example.parrotsysmap.dtos.ResponseDTO;
import com.example.parrotsysmap.dtos.UserDTO;

import java.util.List;
import java.util.UUID;


public interface IUserService {
    User createUser(User user);

    void followUser(UUID userId);

    void unfollowUser(UUID userId);

    List<Post> getAllPostsForUser(UUID userId);

    List<User> getFollowersFromUser(UUID userId);

    UserDTO findById(UUID userId);

    ResponseDTO saveUser(UUID userId);

    ResponseDTO updateUser (UUID userId);

    List<UserDTO> findAllUsers();

    List<UserDTO> uploadProfilePhoto();
}
