package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@Data
@SessionScope
public class Account {
	
	private String name;

	public Account(String name) {
		this.name = name;
	}
}
