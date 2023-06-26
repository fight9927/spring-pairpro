package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@Data
@SessionScope
public class IntakeGoal {
	
	private Integer carbohydrates;
	private Integer protein;
	private Integer lipid;
	private Integer vitamin;
	private Integer mineral;
	
}
