package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Integer> {
	// SELECT * FROM items WHERE category_id = ?
		List<Food> findByCategoryId(Integer categoryId);
		
		List<Food> findByCategoryIdAndNameLike(Integer categoryId, String keyword);
		
		Page<Food> findByCategoryId(Integer categoryId, Pageable pageable);
}
