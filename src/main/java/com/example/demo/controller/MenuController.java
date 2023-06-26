package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Food;
import com.example.demo.repository.FoodRepository;

@Controller
public class MenuController {
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	Food foood;
	
	//メニュー追加画面表示
		@GetMapping("/menu")
		public String index() {
			return "/menu";
		}
		//メニュー登録
		@GetMapping("|/menu/${categoryId}|")
		public String create() {
			return "/menu";
		}
		@PostMapping("|/menu/${categoryId}|")
		public String store(
				@PathVariable(value = "categoryID")Integer categoryId,
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
