--カテゴリー
INSERT INTO categories(name) VALUES('料理');
INSERT INTO categories(name) VALUES('野菜各種');
INSERT INTO categories(name) VALUES('社食');
INSERT INTO categories(name) VALUES('Myメニュー');

--必要な摂取量
--vitamin(μg), mineral(mg)で計算、Na = 5000mgと仮定
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('18～29','男性',331,65,74,7009,9640);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('30～49','男性',338,65,75,7059,9620);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('50～64','男性',325,65,72,8059,9620);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('65～74','男性',300,65,67,8009,9600);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('18～29','女性',250,50,56,5809,8720);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('30～49','女性',256,50,57,6359,8740);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('50～64','女性',244,50,54,6859,8740);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('65～74','女性',206,50,51,7359,8730);

INSERT INTO foods(category_id, name, carbohydrates, protein, lipid, vitamin, mineral) VALUES(1,'食パン',46.6,9.0,4.2,502.0,669.1);