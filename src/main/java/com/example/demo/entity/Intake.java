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
	
	private Integer carbohydrates;
	
	private Integer protein;
	
	private Integer lipid;
	
	private Integer vitamin;
	
	private Integer mineral;
}
