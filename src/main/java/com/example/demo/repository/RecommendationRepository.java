package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository <Recommendation, Integer> {
	
	List<Recommendation> findByGenreAndTypeAndSort(Integer genre, Integer type, Integer sort);
}
