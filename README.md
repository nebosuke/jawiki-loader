# WikipdeiaのデータをMySQLに格納するためのSQLを生成する

## 背景
Wikipediaのデータはbz2で圧縮して4GBあって、非圧縮状態で17GBになるXMLである。
このままでは処理しにくいので、一旦MySQLに格納することにする。

## 想定するテーブル

```mysql
create database if not exists jawiki character set=utf8mb4;
create user 'jawiki' IDENTIFIED BY 'password';
grant all on *.* to 'jawiki'@'%' with grant option;
```

```shell
mysql -A -h 127.0.0.1 -P3306 -u jawiki -ppassword jawiki;
```

```mysql
CREATE TABLE `article` (
  `id` int NOT NULL,
  `title` text COLLATE utf8mb4_bin,
  `text` mediumtext COLLATE utf8mb4_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin
```

## ビルド方法

```text
export JAVA_HOME=~/Library/Java/JavaVirtualMachines/openjdk-23.0.1/Contents/Home
mvn package
```

## 実行方法

```text
java -jar target/jawiki-1.0-SNAPSHOT-jar-with-dependencies.jar -in ./jawiki-20241120-pages-articles.xml.bz2 \
| mysql -u jawiki -ppassword jawiki
```