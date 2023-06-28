package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Food;
import com.example.demo.entity.History;
import com.example.demo.model.Account;
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
	
	@Autowired
	Account account;
	
	// 商品一覧表示
		@GetMapping("/choice")
		public String index(
				@RequestParam(name="categoryId", required=false) Integer categoryId,
				@RequestParam(name="keyword", defaultValue = "") String keyword,
				Model model
		) {
			List<Food> foods = foodRepository.findByCategoryId(categoryId);
			
			model.addAttribute("keyword", keyword);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("foods",foods);
			
			return "choice";
		}

	//商品追加してmainで表示
		@GetMapping("/choice/regist")
		public String store(
				@RequestParam(value = "foodId") Integer foodId,
				Model model) {
			
			Food food = foodRepository.findById(foodId).get();
			Integer userId = account.getId();
			
			History history = new History(userId, food.getName(), food.getCarbohydrates(),food.getProtein(),food.getLipid(),food.getVitamin(),food.getMineral());
			
			historyRepository.save(history);

			return "redirect:/main";
		}

	//検索機能
		@GetMapping("/choice/search")
		public String search(
				@RequestParam(name = "keyword", defaultValue = "") String keyword,
				@RequestParam(name = "categoryId") Integer categoryId,
				Model model) {
			
			List<Food> foods = null;
			
			if (keyword.equals("")) {
				foods = foodRepository.findByCategoryId(categoryId);
			} else {
				foods = foodRepository.findByCategoryIdAndNameLike(categoryId, "%" + keyword + "%");
			}

			model.addAttribute("foods", foods);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("keyword", keyword);
			//表示できるものはありませんメッセージを作る
			
			return "choice";
		}
}
