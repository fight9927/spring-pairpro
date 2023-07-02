package com.example.demo.controller;

import java.util.regex.Pattern;

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
			@RequestParam(value = "gender") String gender,
			@RequestParam(value = "ageYear") Integer ageYear,
			@RequestParam(value = "ageMonth") Integer ageMonth,
			@RequestParam(value = "ageDay") Integer ageDay,
			@RequestParam(value = "password1", defaultValue = "") String password1,
			@RequestParam(value = "password2", defaultValue = "") String password2,
			Model model) {
		
		//正しく入力されているのかを確認
		boolean res = true;
		
		String message1 = "";
		String message2 = "";
		String message3 = "";
		String message4 = "";
		String message5 = "";
		String message6 = "";
		
		//String[] messages = {message1, message2, message3, message4, message5, message6};
		
		//名前が正しく入力されているか判定
		for (int i = 0; i < name.length(); i++) {
			
			if (Character.isLetter(name.charAt(i))) {
				continue;
			} else {
				res = false;
				break;
			}
		}
		
		if (res == false) {
			message1 = "文字のみで入力してください";
		}
		
		//電話番号が数字で構成されているか判定
		String pattern = "^0[789]0\\d{4}\\d{4}$";
		Pattern p1 = Pattern.compile(pattern);
		
		if (!(p1.matcher(tel).find())) {
			
			message2 = "正しく入力して下さい";
		}
		
		//メールが正しく入力されているか判定(****@**.com)
		Pattern p2 = Pattern.compile("^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$");
		
		if (!(p2.matcher(email).find())) {
			
			message3 = "正しいメールアドレスではありません";
		}
		
		//年齢が正しく入力されているか判定
		if (ageYear == 0 || ageMonth == 0 || ageDay == 0) {
			message4 = "生年月日を選択してください";
		}
		
		//パスワードが正しいか判定
		if (password1.equals(password2)) {
			
			Pattern p5 = Pattern.compile("^[a-zA-Z0-9]{8,12}$");
			
			if (!(p5.matcher(password1).find())) {
				
				message5 = "8~12字の半角英数字で入力してください";
			}
		} else {
			message6 = "確認用パスワードが異なります";
		}
		
		//1つでもmessageがnullでない場合は登録画面に戻る
		if (!(message1.equals("")) || !(message2.equals("")) || !(message3.equals("")) || 
				!(message4.equals("")) || !(message5.equals("")) || !(message6.equals(""))) {
			
			model.addAttribute("message1", message1);
			model.addAttribute("message2", message2);
			model.addAttribute("message3", message3);
			model.addAttribute("message4", message4);
			model.addAttribute("message5", message5);
			model.addAttribute("message6", message6);
			
			model.addAttribute("name", name);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			model.addAttribute("ageYear", ageYear);
			model.addAttribute("ageMonth", ageMonth);
			model.addAttribute("ageDay", ageDay);
			model.addAttribute("password1", password1);
			model.addAttribute("password2", password2);
			
			return "accountForm";
		}
		
		User user = new User(name, tel, email, gender, ageYear + ageMonth + ageDay, password1);
		
		userRepository.save(user);

		return "redirect:/";
	}
}