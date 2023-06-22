--テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS foods;
DROP TABLE IF EXISTS vegetables;
DROP TABLE IF EXISTS cafeteria;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS records;
DROP TABLE IF EXISTS intake;

--カテゴリーテーブル
CREATE TABLE categories
(
id SERIAL PRIMARY KEY,
name TEXT
);

--料理テーブル
CREATE TABLE foods
(
id SERIAL PRIMARY KEY,
category_id INTEGER,
name TEXT,
carbohy INTEGER,
protein INTEGER,
lipid INTEGER,
vitamin INTEGER,
mineral INTEGER
);

--野菜テーブル
CREATE TABLE vegetables
(
id SERIAL PRIMARY KEY,
category_id INTEGER,
name TEXT,
carbohy INTEGER,
protein INTEGER,
lipid INTEGER,
vitamin INTEGER,
mineral INTEGER
);

--社食テーブル
CREATE TABLE cafeteria
(
id SERIAL PRIMARY KEY,
category_id INTEGER,
name TEXT,
carbohy INTEGER,
protein INTEGER,
lipid INTEGER,
vitamin INTEGER,
mineral INTEGER
);

--ユーザテーブル
CREATE TABLE users
(
id SERIAL PRIMARY KEY,
name TEXT,
gender TEXT,
age INTEGER,
tel TEXT,
email TEXT,
password TEXT
);

--記録テーブル
CREATE TABLE records
(
id SERIAL PRIMARY KEY,
name TEXT
);

--摂取量テーブル
CREATE TABLE intake
(
id SERIAL PRIMARY KEY,
range TEXT,
carbohy INTEGER,
protein INTEGER,
lipid INTEGER,
vitamin INTEGER,
mineral INTEGER
);
