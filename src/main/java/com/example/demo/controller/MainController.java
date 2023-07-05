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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
	ModelMap modelMap, Model model) {
		
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
			
			//今の西暦を取得
			int year = LocalDate.now().getYear();
			
			if (year - u.getBirthyear() < 30 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(1).get();
			} else if (year - u.getBirthyear() < 50 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(2).get();
			} else if (year - u.getBirthyear() < 65 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(3).get();
			} else if (year - u.getBirthyear() > 65 && u.getGender().equals("男性")) {
				intake = intakeRepository.findById(4).get();
			} else if (year - u.getBirthyear() < 30 && u.getGender().equals("女性")) {
				intake = intakeRepository.findById(5).get();
			} else if (year - u.getBirthyear() < 50 && u.getGender().equals("女性")) {
				intake = intakeRepository.findById(6).get();
			} else if (year - u.getBirthyear() < 65 && u.getGender().equals("女性")) {
				intake = intakeRepository.findById(7).get();
			} else if (year - u.getBirthyear() > 65 && u.getGender().equals("女性")) {
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
			int totalKcal = 0;
			int totalCarbohydrates = 0;
			int totalProtein = 0;
			int totalLipid = 0;
			int totalVitamin = 0;
			int totalMineral = 0;
			
			for (History history : historiesList) {
				
				//その日に摂取した各栄養素の合計値
				    totalKcal += history.getKcal();
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
			
			//コメント欄
			int tk = totalKcal;
			int tc = totalCarbohydrates;
			int tp = totalProtein;
			int tl = totalLipid;
			int tv = totalVitamin;
			int tm = totalMineral;
			
			int ik = intake.getKcal();
			int ic = intake.getCarbohydrates();
			int ip = intake.getProtein();
			int il = intake.getLipid();
			int iv = intake.getVitamin();
			int im = intake.getMineral();
			
			String comment1 = "";
			String comment2 = "";
			String comment3 = "";
			String comment4 = "";
			String comment5 = "";
			String comment6 = "";
			
			//目標摂取量の3割以下の場合のコメント
			if (3 * tk < ik) {
				 comment1 = "食事量が足りません！";
				 comment2 = "もっとたくさん食べましょう！！";
				 comment3 = "メニューバーからおすすめの献立検索できるよ！！！";
				 
				 String[] comments = {comment1, comment2, comment3};
				 model.addAttribute("comments", comments);
				
				 //目標摂取量の3割から6割の場合のコメント
			} else if (3 * tk < 2 * ik) {
				comment1 = "1日に必要な食事量の約半分は食べました！";
				
				if (tc / tk > 0.65) {
					comment2 = "炭水化物";
				} 
				if (ip < 3 * tp && 3 * tp < 2 * ip) {
					comment3 = "タンパク質";
				}
				if (tl / il > 0.3) {
					comment4 = "脂質";
				}
				if (iv < 3 * tv && 3 * tv < 2 * iv) {
					comment5 = "ビタミン";
				}
				if (im < 3 * tm && 3 * tm < 2 * im) {
					comment6 = "ミネラル";
				}
				
                model.addAttribute("comment1", comment1);
				
				if (!(comment3.equals("")) && !(comment5.equals("")) && !(comment6.equals(""))) {
					model.addAttribute("comment2", comment3 + "、" + comment5 + "、" + comment6 + "はバランスよく摂取できています！！");
				} else if (!(comment3.equals("")) && !(comment5.equals(""))) {
					model.addAttribute("comment2", comment3 + "、" + comment5 + "はバランスよく摂取できています！！");
				} else if (!(comment5.equals("")) && !(comment6.equals(""))) {
					model.addAttribute("comment2", comment5 + "、" + comment6 + "はバランスよく摂取できています！！");
				} else if (!(comment6.equals("")) && !(comment3.equals(""))) {
					model.addAttribute("comment2", comment3 + "、" + comment6 + "はバランスよく摂取できています！！");
				} else if (!(comment3.equals(""))) {
					model.addAttribute("comment2", comment3 + "はバランスよく摂取できています！！");
				} else if (!(comment5.equals(""))) {
					model.addAttribute("comment2", comment5 + "はバランスよく摂取できています！！");
				} else if (!(comment6.equals(""))) {
					model.addAttribute("comment2", comment6 + "はバランスよく摂取できています！！");
				}

				
				if (!(comment2.equals("")) && !(comment4.equals(""))) {
					model.addAttribute("comment3", "ただ、、" + comment2 + "と" + comment4 + "は摂取しすぎです...");
				} else if (!(comment2.equals(""))) {
					model.addAttribute("comment3", "ただ、、" + comment2 + "は摂取しすぎです...");
				} else if (!(comment4.equals(""))) {
					model.addAttribute("comment3", "ただ、、" + comment4 + "は摂取しすぎです...");
				}
				
				//目標摂取量の6割以上の場合のコメント
			} else if (tk < ik) {
                comment1 = "しっかりと食事をとっていますね！";
				
				if (tc / tk > 0.65) {
					comment2 = "炭水化物";
				} 
				if (2 * ip < 3 * tp && tp < ip) {
					comment3 = "タンパク質";
				}
				if (tl / il > 0.3) {
					comment4 = "脂質";
				}
				if (2 * iv < 3 * tv && tv < iv) {
					comment5 = "ビタミン";
				}
				if (2 * im < 3 * tm && tm < im) {
					comment6 = "ミネラル";
				}
				
				model.addAttribute("comment1", comment1);
				
				if (!(comment3.equals("")) && !(comment5.equals("")) && !(comment6.equals(""))) {
					model.addAttribute("comment2", comment3 + "、" + comment5 + "、" + comment6 + "はバランスよく摂取できています！！");
				} else if (!(comment3.equals("")) && !(comment5.equals(""))) {
					model.addAttribute("comment2", comment3 + "、" + comment5 + "はバランスよく摂取できています！！");
				} else if (!(comment5.equals("")) && !(comment6.equals(""))) {
					model.addAttribute("comment2", comment5 + "、" + comment6 + "はバランスよく摂取できています！！");
				} else if (!(comment6.equals("")) && !(comment3.equals(""))) {
					model.addAttribute("comment2", comment3 + "、" + comment6 + "はバランスよく摂取できています！！");
				} else if (!(comment3.equals(""))) {
					model.addAttribute("comment2", comment3 + "はバランスよく摂取できています！！");
				} else if (!(comment5.equals(""))) {
					model.addAttribute("comment2", comment5 + "はバランスよく摂取できています！！");
				} else if (!(comment6.equals(""))) {
					model.addAttribute("comment2", comment6 + "はバランスよく摂取できています！！");
				}

				
				if (!(comment2.equals("")) && !(comment4.equals(""))) {
					model.addAttribute("comment3", "ただ、、" + comment2 + "と" + comment4 + "は摂取しすぎです...");
				} else if (!(comment2.equals(""))) {
					model.addAttribute("comment3", "ただ、、" + comment2 + "は摂取しすぎです...");
				} else if (!(comment4.equals(""))) {
					model.addAttribute("comment3", "ただ、、" + comment4 + "は摂取しすぎです...");
				}
				
			}
			
			    
			    // 摂取した栄養素値グラフを標示する
			ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
	        // データセットを作成
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        
	        // dataをデータセットに追加
		    dataset.addValue(tc * 100 / ic, "炭水化物", "炭水化物");
		    dataset.addValue(tp * 100 / ip, "タンパク質", "タンパク質");
		    dataset.addValue(tl * 100 / il, "脂質", "脂質");
		    dataset.addValue(tv * 100 / iv, "ビタミン", "ビタミン");
		    dataset.addValue(tm * 100 / im, "ミネラル", "ミネラル");
	        
	        // チャートを作成
	        JFreeChart chart = ChartFactory.createBarChart(
	        		"",
	        		"栄養素",
	        		"摂取量割合(%)",
	        		dataset,
	        		PlotOrientation.VERTICAL,
	                true,
	                false,
	                false
	        );
	        
	        //背景色の設定
	        chart.setBackgroundPaint(ChartColor.white);
	        
	        //縦軸の設定
	       // NumberAxis numberAxis = (NumberAxis)Plot.get
	       NumberAxis numberAxis = new NumberAxis();
	       numberAxis.setLowerBound(0.0);
	       numberAxis.setUpperBound(120.0);

	        try {
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 画像の出力先
	            //ChartUtils.writeChartAsPNG(byteArrayOutputStream, chart, 600, 400); // チャートをPNG画像として出力
	            ChartUtilities.writeChartAsPNG(byteArrayOutputStream, chart, 700, 500); // チャートをPNG画像として出力
	            String base64string = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()); // 画像をBase64でエンコード
	            String dataUri = "data:image/png;base64," + base64string; // data URIの文字列を作成
	            modelMap.addAttribute("dataUri", dataUri);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return "main";
	    }  
		
       	model.addAttribute("message", "登録情報と異なります");
		
		return "login";
	}
}
