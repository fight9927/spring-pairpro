package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Food;
import com.example.demo.entity.History;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.HistoryRepository;

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
	Food food;
	
	@Autowired
	Account account;
	
	@GetMapping("/main")
	public String index(Model model) {
		
		if (account.getName() == null) {
			
			return "login";
		}
		
		List<Category> categoriesList = categoryRepository.findAll();
		model.addAttribute("categories", categoriesList);
		
		List<History> historiesList = historyRepository.findAll();
		model.addAttribute("histories", historiesList);
		
		return "main";
	}
	 
	    //メニュー追加画面表示
		@GetMapping("/menu/add")
		public String index(
				@RequestParam(name = "categoryId") Integer categoryId,
		Model model) {
			model.addAttribute("categoryId", categoryId);
			
			return "/menu";
		}

		@PostMapping("/menu/add/{categoryId}")
		public String store(
				@PathVariable(value = "categoryId")Integer categoryId,
				@RequestParam(value = "name", defaultValue = "") String name,
				@RequestParam(value = "carbohydrates", defaultValue = "") Integer carbohydrates,
				@RequestParam(value = "protein", defaultValue = "") Integer protein,
				@RequestParam(value = "lipid", defaultValue = "")  Integer lipid,
				@RequestParam(value = "vitamin", defaultValue = "") Integer vitamin,
				@RequestParam(value = "mineral", defaultValue = "") Integer mineral,
				Model model) {

			Food food = new Food(categoryId, name, carbohydrates, protein, lipid, vitamin, mineral);
			
			 foodRepository.save(food);

			return "redirect:/main";
		}
}
