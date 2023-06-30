package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Recommendation;
import com.example.demo.repository.RecommendationRepository;

@Controller
public class RecommendController {
	
	@Autowired
	RecommendationRepository recommendationRepository;
	
	//おすすめページへ
	@GetMapping("/recommend")
	public String index() {
		return "recommend";
	}
	
	//おすすめメニュー提案
	@PostMapping("/recommend")
	public String recommend(
			@RequestParam(name = "genre") Integer genre,
			@RequestParam(name = "type") Integer type,
			@RequestParam(name = "sort") Integer sort,
			Model model) {
		
		List<Recommendation> recommends = recommendationRepository.findByGenreAndTypeAndSort(genre, type, sort);
		model.addAttribute("recommends", recommends);
		
		return "showRecommend";
	}
}
