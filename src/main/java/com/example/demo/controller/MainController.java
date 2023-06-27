package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.History;
import com.example.demo.entity.Intake;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.model.IntakeGoal;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IntakeRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired 
	HistoryRepository historyRepository;
	
	@Autowired 
	IntakeRepository intakeRepository;
	
	@Autowired
	Account account;
	
	@Autowired
	IntakeGoal intakeGoal;
	
	//ログイン画面表示
		@GetMapping("/")
		public String index() {
			session.invalidate();
			
			return "/login";
		}
		
	//ログイン管理
	@PostMapping("/login")
	public String login(
	@RequestParam(value = "email", defaultValue = "") String email,
	@RequestParam(value = "password", defaultValue = "") String password,
	Model model) {
		
		if (email.equals("") && password.equals("")) {
			model.addAttribute("message", "メールアドレスとパスワードを入力してください");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			
			return "/login";
		} else if (email.equals("")) {
			model.addAttribute("message", "メールアドレスを入力してください");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			
			return "/login";
		} else if (password.equals("")) {
			model.addAttribute("message", "パスワードを入力してください");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			
			return "/login";
		}
		
		Optional<User> user = userRepository.findByEmailAndPassword(email, password);
		if(user.isEmpty() == false) {
			User u = user.get();
			//ユーザのセッション登録
			account.setName(u.getName());
			
			Intake intake = null;
			
			if (u.getAge() < 30 && u.getGender() == "男性") {
				intake = intakeRepository.findById(1).get();
			} else if (u.getAge() < 50 && u.getGender() == "男性") {
				intake = intakeRepository.findById(2).get();
			} else if (u.getAge() < 65 && u.getGender() == "男性") {
				intake = intakeRepository.findById(3).get();
			} else if (u.getAge() > 65 && u.getGender() == "男性") {
				intake = intakeRepository.findById(4).get();
			} else if (u.getAge() < 30 && u.getGender() == "女性") {
				intake = intakeRepository.findById(5).get();
			} else if (u.getAge() < 50 && u.getGender() == "女性") {
				intake = intakeRepository.findById(6).get();
			} else if (u.getAge() < 65 && u.getGender() == "女性") {
				intake = intakeRepository.findById(7).get();
			} else if (u.getAge() > 65 && u.getGender() == "女性") {
				intake = intakeRepository.findById(8).get();
			}
			//目安摂取量をセッション登録
			intakeGoal.setId(intake.getId());
			
			List<Category> categoriesList = categoryRepository.findAll();
			model.addAttribute("categories", categoriesList);
			
			List<History> historiesList = historyRepository.findAll();
			model.addAttribute("histories", historiesList);
			
			//Historyの各要素を足し合わせる
			
			
			//明日やること：摂取量と目標摂取量の差を表に映し出す
			//食べたものがHistoryに追加されて、メイン画面に表示される
//			Integer[] gap = {intake.getCarbohydrates(),
//					         intake.getProtein(),
//					         intake.getLipid(),
//					         intake.getVitamin(),
//					         intake.getMineral()};
			
			return "main";
		}
		
       	model.addAttribute("message", "登録情報と異なります");
		
		return "login";
	}
	
	
}
