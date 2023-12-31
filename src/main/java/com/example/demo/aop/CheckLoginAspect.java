package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Account;

@Aspect
@Component
public class CheckLoginAspect {

	@Autowired
	Account account;

	// ログ出力処理
	// 全Controllerクラスの全メソッド処理前を指定
	@Before("execution(* com.example.demo.controller.*Controller.*(..))")
	public void writeLog(JoinPoint jp) {
		// ログインしたアカウント情報を取得
		if (account == null || account.getName() == null
				|| account.getName().length() == 0) {
			System.out.print("ゲスト：");
		} else {
			System.out.print(account.getName() + "：");
		}
		System.out.println(jp.getSignature());
	}

	// 未ログインの場合ログインページにリダイレクト
	@Around("execution(* com.example.demo.controller.ChoiceController.*(..)) ||"
			+ "execution(* com.example.demo.controller.HistoryController.*(..)) ||"
			+ "execution(* com.example.demo.controller.MenuController.*(..)) ||"
			+ "execution(* com.example.demo.controller.RecommendController.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {

		if (account == null || account.getName() == null
				|| account.getName().length() == 0) {
			System.err.println("ログインしていません!");
			// リダイレクト先を指定する
			// パラメータを渡すことでログインControllerで
			// 個別のメッセージをThymeleafに渡すことも可能
			return "redirect:/?error=notLoggedIn";
		}
		// Controller内のメソッドの実行
		return jp.proceed();
	}
}

//献立の写真　全体のデザイン


