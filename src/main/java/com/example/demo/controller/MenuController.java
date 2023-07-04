package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

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
import com.example.demo.entity.Food;
import com.example.demo.entity.History;
import com.example.demo.entity.Intake;
import com.example.demo.model.Account;
import com.example.demo.model.IntakeGoal;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IntakeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MenuController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Autowired
	IntakeRepository intakeRepository;
	
    @Autowired
	IntakeGoal intakeGoal;
	
	@Autowired
	Account account;
	
	//メイン画面表示
	@GetMapping("/main")
	public String index(Model model, ModelMap modelMap) {
		
		if (account.getName() == null) {
			
			return "login";
		}
		
		Integer userId = account.getId();
		
		Intake intake = intakeRepository.findById(intakeGoal.getId()).get();
		
		List<Category> categoriesList = categoryRepository.findAll();
		model.addAttribute("categories", categoriesList);
		
		List<History> histories = historyRepository.findByUserId(userId);
		
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
	    dataset.addValue(tp * 100 / ip, "プロテイン", "プロテイン");
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
            ChartUtilities.writeChartAsPNG(byteArrayOutputStream, chart, 600, 400); // チャートをPNG画像として出力
            String base64string = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()); // 画像をBase64でエンコード
            String dataUri = "data:image/png;base64," + base64string; // data URIの文字列を作成
            modelMap.addAttribute("dataUri", dataUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "main";
    }  
	 
	    //メニュー追加画面表示
		@GetMapping("/menu/add")
		public String index() {
			
			return "/menu";
		}
		
		//メニュー登録
		@PostMapping("/menu/add")
		public String store(
				@RequestParam(value = "name") String name,
				@RequestParam(value = "kcal") String kcal,
				@RequestParam(value = "carbohydrates") String carbohydrates,
				@RequestParam(value = "protein") String protein,
				@RequestParam(value = "lipid")  String lipid,
				@RequestParam(value = "vitamin") String vitamin,
				@RequestParam(value = "mineral") String mineral,
				Model model) {
			 
			boolean res = true;
			
			String message1 = "";
			String message2 = "";
			
			//名前が正しく入力されているか判定
			for (int i = 0; i < name.length(); i++) {
				
				if (Character.isLetter(name.charAt(i))) {
					continue;
				} else {
					res = false;
					break;
				}
			} 
			
			if (res == false) {
				message1 = "文字のみで入力してください";
			}
			
			//名前が正しく入力されているか判定
			String pattern = "^[0-9]$";
			Pattern p1 = Pattern.compile(pattern);
			
            if (!(p1.matcher(kcal).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
			
			if (!(p1.matcher(carbohydrates).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
			
            if (!(p1.matcher(protein).find())) {
 				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(p1.matcher(lipid).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(p1.matcher(vitamin).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(p1.matcher(mineral).find())) {
				
				message2 = "栄養素は数字のみで入力してください";
			}
            
            if (!(message1 == "") || !(message2 == "")) {
            	
            	model.addAttribute("message1", message1);
            	model.addAttribute("message2", message2);
            	model.addAttribute("name", name);
            	model.addAttribute("kcal", kcal);
            	model.addAttribute("carbohydrates", carbohydrates);
            	model.addAttribute("protein", protein);
            	model.addAttribute("lipid", lipid);
            	model.addAttribute("vitamin", vitamin);
            	model.addAttribute("mineral", mineral);
            	
            	return "menu";
            }

			Food food = new Food(4, name, Integer.parseInt(kcal), Integer.parseInt(carbohydrates), Integer.parseInt(protein), Integer.parseInt(lipid), Integer.parseInt(vitamin), Integer.parseInt(mineral));
			
			 foodRepository.save(food);

			return "redirect:/main";
		}
}
