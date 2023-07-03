package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "records")
public class History {
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;
	
	private String name;
	private Integer kcal;
	private Integer carbohydrates;
	private Integer protein;
	private Integer lipid;
	private Integer vitamin;
	private Integer mineral;
	private LocalDate day;
	
	public History() {
		
	}
	
	//ヒストリー登録	
	public History(Integer userId, String name, Integer kcal, Integer carbohydrates, Integer protein, Integer lipid, Integer vitamin, Integer mineral, LocalDate day) {
	this.userId = userId;
	this.name = name;	
	this.kcal = kcal;
	this.carbohydrates = carbohydrates;
	this.protein = protein;
	this.lipid = lipid;
	this.vitamin = vitamin;
	this.mineral = mineral;
	this.day = day;
	}
}
