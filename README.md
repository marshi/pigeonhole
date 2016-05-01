# pigeonhole

[![Circle CI](https://circleci.com/gh/marshi/pigeonhole/tree/master.svg?style=svg&circle-token=1acd1ad6a8214b85d9de9714fdfe586fbeea38a9)](https://circleci.com/gh/marshi/pigeonhole/tree/master)

動作確認用サーバとそれぞれのサーバにデプロイされたgitブランチの対応表を生成するツール

下の図のようにPigeonhole管理下にあるサーバとブランチが表示される

![](https://lh4.googleusercontent.com/Wm8FmUUe4pBFXdnORRpdcfRTpNN8C4aa1CSCVT3z6EZb49HklQm6EZsBjIhduUfanLqLaAVe6DKC450=w1896-h875-rw)

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
  host_group character varying(128),
  username character varying(32),
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
