# Next.js教師なし学習分析プロジェクト 最終構造

## プロジェクト概要
東京都市大学コンピュータシミュレーション最終課題として実施した、State of JS 2024データを用いたNext.jsの矛盾現象解明プロジェクト。

## ディレクトリ構造

```bash
cs-final/
├── .git/                          # Gitリポジトリ管理
├── CLAUDE.md                      # Claude Code用プロジェクト指示書
├── README.md                      # プロジェクト説明書（336行）
├── PROJECT_STRUCTURE.md           # プロジェクト構造説明（124行）
│
├── src/                           # ソースコード
│   └── NextjsWekaAnalysis.java    # Weka完全版分析プログラム（523行）
│
├── weka/                          # Weka環境
│   └── weka-3-8-6/               # Weka 3.8.6公式ライブラリ
│
├── output/                        # Weka分析結果
│   └── meta_frameworks_analysis.arff
│
├── results/                       # 分析結果詳細
│   ├── weka_kmeans_results.txt    # K-meansクラスタリング結果
│   ├── weka_hierarchical_results.txt # 階層クラスタリング結果
│   └── weka_contradiction_analysis.txt # 矛盾指数分析結果
│
├── csv_data/                      # グラフ作成用CSVデータ
│   ├── framework_comparison.csv   # フレームワーク比較データ
│   ├── kmeans_clusters.csv        # K-meansクラスタ結果
│   ├── nextjs_timeseries.csv      # Next.js時系列データ
│   ├── correlation_matrix.csv     # 相関分析結果
│   └── pca_results.csv            # 主成分分析結果
│
├── surveys/                       # 元データ
│   ├── README.md                  # データ説明
│   ├── state-of-js-2024/         # State of JS 2024データ
│   └── state-of-react-2024/      # State of React 2024データ
│
├── reports/                       # レポート
│   ├── REPORTS.md                 # レポートテンプレート（70行）
│   └── report2.md                 # 最終レポート（281行）
│
└── images/                        # 画像資料
    ├── meta_frameworks_ratios.png # フレームワーク使用率図
    ├── correction_matrix.png      # 相関行列図
    ├── framework_comparison.png   # フレームワーク比較図
    ├── kmeans_clusters.png        # K-meansクラスタ図
    ├── nextjs_timeseries.png      # Next.js時系列図
    ├── pca_result.png             # PCA結果図
    └── stateofjs2024.webp         # State of JS 2024ロゴ
```

## 重要ファイル詳細

### 📊 分析コード
- **`src/NextjsWekaAnalysis.java`**: Weka 3.8.6を使用した本格的教師なし学習分析
  - エルボー法による最適K値決定（K=4）
  - Next.js単独クラスタ形成の発見
  - 矛盾指数3.82倍の科学的証明

### 📈 分析結果データ
- **`csv_data/*.csv`**: 5つの重要CSVデータセット
  - `framework_comparison.csv`: 6フレームワーク全指標比較
  - `kmeans_clusters.csv`: Weka K=4クラスタ分類結果
  - `nextjs_timeseries.csv`: 2018-2027年推移・予測
  - `correlation_matrix.csv`: 継続率相関分析
  - `pca_results.csv`: 主成分寄与率データ

### 📊 Weka詳細結果
- **`results/weka_kmeans_results.txt`**: SSE=0.295の最適クラスタリング
- **`results/weka_hierarchical_results.txt`**: Ward法階層クラスタリング
- **`results/weka_contradiction_analysis.txt`**: 矛盾指数完全版分析

### 📋 ドキュメント
- **`GRAPH_CREATION_GUIDE.md`**: 5つのグラフ作成完全マニュアル
- **`reports/report2.md`**: 最終分析レポート
- **`README.md`**: プロジェクト概要説明

## 実行方法

### Weka分析の実行
```bash
# コンパイル
javac -cp "weka/weka-3-8-6/weka.jar:." src/NextjsWekaAnalysis.java -d .

# 実行
java -cp "weka/weka-3-8-6/weka.jar:." NextjsWekaAnalysis
```

### グラフ作成
`GRAPH_CREATION_GUIDE.md`を参照し、`csv_data/`内のCSVファイルを使用

## 主要成果

### 🔬 科学的発見
1. **Next.js単独クラスタ形成**: Wekaエルボー法でK=4が最適、Next.jsが完全孤立
2. **矛盾指数3.82倍**: 競合平均20.79に対してNext.js 79.41の異常値
3. **時系列劣化**: 6年間で継続率20ポイント劣化（年間3.25%）
4. **関心度最重要**: r=0.944の極強相関で満足度決定要因特定

### 📊 技術的貢献
- 独自「矛盾指数」概念による定量的フレームワーク評価手法
- Weka準拠の教師なし学習による技術選択支援メトリクス
- State of JS実データ活用の本格的機械学習分析フレームワーク

### 🎯 実用的価値
- Next.js開発チームへの具体的改善提案
- 企業の技術選択における科学的判断基準
- 開発者コミュニティの適切な技術学習指針

## 再現性情報
- **実行環境**: macOS 15.5, Java 22.0.2, Weka 3.8.6
- **データ取得**: 2025年7月19日（State of JS 2024公式）
- **分析実行**: 2025年7月20日
- **固定シード**: 42（K-meansの再現性確保）

---

**注意**: このプロジェクト構造は、実際に分析で使用したファイルのみを残し、不要なファイルを削除した最終版です。全ての分析は再現可能な形で保存されています。