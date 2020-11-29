# den3Account
RESTful な アカウント管理/外部連携アプリの管理サービス

# 使用技術
- Java8
- Redis
- MariaDB
- Docker
- Docker Compose

# 動かし方
- このプロジェクトの.gitをcloneする
- "docker build -t den3accountinjar ."を実行
- "docker-compose up" を実行

# DBのテーブルについて
| 列名 | uuid | mail | pass | nick | icon | last_login_time | admin |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| プライマリーキー | ○ | - | - | - | - | - | - |
| データ種類 | VARCHAR | VARCHAR | VARCHAR | VARCHAR | VARCHAR | VARCHAR | BOOLEAN |
| 役割 | 識別子 | メールアドレス | ハッシュ化したパスワード | ニックネーム | アイコン| 最終ログイン時刻 | 管理者権限の有無 |
