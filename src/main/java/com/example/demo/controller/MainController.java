package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.History;
import com.example.demo.entity.Intake;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.model.IntakeGoal;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IntakeRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired 
	HistoryRepository historyRepository;
	
	@Autowired 
	IntakeRepository intakeRepository;
	
	@Autowired
	Account account;
	
	@Autowired
	IntakeGoal intakeGoal;
	
	//ログイン画面表示
		@GetMapping("/")
		public String index() {
			session.invalidate();
			
			return "/login";
		}
		
	//ログイン管理
	@PostMapping("/login")
	public String login(
	@RequestParam(value = "email", defaultValue = "") String email,
	@RequestParam(value = "password", defaultValue = "") String password,
	Model model) {
		
		if (email.equals("") && password.equals("")) {
			model.addAttribute("message", "メールアドレスとパスワードを入力してください");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			
			return "/login";
		} else if (email.equals("")) {
			model.addAttribute("message", "メールアドレスを入力してください");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			
			return "/login";
		} else if (password.equals("")) {
			model.addAttribute("message", "パスワードを入力してください");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			
			return "/login";
		}
		
		Optional<User> user = userRepository.findByEmailAndPassword(email, password);
		if(user.isEmpty() == false) {
			User u = user.get();
			//ユーザのセッション登録
			account.setName(u.getName());
			account.setId(u.getId());
			
			Intake intake = null;
			
			if (u.getAge() < 30 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(1).get();
			} else if (u.getAge() < 50 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(2).get();
			} else if (u.getAge() < 65 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(3).get();
			} else if (u.getAge() > 65 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(4).get();
			} else if (u.getAge() < 30 && u.getGender().equals("女性")) {
				intake = intakeRepository.findById(5).get();
			} else if (u.getAge() < 50 && u.getGender().equals("女性")) {
				intake = intakeRepository.findById(6).get();
			} else if (u.getAge() < 65 && u.getGender().equals("女性")) {
				intake = intakeRepository.findById(7).get();
			} else if (u.getAge() > 65 && u.getGender().equals("女性")) {
				intake = intakeRepository.findById(8).get();
			}
			//目安摂取量をセッション登録
			intakeGoal.setId(intake.getId());
			
			//カテゴリー一覧を取得
			List<Category> categoriesList = categoryRepository.findAll();
			model.addAttribute("categories", categoriesList);
			

			List<History> histories = historyRepository.findByUserId(u.getId());
			
			List<History> historiesList = new ArrayList<>();
			
			//その日食べたものを取得
			for (History history : histories) {
				if (LocalDate.now().isEqual(history.getDay())) {
					historiesList.add(history);
				}
			}
			
			
			model.addAttribute("histories", historiesList);
			
			//Historyの各栄養素を足し合わせる
			int totalCarbohydrates = 0;
			int totalProtein = 0;
			int totalLipid = 0;
			int totalVitamin = 0;
			int totalMineral = 0;
			
			for (History history : historiesList) {
				
				//その日に摂取した各栄養素の合計値
					totalCarbohydrates += history.getCarbohydrates();
					totalProtein += history.getProtein();
					totalLipid += history.getLipid();
					totalVitamin += history.getVitamin();
					totalMineral += history.getMineral();
				}
			
			model.addAttribute("total1", totalCarbohydrates);
			model.addAttribute("total2", totalProtein);
			model.addAttribute("total3", totalLipid);
			model.addAttribute("total4", totalVitamin);
			model.addAttribute("total5", totalMineral);
			
			//目安摂取量-摂取した合計値　足りない要素を表示したいため
			Integer[] gap = {intake.getCarbohydrates() - totalCarbohydrates,
					         intake.getProtein() - totalProtein,
					         intake.getLipid() - totalLipid,
					         intake.getVitamin() - totalVitamin,
					         intake.getMineral() - totalMineral};
			
			model.addAttribute("gap", gap);
			
			//FileOutputStream fos = null;
			
			try {
			    // 日本語が文字化けしないテーマ
			    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
			    
			    // グラフデータを設定する
			    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			    
			    //dataset.addValue(100, "炭水化物", "炭水化物(目標)");
			    dataset.addValue((totalCarbohydrates / intake.getCarbohydrates()) * 100, "炭水化物", "炭水化物");
			    //dataset.addValue(100, "プロテイン", "プロテイン(目標)");
			    dataset.addValue((totalProtein / intake.getProtein()) * 100, "プロテイン", "プロテイン");
			    //dataset.addValue(100, "脂質", "脂質(目標)");
			    dataset.addValue((totalLipid / intake.getLipid()) * 100, "脂質", "脂質");
			    //dataset.addValue(100, "ビタミン", "ビタミン(目標)");
			    dataset.addValue((totalVitamin / intake.getVitamin()) * 100, "ビタミン", "ビタミン");
			    //dataset.addValue(100, "ミネラル", "ミネラル(目標)");
			    dataset.addValue((totalMineral / intake.getMineral()) * 100, "ミネラル", "ミネラル");
			    
			    // グラフを生成する
			    JFreeChart chart = ChartFactory.createBarChart("", "栄養素", "摂取量割合(%)", dataset);
			    
			 // 背景色を設定
			    chart.setBackgroundPaint(ChartColor.WHITE);

			    // ファイルへ出力する
			    //fos = new FileOutputStream(this.getClass().getSimpleName() + ".jpg");
			    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			    ChartUtilities.writeChartAsPNG(byteArrayOutputStream, chart, 600, 400);
			    String base64string = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
			    String dataUri = "data:image/png;base64," + base64string;
			    model.addAttribute("dataUri", dataUri); 
			    
			} catch (IOException e) {
			    // エラー処理
			}     
			
				return "main";
				
		}
		
       	model.addAttribute("message", "登録情報と異なります");
		
		return "login";
	}
	
	
}
