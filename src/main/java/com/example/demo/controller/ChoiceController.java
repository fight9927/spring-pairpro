package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Food;
import com.example.demo.entity.History;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.HistoryRepository;

@Controller
public class ChoiceController {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Autowired
	FoodRepository foodRepository;
	
	// 商品一覧表示
		@GetMapping("/choice")
		public String index(
				@RequestParam(name="categoryId", required=false) Integer categoryId,
				Model m
		) {
			List<Food> foods = foodRepository.findByCategoryId(categoryId);
			
			m.addAttribute("foods",foods);
			
			return "choice";
		}
	//選択したらmainで表示
		@PostMapping("/choice/{id}")
		public String store(
				@PathVariable(value = "id") Integer id,
				@RequestParam(value = "name", defaultValue = "") String name,
				@RequestParam(value = "name", defaultValue = "") Integer userId,
				Model model) {
			
			Food food = foodRepository.findById(id).get();
			
			History history = new History(id, userId, name, food.getCarbohydrates(),food.getProtein(),food.getLipid(),food.getVitamin(),food.getMineral());
			
			 historyRepository.save(history);

			return "redirect:/main";
		}
		
}
