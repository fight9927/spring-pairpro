package com.example.demo.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
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
	
	@Autowired
	Account account;
	
	//会員登録
	@GetMapping("/account")
	public String create() {
		return "/accountform";
	}
	@PostMapping("/account")
	public String store(
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "tel") String tel,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "gender") String gender,
			@RequestParam(value = "birthyear") Integer birthyear,
			@RequestParam(value = "birthmonth") Integer birthmonth,
			@RequestParam(value = "birthday") Integer birthday,
			@RequestParam(value = "password1") String password1,
			@RequestParam(value = "password2") String password2,
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
		for (int i = 0; i < firstName.length(); i++) {
			
			if (Character.isLetter(firstName.charAt(i))) {
				continue;
			} else {
				res = false;
				break;
			}
		}
		
		for (int i = 0; i < lastName.length(); i++) {
			
			if (Character.isLetter(lastName.charAt(i))) {
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
			
			message2 = "正しい携帯番号ではありません";
		}
		
		//メールが正しく入力されているか判定(****@**.com)
		Pattern p2 = Pattern.compile("^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$");
		
		if (!(p2.matcher(email).find())) {
			
			message3 = "正しいメールアドレスではありません";
		}
		
		//年齢が正しく入力されているか判定
		if (birthyear == 0 || birthmonth == 0 || birthday == 0) {
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
			
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			model.addAttribute("birthyear", birthyear);
			model.addAttribute("birthmonth", birthmonth);
			model.addAttribute("birthday", birthday);
			model.addAttribute("password1", password1);
			model.addAttribute("password2", password2);
			
			return "accountForm";
		}
		
		User user = new User(lastName + firstName, tel, email, gender, birthyear, birthmonth, birthday, password1);
		
		userRepository.save(user);

		return "redirect:/";
	}
	
	@GetMapping("/account/edit")
	public String edit(Model model) {
		User user = userRepository.findById(account.getId()).get();
		model.addAttribute("user", user);
		
		return "editAccount";
	}
	
	//登録情報変更
	@PostMapping("/account/edit")
	public String store(
			@RequestParam(value = "tel") String tel,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "password1") String password1,
			@RequestParam(value = "password2") String password2,
			Model model) {
		
		//ユーザ情報を確保
		User user = userRepository.findById(account.getId()).get();
		
		//正しく入力されているのかを確認
		boolean res = true;
		
		String message2 = "";
		String message3 = "";
		String message5 = "";
		String message6 = "";
		
		//電話番号が数字で構成されているか判定
		String pattern = "^0[789]0\\d{4}\\d{4}$";
		Pattern p1 = Pattern.compile(pattern);
		
		if (!(p1.matcher(tel).find())) {
			
			message2 = "正しい携帯番号ではありません";
		}
		
		//メールが正しく入力されているか判定(****@**.com)
		Pattern p2 = Pattern.compile("^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$");
		
		if (!(p2.matcher(email).find())) {
			
			message3 = "正しいメールアドレスではありません";
		}
		
		//パスワードが正しいか判定
		if (user.getPassword().equals(password)) {
			
			if (password1.equals(password2)) {
				
				Pattern p5 = Pattern.compile("^[a-zA-Z0-9]{8,12}$");
				
				if (!(p5.matcher(password1).find())) {
					
					message5 = "8~12字の半角英数字で入力してください";
				}
			} else {
				message6 = "確認用パスワードが異なります";
			}
		}
		
		//1つでもmessageがnullでない場合は登録画面に戻る
		if (!(message2.equals("")) || !(message3.equals("")) || 
				!(message5.equals("")) || !(message6.equals(""))) {
			
			model.addAttribute("message2", message2);
			model.addAttribute("message3", message3);
			model.addAttribute("message5", message5);
			model.addAttribute("message6", message6);
			
			model.addAttribute("user", user);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			model.addAttribute("password1", password1);
			model.addAttribute("password2", password2);
			
			return "editAccount";
		}
		
		User editUser = new User(user.getId(), user.getName(), tel, email, user.getGender(), 
				user.getBirthyear(), user.getBirthmonth(), user.getBirthday(), password1);
		
		userRepository.save(editUser);

		return "redirect://";
	}
}