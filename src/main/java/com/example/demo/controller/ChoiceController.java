package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
				@RequestParam(name="page", required=false) Integer page,
				Model model
		) {
			
			Integer size = 10; //１ページに表示できるレコード数
			
			categoryId = categoryId == null ? 0 : categoryId;
			page = page == null ? 0 : page; //ページ番号　最初「0」
			
			// リクエストパラメータを基にPageableを作成
		    Pageable pageable = Pageable.ofSize(size).withPage(page);
		    
		    List<Food> records = foodRepository.findByCategoryId(categoryId);
		 
		    Page<Food> foodPage = foodRepository.findByCategoryId(categoryId, pageable);
		    List<Food> foods = foodPage.getContent();
		    
		    //ページネーション計算
		    Integer recordMax = records.size();
		    
			Integer max = recordMax / size;

			if (recordMax == 0 || recordMax % size != 0) {
				max++;
			}
		  
			model.addAttribute("pagination", max);
			model.addAttribute("page", page);
			
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
			
			History history = new History(userId, food.getName(), food.getKcal(), food.getCarbohydrates(),food.getProtein(),food.getLipid(),food.getVitamin(),food.getMineral(), LocalDate.now());
			
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
