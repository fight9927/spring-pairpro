package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Component
@Table(name = "foods")
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	private String name;
	private Integer kcal;
	private Integer carbohydrates;
	private Integer protein;
	private Integer lipid;
	private Integer vitamin;
	private Integer mineral;
	
	//コンストラクタ
		public Food() {
			
		}
	//コンストラクタ（登録用）
		public Food(Integer categoryId, String name, Integer kcal, Integer carbohydrates,Integer protein,Integer lipid, Integer vitamin,Integer mineral) {
			this.categoryId = categoryId;
			this.name = name;
			this.kcal = kcal;
			this.carbohydrates = carbohydrates;
			this.protein = protein;
			this.lipid = lipid;
			this.vitamin = vitamin;
			this.mineral = mineral;
			}
}
