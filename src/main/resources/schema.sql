--テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS foods;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS records;
DROP TABLE IF EXISTS intake;

--カテゴリーテーブル
CREATE TABLE categories
(
id SERIAL PRIMARY KEY,
name TEXT
);

--料理/野菜/社食テーブル
CREATE TABLE foods
(
id SERIAL PRIMARY KEY,
category_id INTEGER,
name TEXT,
carbohydrates INTEGER,
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
user_id Integer,
name TEXT,
carbohydrates INTEGER,
protein INTEGER,
lipid INTEGER,
vitamin INTEGER,
mineral INTEGER,
day DATE
);

--必要摂取量テーブル
CREATE TABLE intake
(
id SERIAL PRIMARY KEY,
range TEXT,
gender TEXT,
carbohydrates INTEGER,
protein INTEGER,
lipid INTEGER,
vitamin INTEGER,
mineral INTEGER
);
