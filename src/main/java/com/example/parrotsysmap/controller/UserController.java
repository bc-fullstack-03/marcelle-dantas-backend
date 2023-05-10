package com.example.parrotsysmap.controller;

import com.example.parrotsysmap.exception.EmailAlreadyExistsException;
import com.example.parrotsysmap.model.Post;
import com.example.parrotsysmap.model.User;
import com.example.parrotsysmap.dtos.ResponseDTO;
import com.example.parrotsysmap.dtos.UserDTO;
import com.example.parrotsysmap.service.user.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try{
            String result = this.userService.createUser(userDTO);
            return ResponseEntity.ok().body(result);
        }
        catch(final EmailAlreadyExistsException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/follow/{userIdFollower}/{userIdFollowed}")
    public ResponseEntity<?> followUser(@PathVariable("userIdFollower") ObjectId userId, @PathVariable("userIdFollowed") ObjectId userIdFollowed) {
        String result = this.userService.followUser(userId, userIdFollowed);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/unfollow/{userIdFollower}/{userIdUnfollowed}")
    public ResponseEntity<?> unfollowUser(@PathVariable ObjectId userIdFollower, @PathVariable ObjectId userIdUnfollowed) {
        String result = userService.unfollowUser(userIdFollower, userIdUnfollowed);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> findById(@RequestBody UUID userId) {
        UserDTO user = this.userService.findById(userId);
        return ResponseEntity.ok(user);
    }

     @PutMapping("/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody UUID userId) {
        ResponseDTO result = this.userService.updateUser(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profile/all")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> user = this.userService.findAllUsers();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/photo")
    public ResponseEntity<List<UserDTO>> uploadProfilePhoto(MultipartFile photo) {
        List<UserDTO> user = this.userService.uploadProfilePhoto();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<Post>> getAllPostsFromUser(@PathVariable UUID userId) {
        List<Post> posts = userService.getAllPostsForUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getPostFromFollowers(@PathVariable UUID userId) {
        List<User> users = this.userService.getFollowersFromUser(userId);
        return ResponseEntity.ok(users);
    }

//    @PostMapping("/delete/{postId}")
//    public ResponseEntity deleteUser(@PathVariable Long postId) {
//        this.use
//    }

//    @PostMapping("/users/signin")
//    public ResponseEntity<ResponseDTO> userSignIn(@RequestBody Login inputUser) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputUser.getEmail(), inputUser.getPassword()));
//            String token = jwtUtil.generateToken(inputUser.getEmail());
//
//            Optional<UserEntity> optUser = userRepo.findByEmail(inputUser.getEmail());
//            UserEntity user = optUser.get();
//            user.setPassword("");
//            return new ResponseEntity<ResponseObjectService>(new ResponseObjectService("success", "authenticated", new AuthorizedEntity(user, token)), HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<ResponseObjectService>(new ResponseObjectService("fail", "unauthenticated", null), HttpStatus.OK);
//        }
//    }

}
