package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class AccountController {
	@Autowired
	private UserRepository userRepository;
	
	//ログイン画面表示
	@GetMapping("/")
	public String index() {
		return "/login";
	}
	
	//会員登録
	@GetMapping("/account/add")
	public String create() {
		return "/accountform";
	}
	@PostMapping("/users/add")
	public String store(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "name", defaultValue = "") String tel,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "name", defaultValue = "") String gender,
			@RequestParam(value = "name", defaultValue = "") Integer age,
			@RequestParam(value = "password", defaultValue = "") String password,
			Model model) {

		User user = new User(name, tel, email,gender,age, password);
		
		userRepository.save(user);

		return "redirect:/login";
	}
}