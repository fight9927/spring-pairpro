package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * チャート
 */
@Controller
@RequestMapping("/chart")
public class ChartController {

    /** 入力（フォーム） */
    @GetMapping("input")
    public String input() {
        return "input";
    }

    /** 処理（フォームから受け取り表示） */
    @PostMapping("process")
    public String process(@RequestParam("data1") String data1, @RequestParam("data2") String data2, ModelMap modelMap) {
        // データセットを作成
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // data1をデータセットに追加
        String[] valueArray1 = data1.split("\n");
        for (int i=0; i<valueArray1.length; i++) {
            double value = Double.valueOf(valueArray1[i]);
            dataset.addValue(value, "data1", String.valueOf(i));
        }

        // data2をデータセットに追加
        String[] valueArray2 = data2.split("\n");
        for (int i=0; i<valueArray2.length; i++) {
            double value = Double.valueOf(valueArray2[i]);
            dataset.addValue(value, "data2", String.valueOf(i));
        }

        // チャートを作成
        JFreeChart chart = ChartFactory.createLineChart("タイトル", "カテゴリー", "値", dataset);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 画像の出力先
            ChartUtils.writeChartAsPNG(byteArrayOutputStream, chart, 600, 400); // チャートをPNG画像として出力
            String base64string = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()); // 画像をBase64でエンコード
            String dataUri = "data:image/png;base64," + base64string; // data URIの文字列を作成
            modelMap.addAttribute("dataUri", dataUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "process";
    }
    
    @GetMapping("/graph")
    public String graph(ModelMap modelMap) {
        // データセットを作成
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // data1をデータセットに追加
        dataset.addValue(800, "本社", "4月");
        dataset.addValue(600, "本社", "5月");
        dataset.addValue(900, "本社", "6月");
        
        // チャートを作成
        JFreeChart chart = ChartFactory.createBarChart(
        		"タイトル",
        		"カテゴリー",
        		"値",
        		dataset,
        		PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 画像の出力先
            ChartUtils.writeChartAsPNG(byteArrayOutputStream, chart, 600, 400); // チャートをPNG画像として出力
            String base64string = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()); // 画像をBase64でエンコード
            String dataUri = "data:image/png;base64," + base64string; // data URIの文字列を作成
            modelMap.addAttribute("dataUri", dataUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "process";
    }
}
