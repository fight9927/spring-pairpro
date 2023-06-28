package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.History;
import com.example.demo.model.Account;
import com.example.demo.repository.HistoryRepository;

@Controller
public class HistroyController {
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Autowired
	Account account;
	
	//日ごとに食べたものを表示
	@GetMapping("/history")
	public String index(Model model) {
		
		Integer userId = account.getId();
		
		List<History> historiesList = historyRepository.findByUserId(userId);
		
		
		
		
		return "";
	}
	
	@PostMapping("/history")
	public String show(
			@RequestParam(name = "") LocalDate day,
			Model model) {
		
		Integer userId = account.getId();
		
		List<History> historiesList = historyRepository.findByUserIdAndDay(userId, day);
		
		return "";
	}
}
