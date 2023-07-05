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
	
	private Integer birthyear;
	private Integer birthmonth;
	private Integer birthday;
	
	private String password;
	
	//コンストラクタ
	public User() {
		
	}

	public User(String name, String tel, String email, String gender, Integer birthyear, Integer birthmonth,
			Integer birthday, String password) {
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.gender = gender;
		this.birthyear = birthyear;
		this.birthmonth = birthmonth;
		this.birthday = birthday;
		this.password = password;
	}

	public User(Integer id, String name, String tel, String email, String gender, Integer birthyear, Integer birthmonth,
			Integer birthday, String password) {
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.gender = gender;
		this.birthyear = birthyear;
		this.birthmonth = birthmonth;
		this.birthday = birthday;
		this.password = password;
	}
	
		
    
}
