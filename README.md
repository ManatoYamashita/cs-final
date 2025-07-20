# Conputer Simulation Final Report(2. Next.js satisfaction survey)

![cs-final](https://github.com/user-attachments/assets/ca30db8d-9ed6-4c4c-82df-80dbbc54a1bf)


[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Weka](https://img.shields.io/badge/Weka-3.9.6-orange?style=for-the-badge)](https://www.cs.waikato.ac.nz/ml/weka/)
[![Next.js](https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=next.js&logoColor=white)](https://nextjs.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

## 📋 プロジェクト概要

![stateofjs2024](./images/stateofjs2024.webp)

### 背景
State of JS 2024の調査結果により、Next.jsは使用量では圧倒的に上位にあるものの、ユーザー満足度（retention）では最下位に近いという興味深い現象が判明しています。この矛盾する結果の背景を教師なし学習により分析し、Next.js利用者の満足度とユーザーパターンの関係を明らかにすることを目的としています。

![Next.js rations](./images/meta_frameworks_ratios.png)

### 目的
- Next.js利用者の満足度とユーザーパターンの関係を明らかにする
- 高満足度ユーザーと低満足度ユーザーの特徴を特定する
- 競合メタフレームワークとの差別化ポイントを分析する
- Next.jsの改善点や市場戦略に関する示唆を得る

## 🎯 課題要件

### 基本情報
- **科目**: コンピュータシミュレーション（東京都市大学）
- **担当**: 教師なし学習部分
- **提出期限**: 2025年8月1日（金）23:59
- **形式**: グループ課題（3名1組）

### 必須分析対象
- ✅ **教師あり学習** - 他メンバー担当
- ✅ **教師なし学習** - **あなたの担当（Next.js分析）**
- ✅ **テキストデータ分析** - 他メンバー担当

### 技術制約
- **プログラム言語**: Java（原則）
- **ライブラリ**: Weka使用推奨
- **データ形式**: CSV → ARFF変換必須
- **前処理**: 欠測値補完必須
- **目的変数**: 最右列配置

## 🏗️ プロジェクト構造

```bash
cs-final/
├── .git/                          # Gitリポジトリ管理
├── CLAUDE.md                      # プロジェクト概要・計画書（メイン）
├── README.md                      # プロジェクト説明（このファイル）
├── task.md                        # 課題要件・提出物詳細
├── surveys/                       # 調査データ
│   ├── README.md                  # データ説明
│   ├── state-of-js-2024/         # State of JS 2024データ
│   └── state-of-react-2024/      # State of React 2024データ
└── reports/                       # レポート・分析結果
    ├── REPORTS.md                 # レポートテンプレート
    └── report2.md                 # Next.js教師なし学習分析レポート
```

## 📊 分析手法

### 1. K-meansクラスタリング
**目的**: Next.js開発者を満足度パターンでセグメント化
- **データ**: 満足度スコア、経験年数、使用機能、プロジェクト規模
- **仮説**: 特定の使用パターンが満足度に強く影響している

### 2. 階層クラスタリング
**目的**: メタフレームワーク競合関係の分析
- **データ**: Next.js vs Astro vs SvelteKit vs Nuxt.js の利用状況
- **仮説**: Astro、SvelteKitの高満足度には特定の利用パターンが存在

### 3. 主成分分析（PCA）
**目的**: Next.js機能利用パターンの抽出
- **データ**: App Router、Server Components、API Routes等の機能利用状況
- **仮説**: 機能の組み合わせ使用が満足度に影響

## 📈 データ仕様

### データソース
- **State of JS 2024 公式データ**: https://stateofjs.com/
- **State of React 2024 公式データ**: https://stateofreact.com/
- **補完データ**: GitHub State of JS/React リポジトリ、過去年度データ

### 利用可能なデータセット
#### State of JS 2024
- **メタフレームワーク使用率推移**: `surveys/state-of-js-2024/meta_frameworks_ratios.json`
- **業務での使用状況**: `surveys/state-of-js-2024/meta_frameworks_work.json`
- **メタフレームワーク満足度**: `surveys/state-of-js-2024/meta_frameworks_happiness.json`
- **メタフレームワークの課題**: `surveys/state-of-js-2024/meta_frameworks_pain_points.json`

#### State of React 2024
- **全機能データ**: `surveys/state-of-react-2024/all_features.json` (4,429行)
- **全ツール経験**: `surveys/state-of-react-2024/all_tools_experience.json` (4,706行)
- **Hooks課題**: `surveys/state-of-react-2024/hooks_pain_points.json` (271行)
- **主要API課題**: `surveys/state-of-react-2024/main_apis_pain_points.json` (248行)
- **新API課題**: `surveys/state-of-react-2024/new_apis_pain_points.json` (239行)
- **コンポーネントライブラリ課題**: `surveys/state-of-react-2024/omponent_libraries_pain_points.json` (157行)

### データ項目
| カテゴリ | 項目 | データ型 | 説明 |
|----------|------|----------|------|
| 基本情報 | user_id | 文字列 | ユーザー識別子 |
| 経験 | nextjs_experience | 数値 | Next.js使用年数 |
| 満足度 | satisfaction_score | 数値 | 満足度スコア (1-5) |
| 機能利用 | app_router_usage | バイナリ | App Router使用有無 |
| 機能利用 | server_components | バイナリ | Server Components使用有無 |
| 機能利用 | api_routes | バイナリ | API Routes使用有無 |
| 機能利用 | static_generation | バイナリ | Static Generation使用有無 |
| 機能利用 | image_optimization | バイナリ | Image最適化使用有無 |
| プロジェクト | project_size | カテゴリ | プロジェクト規模 (small/medium/large) |
| プロジェクト | project_type | カテゴリ | プロジェクト種類 (corporate/startup/personal) |
| 競合 | also_uses_astro | バイナリ | Astro併用有無 |
| 競合 | also_uses_sveltekit | バイナリ | SvelteKit併用有無 |
| 競合 | also_uses_nuxt | バイナリ | Nuxt.js併用有無 |

## 🛠️ 技術仕様

### 開発環境
- **OS**: macOS 15.5 (24F74)
- **言語**: Java（最新版）
- **ライブラリ**: Weka 3.9.6
- **データ処理**: CSV → ARFF変換
- **IDE**: NetBeans（推奨）

### 前処理戦略
1. **欠測値処理**
   - 数値データ: 平均値補完
   - カテゴリデータ: 最頻値補完
   - 補完不可データ: 行削除

2. **データ変換**
   - カテゴリデータのワンホットエンコーディング
   - 数値データの標準化
   - 目的変数を最右列に配置

3. **データ検証**
   - 重複データの除去
   - 外れ値の検出・処理
   - データ品質チェック

## 📋 セットアップ手順

### 1. 環境構築
```bash
# Javaのインストール確認
java -version

# Wekaのダウンロード
wget https://sourceforge.net/projects/weka/files/latest/download/weka-3-9-6.zip
unzip weka-3-9-6.zip
```

### 2. データ準備
```bash
# State of JS 2024データの取得
# 手動でjsonをダウンロード

# State of React 2024データの取得
# 手動でjsonをダウンロード

# CSV → ARFF変換
java -cp weka.jar weka.core.converters.CSVLoader data.csv > data.arff
```

### 3. プログラム実行
```bash
# K-meansクラスタリング実行
java -cp weka.jar weka.clusterers.SimpleKMeans -t data.arff -N 3

# 階層クラスタリング実行
java -cp weka.jar weka.clusterers.HierarchicalClusterer -t data.arff

# 主成分分析実行
java -cp weka.jar weka.attributeSelection.PrincipalComponents -t data.arff
```

## 📊 分析手順

### Phase 1: データ探索
1. データの基本統計量確認
2. 相関関係の可視化
3. 分布の確認

### Phase 2: クラスタリング分析
1. **K-means実行**
   - 最適クラスタ数の決定（エルボー法）
   - クラスタ結果の解釈
   - 各クラスタの特徴分析

2. **階層クラスタリング実行**
   - デンドログラムの作成
   - 距離行列の分析
   - フレームワーク関係の可視化

### Phase 3: 主成分分析
1. 主成分の抽出
2. 寄与率の計算
3. 主成分の解釈

### Phase 4: 結果統合・考察
1. 各分析結果の統合
2. 仮説の検証
3. 実用的な洞察の抽出

## 📁 提出物

### 必須提出物
1. **分析レポート**（PDF形式）
   - タイトル（代表者の学籍番号.pdf）
   - 表紙に「タイトル」、「メンバーの学籍番号」、「氏名」
   - 担当者と貢献度の明記
   - 分析手法の説明
   - 使用データの取得先と内容
   - 図表を多用した分かりやすい構成

2. **Javaプログラム**（全て）
   - 原則Javaで作成
   - 他言語使用時はJava版も併記
   - 前処理、分析、可視化の全コード

3. **使用データセット**
   - 元データ（CSV形式）
   - 前処理済みデータ（ARFF形式）
   - データ辞書・説明書

4. **ZIP圧縮**して一括提出

### 評価基準（100点満点）
- データの真新しさ: 5点
- 記載の正確さ: 20点
- 分析手法の工夫: 15点
- **結果の解釈と考察: 35点**（最重要）
- 課題設定の工夫: 25点

## 🎯 期待される成果

### 分析結果
1. **開発者セグメント**
   - 高満足度ユーザーの特徴
   - 低満足度ユーザーの課題
   - 各セグメントの具体的な改善提案

2. **競合分析**
   - Next.js vs 他フレームワークの差別化ポイント
   - 満足度差の具体的要因
   - 市場ポジショニングの提案

3. **機能利用パターン**
   - 効果的な機能組み合わせ
   - 未活用機能の特定
   - 学習パスの提案

### 実用的価値
- Next.js開発チームへの改善提案
- 開発者コミュニティへの学習指針
- 企業のフレームワーク選択支援

## 📅 スケジュール

| 週 | タスク | 成果物 |
|----|--------|--------|
| 1 | データ収集・前処理 | クリーンなデータセット |
| 2 | 探索的データ分析 | 基本統計・可視化 |
| 3 | クラスタリング分析 | セグメント分析結果 |
| 4 | 主成分分析・結果統合 | 最終分析結果 |
| 5 | レポート作成 | 最終レポート |

## ⚠️ リスク管理

### 想定リスク
1. **データ取得困難**
   - 対策: 複数のデータソース確保
   - 代替案: サンプルデータでの概念実証

2. **データ品質問題**
   - 対策: 厳格な前処理プロセス
   - 検証: 複数の検証手法併用

3. **分析結果の解釈困難**
   - 対策: 専門知識の活用
   - 支援: 教員・SAへの相談

### 品質保証
- 分析結果の再現性確保
- 複数手法での結果検証
- 統計的有意性の確認

## 📚 参考資料

### 技術資料
- [State of JS 2024 Survey Results](https://stateofjs.com/)
- [State of React 2024 Survey Results](https://stateofreact.com/)
- [Weka Documentation](https://www.cs.waikato.ac.nz/ml/weka/documentation.html)
- [Java機械学習ライブラリ](https://www.oracle.com/java/)

### 学術資料
- クラスタリング分析に関する論文
- 主成分分析の実装例
- ユーザー満足度分析の手法

## 👥 チーム情報

### メンバー
- **教師なし学習担当**: [あなたの名前]
- **教師あり学習担当**: [メンバー名]
- **テキスト分析担当**: [メンバー名]

### サポート
- **教員**: 三川 健太 先生
- **場所**: 22H教室
- **質問受付**: 授業時間中

## 📄 ライセンス

このプロジェクトはMITライセンスの下で公開されています。詳細は[LICENSE](LICENSE)ファイルを参照してください。

## 🤝 貢献

このプロジェクトは東京都市大学コンピュータシミュレーション最終課題として作成されています。質問や提案がある場合は、授業時間中に22H教室でお気軽にお声がけください。

---

**最終更新**: 2025年7月18日  
**バージョン**: 1.0.0  
**ステータス**: 開発中
