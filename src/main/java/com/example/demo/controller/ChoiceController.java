package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Food;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;

@Controller
public class ChoiceController {
	
	@Autowired
	CategoryRepository categoryRepository;

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
		
}
