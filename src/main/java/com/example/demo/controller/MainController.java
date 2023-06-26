package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	User user;
	
	@PostMapping("/login")
	public String login(
	@RequestParam(value = "name", defaultValue = "") String name,
	@RequestParam(value = "email", defaultValue = "") String email,
	@RequestParam(value = "password", defaultValue = "") String password,
	Model model) {
		
		Optional<User> user = userRepository.findByNameAndEmailAndPassword(name,email,password);
		
		return "/main";
	}
	
	
}
