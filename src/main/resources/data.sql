--カテゴリー
INSERT INTO categories(name) VALUES('料理');
INSERT INTO categories(name) VALUES('野菜各種');
INSERT INTO categories(name) VALUES('社食');

--必要な摂取量
--vitamin(μg), mineral(mg)で計算、Na = 5000mgと仮定
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('18～29','男性',50,65,20,7009,9640);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('30～49','男性',50,65,20,7059,9620);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('50～64','男性',50,65,20,8059,9620);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('18～29','男性',50,65,20,8009,9600);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('18～29','女性',50,50,20,5809,8720);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('30～49','女性',50,50,20,6359,8740);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('50～64','女性',50,50,20,6859,8740);
INSERT INTO intake(range, gender, carbohydrates, protein, lipid, vitamin, mineral) VALUES('18～29','女性',50,50,20,7359,8730);
