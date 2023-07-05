package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository <User, Integer> {
	Optional<User> findByEmailAndPassword(String email,String password);
	
	//日付ごとにグループ化してそれを表示させたいんじゃ！！
//	@Query(
//			value = "SELECT SUBSTRING(age, 1, 4) FROM users;",
//			nativeQuery=true)
//	Integer find01(int age);
}
