package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	User user;
	
	//ログイン画面表示
	@GetMapping("/")
	public String index() {
		session.invalidate();
		
		return "/login";
	}
	
	//会員登録
	@GetMapping("/account")
	public String create() {
		return "/accountform";
	}
	@PostMapping("/account")
	public String store(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "tel", defaultValue = "") String tel,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "gender", defaultValue = "") String gender,
			@RequestParam(value = "age", defaultValue = "") Integer age,
			@RequestParam(value = "password", defaultValue = "") String password,
			Model model) {

		User user = new User(name, tel, email, gender, age, password);
		
		userRepository.save(user);

		return "redirect:/";
	}
}