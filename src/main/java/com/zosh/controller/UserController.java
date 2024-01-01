package com.zosh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.models.User;
import com.zosh.repository.UserRepository;
import com.zosh.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	
//	@PostMapping("/users")
//	public User createUser(@RequestBody User user) {
//		User savedUser=userService.registerUser(user);
//		return savedUser;
//	}
	
	@GetMapping("/api/users")
	public List<User> getUsers() {
		List<User> users=userRepository.findAll();
		return users;
	}
	
	@GetMapping("/api/users/{userId}")
	public User getUsersById(@PathVariable("userId") Integer id) throws Exception {
		User user=userService.findUserById(id);
		return user;
	}
	
	
	@PutMapping("/api/users")
	public User updateUser(@RequestHeader("Authorization")String jwt, @RequestBody User user) throws Exception { 
		
		User reqUser=userService.findUserByJwt(jwt);
		
		
		User updatedUser=userService.updateUser(user, reqUser.getId());
	
		return updatedUser;
	}
	
//	(@PathVariable("userId") Integer id) not used as (@PathVariable Integer userId) is used
	@PutMapping("/api/users/follow/{userId2}")
	public User followUserHandler(@RequestHeader("Authorization")String jwt,@PathVariable Integer userId2 ) throws Exception {
		User reqUser=userService.findUserByJwt(jwt);
		User user=userService.followUser(reqUser.getId(), userId2);
		return user;
	}
	
	@GetMapping("/api/users/search")
	public List<User> searchUser(@RequestParam("query") String query){
		List<User> users=userService.searchUser(query);
		return users;
		
	}
	
	
	@GetMapping("/api/users/profile")
	public User getUserFromToken(@RequestHeader("Authorization")String jwt) {

		User user=userService.findUserByJwt(jwt);
		user.setPassword(null);
		return user;
	}
	
//	@DeleteMapping("/users/delete/{userId}")
//	public String deleteUser(@PathVariable Integer userId) {
//		Optional<User> user=userRepository.findById(userId);
//		if(user.isPresent()) {
//			userRepository.delete(user.get());
//			return "deleted";
//		}else {
//			return "not found";
//		}
//		
//	}
	
	
}
