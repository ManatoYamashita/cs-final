# Next.js教師なし学習分析 グラフ作成完全マニュアル

## 概要
本ガイドでは、Next.js教師なし学習分析の結果を可視化するための5つの重要なグラフの作成手順を詳説します。生成された5つのCSVファイルを使用してExcel、Python、R、Tableau等で作成可能です。

## 生成されたデータファイル

### CSVファイル一覧（`csv_data/`ディレクトリ内）
1. **`framework_comparison.csv`** - フレームワーク比較データ
2. **`kmeans_clusters.csv`** - K-meansクラスタ分析結果
3. **`nextjs_timeseries.csv`** - Next.js時系列推移データ
4. **`correlation_matrix.csv`** - 相関分析結果
5. **`pca_results.csv`** - 主成分分析結果

---

## 図1: フレームワーク矛盾指数比較（棒グラフ）

### データファイル
`csv_data/framework_comparison.csv`

### グラフタイプ
水平棒グラフ（降順）

### 作成手順（Excel）
1. **データ読み込み**
   - Framework列（X軸）とContradiction_Index列（Y軸）を選択
   
2. **グラフ作成**
   ```
   挿入 → グラフ → 棒グラフ（水平）
   ```

3. **スタイル設定**
   - タイトル: 「フレームワーク矛盾指数比較（State of JS 2024）」
   - Y軸ラベル: 「フレームワーク」
   - X軸ラベル: 「矛盾指数（使用率/継続率 × 100）」
   - カラー設定:
     * Next.js: #FF6B6B（赤：最高矛盾）
     * Gatsby: #FF9F43（オレンジ：衰退）
     * Nuxt: #FEE668（黄：中程度）
     * Astro: #26de81（緑：理想）
     * SvelteKit: #26de81（緑：理想）
     * Remix: #26de81（緑：理想）

4. **値ラベル追加**
   - 各棒の端に数値表示（例：79.41）

### Python実装例
```python
import pandas as pd
import matplotlib.pyplot as plt

# データ読み込み
df = pd.read_csv('csv_data/framework_comparison.csv')

# グラフ作成
plt.figure(figsize=(12, 8))
colors = ['#FF6B6B', '#26de81', '#26de81', '#FEE668', '#26de81', '#FF9F43']
bars = plt.barh(df['Framework'], df['Contradiction_Index'], color=colors)

# スタイル設定
plt.xlabel('矛盾指数（使用率/継続率 × 100）')
plt.title('フレームワーク矛盾指数比較（State of JS 2024）')
plt.grid(axis='x', alpha=0.3)

# 値ラベル
for bar in bars:
    plt.text(bar.get_width() + 1, bar.get_y() + bar.get_height()/2, 
             f'{bar.get_width():.1f}', va='center')

plt.tight_layout()
plt.savefig('figures/fig1_contradiction_index.png', dpi=300)
plt.show()
```

---

## 図2: 使用率vs継続率散布図（クラスタ別色分け）

### データファイル
`csv_data/kmeans_clusters.csv`

### グラフタイプ
散布図（クラスタ別色分け）

### 作成手順（Excel）
1. **データ準備**
   - Usage_Rate（X軸）, Retention_Rate（Y軸）, Pattern_Type（色分け）

2. **散布図作成**
   ```
   挿入 → 散布図
   ```

3. **クラスタ別色分け**
   - 理想型（緑）: Astro, SvelteKit, Remix
   - 矛盾型（赤）: Next.js, Nuxt  
   - 衰退型（灰）: Gatsby

4. **追加要素**
   - 対角線（y=x）追加で「完全バランス」ライン表示
   - Next.jsラベルを強調表示

### Python実装例
```python
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('csv_data/kmeans_clusters.csv')

# クラスタ別色分け
colors = {'理想型': '#26de81', '矛盾型': '#FF6B6B', '衰退型': '#95a5a6'}

plt.figure(figsize=(12, 10))

# クラスタ別プロット
for pattern in df['Pattern_Type'].unique():
    subset = df[df['Pattern_Type'] == pattern]
    plt.scatter(subset['Usage_Rate'], subset['Retention_Rate'], 
               c=colors[pattern], label=pattern, s=100, alpha=0.7)

# 対角線
plt.plot([0, 1], [0, 1], 'k--', alpha=0.5, label='完全バランス線')

# フレームワーク名ラベル
for _, row in df.iterrows():
    plt.annotate(row['Framework'], 
                (row['Usage_Rate'], row['Retention_Rate']),
                xytext=(5, 5), textcoords='offset points')

plt.xlabel('使用率')
plt.ylabel('継続率（満足度）')
plt.title('フレームワーク使用率 vs 継続率（クラスタ分析結果）')
plt.legend()
plt.grid(True, alpha=0.3)
plt.savefig('figures/fig2_usage_vs_retention.png', dpi=300)
plt.show()
```

---

## 図3: Next.js継続率時系列推移（折れ線グラフ）

### データファイル
`csv_data/nextjs_timeseries.csv`

### グラフタイプ
折れ線グラフ（実測値 + 予測値）

### 作成手順（Excel）
1. **データ準備**
   - Year（X軸）, Retention_Rate（実測値）, Predicted_Rate（予測値）

2. **折れ線グラフ作成**
   ```
   挿入 → 折れ線グラフ
   ```

3. **スタイル設定**
   - 実測値: 太い実線（青）
   - 予測値: 破線（赤）
   - 2025年以降を予測として区別

### Python実装例
```python
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('csv_data/nextjs_timeseries.csv')

plt.figure(figsize=(14, 8))

# 実測データ（2018-2024）
actual = df[df['Trend'] != '予測']
predicted = df[df['Trend'] == '予測']

plt.plot(actual['Year'], actual['Retention_Rate'], 
         'o-', linewidth=3, markersize=8, color='#3498db', label='実測値')

# 予測データ（2025-2027）
plt.plot(predicted['Year'], predicted['Retention_Rate'], 
         '--', linewidth=2, color='#e74c3c', label='予測値')

# 劣化トレンド強調
plt.axvline(x=2022, color='orange', linestyle=':', alpha=0.7, 
           label='急激劣化開始点')

plt.xlabel('年')
plt.ylabel('継続率（満足度）')
plt.title('Next.js継続率の時系列推移と将来予測')
plt.legend()
plt.grid(True, alpha=0.3)
plt.ylim(0, 1)

# 注釈追加
plt.annotate('20ポイント劣化', xy=(2021, 0.75), xytext=(2019, 0.5),
            arrowprops=dict(arrowstyle='->', color='red'),
            fontsize=12, color='red')

plt.savefig('figures/fig3_nextjs_timeseries.png', dpi=300)
plt.show()
```

---

## 図4: 継続率相関分析（ヒートマップ）

### データファイル
`csv_data/correlation_matrix.csv`

### グラフタイプ
ヒートマップ（相関係数の可視化）

### Python実装例
```python
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

df = pd.read_csv('csv_data/correlation_matrix.csv')

# 相関行列作成
corr_matrix = pd.DataFrame({
    'Factor': df['Factor'],
    'Correlation': df['Correlation_with_Retention']
}).set_index('Factor')

plt.figure(figsize=(10, 8))

# ヒートマップ
sns.heatmap(corr_matrix.T, annot=True, cmap='RdBu_r', center=0,
            fmt='.3f', square=True, cbar_kws={'label': '相関係数'})

plt.title('継続率（満足度）との相関分析')
plt.xlabel('要因')
plt.ylabel('')
plt.tight_layout()
plt.savefig('figures/fig4_correlation_heatmap.png', dpi=300)
plt.show()
```

---

## 図5: 主成分分析結果（寄与率グラフ）

### データファイル
`csv_data/pca_results.csv`

### グラフタイプ
棒グラフ + 折れ線グラフ（寄与率 + 累積寄与率）

### Python実装例
```python
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('csv_data/pca_results.csv')

fig, ax1 = plt.subplots(figsize=(12, 8))

# 寄与率（棒グラフ）
ax1.bar(df['Principal_Component'], df['Variance_Explained'], 
        alpha=0.7, color='skyblue', label='寄与率')
ax1.set_xlabel('主成分')
ax1.set_ylabel('寄与率 (%)', color='blue')
ax1.tick_params(axis='y', labelcolor='blue')

# 累積寄与率（折れ線グラフ）
ax2 = ax1.twinx()
ax2.plot(df['Principal_Component'], df['Cumulative_Variance'], 
         'ro-', linewidth=2, label='累積寄与率')
ax2.set_ylabel('累積寄与率 (%)', color='red')
ax2.tick_params(axis='y', labelcolor='red')

# 85%線（Kaiser基準）
ax2.axhline(y=85, color='orange', linestyle='--', alpha=0.7, 
           label='Kaiser基準（85%）')

plt.title('主成分分析結果：各成分の寄与率')
plt.grid(True, alpha=0.3)

# 凡例統合
lines1, labels1 = ax1.get_legend_handles_labels()
lines2, labels2 = ax2.get_legend_handles_labels()
ax1.legend(lines1 + lines2, labels1 + labels2, loc='center right')

plt.tight_layout()
plt.savefig('figures/fig5_pca_contribution.png', dpi=300)
plt.show()
```

---

## 推奨ツール別まとめ

### Excel
- **適用グラフ**: 図1, 図2, 図3
- **メリット**: 簡単、レポート挿入が容易
- **手順**: CSVインポート → グラフウィザード → スタイル調整

### Python (matplotlib/seaborn)
- **適用グラフ**: 全図（1-5）
- **メリット**: 高品質、カスタマイズ性、再現性
- **必要ライブラリ**: pandas, matplotlib, seaborn

### R (ggplot2)
- **適用グラフ**: 全図（1-5）
- **メリット**: 統計向け、出版品質
- **推奨パッケージ**: ggplot2, dplyr, corrplot

### Tableau
- **適用グラフ**: 図1, 図2, 図3
- **メリット**: インタラクティブ、プレゼンテーション向け

---

## 図表挿入用テンプレート

レポートへの挿入時は以下の形式を使用：

```markdown
{図1. フレームワーク矛盾指数比較 - Next.jsの突出した矛盾を示す棒グラフ}

{図2. 使用率vs継続率散布図 - クラスタ分析による3つのパターン可視化}

{図3. Next.js継続率時系列推移 - 2018-2024年の劣化トレンドと将来予測}

{図4. 継続率相関分析ヒートマップ - 関心度との極強正相関を示す}

{図5. 主成分分析寄与率 - 満足度関連成分が45.2%を占有}
```

## 技術仕様

### 推奨解像度
- **図のサイズ**: 1200×800 ピクセル
- **DPI**: 300（印刷品質）
- **フォーマット**: PNG（レポート用）, SVG（拡大縮小用）

### カラーパレット
- **理想型**: #26de81（緑）
- **矛盾型**: #FF6B6B（赤）  
- **衰退型**: #95a5a6（灰）
- **予測値**: #e74c3c（赤破線）
- **実測値**: #3498db（青実線）

この完全ガイドにより、Next.js教師なし学習分析の全結果を視覚的に効果的に表現できます。