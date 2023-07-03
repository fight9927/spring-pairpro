package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "intake")
public class Intake {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String range;
	private String gender;
	private Integer kcal;
	private Integer carbohydrates;
	private Integer protein;
	private Integer lipid;
	private Integer vitamin;
	private Integer mineral;
	
	public Intake() {
		
	}
	
	public Intake(Integer id, String range, String gender, Integer kcal, Integer carbohydrates, Integer protein, Integer lipid,
			Integer vitamin, Integer mineral) {
		this.id = id;
		this.range = range;
		this.gender = gender;
		this.kcal = kcal;
		this.carbohydrates = carbohydrates;
		this.protein = protein;
		this.lipid = lipid;
		this.vitamin = vitamin;
		this.mineral = mineral;
	}
	
	
}
