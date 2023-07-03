package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Food;
import com.example.demo.entity.History;
import com.example.demo.entity.Intake;
import com.example.demo.model.Account;
import com.example.demo.model.IntakeGoal;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IntakeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MenuController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Autowired
	IntakeRepository intakeRepository;
	
    @Autowired
	IntakeGoal intakeGoal;
	
	@Autowired
	Account account;
	
	//メイン画面表示
	@GetMapping("/main")
	public String index(Model model) {
		
		if (account.getName() == null) {
			
			return "login";
		}
		
		Integer userId = account.getId();
		
		Intake intake = intakeRepository.findById(intakeGoal.getId()).get();
		
		List<Category> categoriesList = categoryRepository.findAll();
		model.addAttribute("categories", categoriesList);
		
		List<History> histories = historyRepository.findByUserId(userId);
		
		List<History> historiesList = new ArrayList<>();
		
		//その日食べたものを取得
		for (History history : histories) {
			if (LocalDate.now().isEqual(history.getDay())) {
				historiesList.add(history);
			}
		}
		
		model.addAttribute("histories", historiesList);
		
		//Historyの各栄養素を足し合わせる
		int totalCarbohydrates = 0;
		int totalProtein = 0;
		int totalLipid = 0;
		int totalVitamin = 0;
		int totalMineral = 0;
		
		for (History history : historiesList) {
			
				totalCarbohydrates += history.getCarbohydrates();
				totalProtein += history.getProtein();
				totalLipid += history.getLipid();
				totalVitamin += history.getVitamin();
				totalMineral += history.getMineral();
			}
		
		model.addAttribute("total1", totalCarbohydrates);
		model.addAttribute("total2", totalProtein);
		model.addAttribute("total3", totalLipid);
		model.addAttribute("total4", totalVitamin);
		model.addAttribute("total5", totalMineral);
		
		
		Integer[] gap = {intake.getCarbohydrates() - totalCarbohydrates,
				         intake.getProtein() - totalProtein,
				         intake.getLipid() - totalLipid,
				         intake.getVitamin() - totalVitamin,
				         intake.getMineral() - totalMineral};
		
		
		model.addAttribute("gap", gap);
		
		return "main";
	}
	 
	    //メニュー追加画面表示
		@GetMapping("/menu/add")
		public String index() {
			
			return "/menu";
		}
		
		//メニュー登録
		@PostMapping("/menu/add")
		public String store(
				@RequestParam(value = "name", defaultValue = "") String name,
				@RequestParam(value = "carbohydrates", defaultValue = "") String carbohydrates,
				@RequestParam(value = "protein", defaultValue = "") String protein,
				@RequestParam(value = "lipid", defaultValue = "")  String lipid,
				@RequestParam(value = "vitamin", defaultValue = "") String vitamin,
				@RequestParam(value = "mineral", defaultValue = "") String mineral,
				Model model) {
			 
			boolean res = true;
			
			String message1 = "";
			String message2 = "";
			
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
			
			//名前が正しく入力されているか判定
			String pattern = "^[0-9]$";
			Pattern p1 = Pattern.compile(pattern);
			
			if (!(p1.matcher(carbohydrates).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
			
            if (!(p1.matcher(protein).find())) {
 				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(p1.matcher(lipid).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(p1.matcher(vitamin).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(p1.matcher(mineral).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(message1 == "") || !(message2 == "")) {
            	
            	model.addAttribute("message1", message1);
            	model.addAttribute("message2", message2);
            	model.addAttribute("name", name);
            	model.addAttribute("carbohydrates", carbohydrates);
            	model.addAttribute("protein", protein);
            	model.addAttribute("lipid", lipid);
            	model.addAttribute("vitamin", vitamin);
            	model.addAttribute("mineral", mineral);
            	
            	return "menu";
            }

			Food food = new Food(4, name, Integer.parseInt(carbohydrates), Integer.parseInt(protein), Integer.parseInt(lipid), Integer.parseInt(vitamin), Integer.parseInt(mineral));
			
			 foodRepository.save(food);

			return "redirect:/main";
		}
}
