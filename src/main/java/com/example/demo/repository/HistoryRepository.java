package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {
	
	List<History> findByUserId(Integer userId);
	
	List<History> findByUserIdAndDay(Integer userId, LocalDate date);

}
