# pigeonhole

[![Circle CI](https://circleci.com/gh/marshi/pigeonhole/tree/master.svg?style=svg&circle-token=1acd1ad6a8214b85d9de9714fdfe586fbeea38a9)](https://circleci.com/gh/marshi/pigeonhole/tree/master)

動作確認用サーバとそれぞれのサーバにデプロイされたgitブランチの対応表を生成するツール

下の図のようにPigeonhole管理下にあるサーバとブランチが表示される

![](https://raw.githubusercontent.com/marshi/images/master/pigeonhole/pigeionhole.png)

# 機能

* HTTP POSTリクエストによってホスト名、ブランチ名を登録. CIでデプロイ後にPOSTリクエストする使い方を想定
```
# 例
curl -X POST -d Payloads={\"host\":\"${HOST_NAME}\"\,\"branch\":\"${BRANCH_NAME}\"} http://<Pigeionholeのホスト>:<ポート>/hook/deploy
```

* ダッシュボードでサーバのホスト名とブランチ名の一覧表示
* サーバ一覧表示、サーバの登録削除

# Get Started

## データベーステーブルの生成
src/main/resources/application.conf で設定されているPostgreSQLに下記のテーブルを生成する

```SQL
CREATE TABLE host_branch
(
  id serial NOT NULL,
  branch_name character varying(128),
  host_machine_id integer NOT NULL,
  deploy_time timestamp with time zone,
  CONSTRAINT id PRIMARY KEY (id),
  CONSTRAINT host_branch_host_machine_id_key UNIQUE (host_machine_id)
)

```

```SQL
CREATE TABLE host_machine
(
  id serial NOT NULL,
  name character varying(64) NOT NULL,
  CONSTRAINT host_machine_pkey PRIMARY KEY (id),
  CONSTRAINT host_machine_name_key UNIQUE (name)
)
```

## 起動

プロジェクトのルートディレクトリで下記のコマンドを実行

デフォルトで8888番ポードで起動する

```bash
./gradlew run
```

# 名前の由来
鳩の巣原理(pigeonhole principle)から拝借

開発時にサーバの取り合いを防ぐにはブランチの数だけサーバを用意しないといけない

”小さく区切られた整理棚”を表す単語でもあるので、ブランチとサーバの対応関係を整理するという意味も込めた
