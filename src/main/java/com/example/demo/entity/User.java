package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Component
@Table(name = "users")
public class User {
	//フィールド
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String tel;
	
	private String email;
	
	private String gender;
	
	private Integer age;
	
	private String password;
	
	//コンストラクタ
	public User() {
		
	}
	//コンストラクタ（登録用）
		public User(String name, String tel, String email, String gender, Integer age, String password) {
			this.name = name;
			this.tel = tel;
			this.email = email;
			this.gender = gender;
			this.age = age;
			this.password = password;
		}
		
	public User(Integer id, String name, String tel, String email, String gender, Integer age, String password) {
		super();
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.password = password;
	}
		
    
}
