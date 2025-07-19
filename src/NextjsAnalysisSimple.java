import java.io.*;
import java.util.*;

/**
 * Next.js教師なし学習分析プログラム（簡易版）
 * State of JS 2024データを用いてNext.jsの矛盾現象を解明
 * Wekaなしで基本的な統計分析とクラスタリングを実装
 */
public class NextjsAnalysisSimple {
    
    private static final String RESULTS_DIR = "results/";
    
    // フレームワークデータクラス
    static class Framework {
        String name;
        double usage;
        double retention;
        double positivity;
        double interest;
        double awareness;
        double appreciation;
        
        Framework(String name, double usage, double retention, double positivity, 
                 double interest, double awareness, double appreciation) {
            this.name = name;
            this.usage = usage;
            this.retention = retention;
            this.positivity = positivity;
            this.interest = interest;
            this.awareness = awareness;
            this.appreciation = appreciation;
        }
        
        double getContradictionIndex() {
            return (usage / retention) * 100;
        }
        
        double[] getFeatureVector() {
            return new double[]{usage, retention, positivity, interest, awareness, appreciation};
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Next.js教師なし学習分析開始 ===");
        
        // データの準備
        List<Framework> frameworks = prepareData();
        System.out.println("データ準備完了: " + frameworks.size() + "フレームワーク");
        
        // 基本統計分析
        performBasicStatistics(frameworks);
        
        // 簡易K-meansクラスタリング
        performSimpleKMeans(frameworks);
        
        // 矛盾指数分析
        performContradictionAnalysis(frameworks);
        
        // 時系列分析
        performTimeSeriesAnalysis();
        
        // 相関分析
        performCorrelationAnalysis(frameworks);
        
        System.out.println("=== 分析完了 ===");
    }
    
    /**
     * データの準備
     */
    private static List<Framework> prepareData() {
        List<Framework> frameworks = new ArrayList<>();
        
        // State of JS 2024の実データ
        frameworks.add(new Framework("Next.js", 0.54, 0.68, 0.32, 0.43, 0.99, 0.25));
        frameworks.add(new Framework("Astro", 0.23, 0.94, 0.34, 0.67, 0.82, 0.27));
        frameworks.add(new Framework("SvelteKit", 0.16, 0.90, 0.27, 0.55, 0.82, 0.35));
        frameworks.add(new Framework("Nuxt", 0.22, 0.81, 0.24, 0.39, 0.89, 0.42));
        frameworks.add(new Framework("Remix", 0.11, 0.80, 0.21, 0.48, 0.79, 0.31));
        frameworks.add(new Framework("Gatsby", 0.19, 0.27, 0.07, 0.12, 0.83, 0.14));
        
        return frameworks;
    }
    
    /**
     * 基本統計分析
     */
    private static void performBasicStatistics(List<Framework> frameworks) throws Exception {
        System.out.println("\n=== 基本統計分析 ===");
        
        // 各指標の平均、標準偏差を計算
        String[] metrics = {"使用率", "継続率", "ポジティブ率", "関心度", "認知度", "評価度"};
        
        for (int i = 0; i < 6; i++) {
            double sum = 0, sumSquare = 0;
            double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            
            for (Framework fw : frameworks) {
                double value = fw.getFeatureVector()[i];
                sum += value;
                sumSquare += value * value;
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
            
            double mean = sum / frameworks.size();
            double variance = (sumSquare / frameworks.size()) - (mean * mean);
            double stdDev = Math.sqrt(variance);
            
            System.out.printf("%s: 平均=%.3f, 標準偏差=%.3f, 最小=%.3f, 最大=%.3f\n", 
                metrics[i], mean, stdDev, min, max);
        }
        
        saveBasicStatistics(frameworks, metrics);
    }
    
    /**
     * 簡易K-meansクラスタリング
     */
    private static void performSimpleKMeans(List<Framework> frameworks) throws Exception {
        System.out.println("\n=== 簡易K-meansクラスタリング ===");
        
        int k = 3; // クラスタ数
        List<int[]> clusters = simpleKMeans(frameworks, k);
        
        System.out.println("クラスタ割り当て結果:");
        for (int i = 0; i < frameworks.size(); i++) {
            Framework fw = frameworks.get(i);
            int cluster = -1;
            for (int c = 0; c < clusters.size(); c++) {
                for (int idx : clusters.get(c)) {
                    if (idx == i) {
                        cluster = c;
                        break;
                    }
                }
            }
            System.out.printf("%s → クラスタ%d (矛盾指数: %.2f)\n", 
                fw.name, cluster, fw.getContradictionIndex());
        }
        
        // クラスタ特性分析
        analyzeClusterCharacteristics(frameworks, clusters);
        saveKMeansResults(frameworks, clusters);
    }
    
    /**
     * 簡易K-meansアルゴリズム実装
     */
    private static List<int[]> simpleKMeans(List<Framework> frameworks, int k) {
        int n = frameworks.size();
        int[] assignments = new int[n];
        Random random = new Random(42); // 再現性のためのシード固定
        
        // 初期クラスタ割り当て
        for (int i = 0; i < n; i++) {
            assignments[i] = random.nextInt(k);
        }
        
        // 最大10回の反復
        for (int iter = 0; iter < 10; iter++) {
            // クラスタ中心計算
            double[][] centroids = new double[k][6];
            int[] counts = new int[k];
            
            for (int i = 0; i < n; i++) {
                int cluster = assignments[i];
                double[] features = frameworks.get(i).getFeatureVector();
                for (int j = 0; j < 6; j++) {
                    centroids[cluster][j] += features[j];
                }
                counts[cluster]++;
            }
            
            for (int c = 0; c < k; c++) {
                if (counts[c] > 0) {
                    for (int j = 0; j < 6; j++) {
                        centroids[c][j] /= counts[c];
                    }
                }
            }
            
            // 再割り当て
            boolean changed = false;
            for (int i = 0; i < n; i++) {
                double[] features = frameworks.get(i).getFeatureVector();
                double minDist = Double.MAX_VALUE;
                int newCluster = assignments[i];
                
                for (int c = 0; c < k; c++) {
                    double dist = euclideanDistance(features, centroids[c]);
                    if (dist < minDist) {
                        minDist = dist;
                        newCluster = c;
                    }
                }
                
                if (newCluster != assignments[i]) {
                    assignments[i] = newCluster;
                    changed = true;
                }
            }
            
            if (!changed) break;
        }
        
        // 結果をクラスタごとのリストに変換
        List<int[]> clusters = new ArrayList<>();
        for (int c = 0; c < k; c++) {
            List<Integer> clusterMembers = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (assignments[i] == c) {
                    clusterMembers.add(i);
                }
            }
            clusters.add(clusterMembers.stream().mapToInt(i -> i).toArray());
        }
        
        return clusters;
    }
    
    /**
     * ユークリッド距離計算
     */
    private static double euclideanDistance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
    
    /**
     * クラスタ特性分析
     */
    private static void analyzeClusterCharacteristics(List<Framework> frameworks, List<int[]> clusters) {
        System.out.println("\nクラスタ特性分析:");
        
        String[] metrics = {"使用率", "継続率", "ポジティブ率", "関心度", "認知度", "評価度"};
        
        for (int c = 0; c < clusters.size(); c++) {
            System.out.printf("\nクラスタ%d (%d個のフレームワーク):\n", c, clusters.get(c).length);
            
            if (clusters.get(c).length == 0) continue;
            
            double[] avgFeatures = new double[6];
            double avgContradiction = 0;
            
            for (int idx : clusters.get(c)) {
                Framework fw = frameworks.get(idx);
                double[] features = fw.getFeatureVector();
                for (int i = 0; i < 6; i++) {
                    avgFeatures[i] += features[i];
                }
                avgContradiction += fw.getContradictionIndex();
                System.out.printf("  - %s\n", fw.name);
            }
            
            for (int i = 0; i < 6; i++) {
                avgFeatures[i] /= clusters.get(c).length;
            }
            avgContradiction /= clusters.get(c).length;
            
            System.out.printf("  平均矛盾指数: %.2f\n", avgContradiction);
            for (int i = 0; i < 6; i++) {
                System.out.printf("  平均%s: %.3f\n", metrics[i], avgFeatures[i]);
            }
        }
    }
    
    /**
     * 矛盾指数分析
     */
    private static void performContradictionAnalysis(List<Framework> frameworks) throws Exception {
        System.out.println("\n=== 矛盾指数分析 ===");
        
        Framework nextjs = frameworks.stream()
            .filter(fw -> fw.name.equals("Next.js"))
            .findFirst().orElse(null);
        
        if (nextjs == null) return;
        
        // 競合フレームワークの矛盾指数
        List<Framework> competitors = frameworks.stream()
            .filter(fw -> !fw.name.equals("Next.js") && !fw.name.equals("Gatsby"))
            .toList();
        
        double competitorAvgContradiction = competitors.stream()
            .mapToDouble(Framework::getContradictionIndex)
            .average().orElse(0.0);
        
        System.out.printf("Next.js矛盾指数: %.2f\n", nextjs.getContradictionIndex());
        System.out.printf("競合平均矛盾指数: %.2f\n", competitorAvgContradiction);
        System.out.printf("Next.js vs 競合の差: %.2f倍\n", 
            nextjs.getContradictionIndex() / competitorAvgContradiction);
        
        System.out.println("\n全フレームワークの矛盾指数:");
        frameworks.stream()
            .sorted((a, b) -> Double.compare(b.getContradictionIndex(), a.getContradictionIndex()))
            .forEach(fw -> System.out.printf("%s: %.2f\n", fw.name, fw.getContradictionIndex()));
        
        saveContradictionAnalysis(frameworks, nextjs, competitorAvgContradiction);
    }
    
    /**
     * 時系列分析
     */
    private static void performTimeSeriesAnalysis() throws Exception {
        System.out.println("\n=== 時系列分析 ===");
        
        // Next.jsの継続率推移（2018-2024）
        double[] nextjsRetention = {0.88, 0.90, 0.92, 0.91, 0.89, 0.76, 0.68};
        int[] years = {2018, 2019, 2020, 2021, 2022, 2023, 2024};
        
        // 線形回帰
        double slope = calculateLinearRegression(years, nextjsRetention);
        double intercept = calculateIntercept(years, nextjsRetention, slope);
        
        System.out.println("Next.js継続率の時系列推移:");
        for (int i = 0; i < years.length; i++) {
            double predicted = slope * years[i] + intercept;
            System.out.printf("%d年: 実測値=%.3f, 予測値=%.3f\n", 
                years[i], nextjsRetention[i], predicted);
        }
        
        System.out.printf("年間劣化率: %.4f/年\n", slope);
        System.out.printf("6年間総劣化: %.2fポイント\n", 
            nextjsRetention[nextjsRetention.length-1] - nextjsRetention[0]);
        
        // 2025年予測
        double prediction2025 = slope * 2025 + intercept;
        System.out.printf("2025年予測継続率: %.3f\n", prediction2025);
        
        saveTimeSeriesAnalysis(years, nextjsRetention, slope, intercept);
    }
    
    /**
     * 相関分析
     */
    private static void performCorrelationAnalysis(List<Framework> frameworks) throws Exception {
        System.out.println("\n=== 相関分析 ===");
        
        String[] metrics = {"使用率", "継続率", "ポジティブ率", "関心度", "認知度", "評価度"};
        
        // 継続率と他指標の相関
        System.out.println("継続率との相関係数:");
        for (int i = 0; i < 6; i++) {
            if (i == 1) continue; // 継続率自身はスキップ
            
            double correlation = calculateCorrelation(frameworks, 1, i);
            System.out.printf("%s: %.3f\n", metrics[i], correlation);
        }
        
        saveCorrelationAnalysis(frameworks, metrics);
    }
    
    /**
     * 線形回帰の傾き計算
     */
    private static double calculateLinearRegression(int[] x, double[] y) {
        int n = x.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        
        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
        }
        
        return (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
    }
    
    /**
     * 線形回帰の切片計算
     */
    private static double calculateIntercept(int[] x, double[] y, double slope) {
        double meanX = Arrays.stream(x).average().orElse(0.0);
        double meanY = Arrays.stream(y).average().orElse(0.0);
        return meanY - slope * meanX;
    }
    
    /**
     * 相関係数計算
     */
    private static double calculateCorrelation(List<Framework> frameworks, int feature1, int feature2) {
        int n = frameworks.size();
        double[] x = new double[n];
        double[] y = new double[n];
        
        for (int i = 0; i < n; i++) {
            double[] features = frameworks.get(i).getFeatureVector();
            x[i] = features[feature1];
            y[i] = features[feature2];
        }
        
        double meanX = Arrays.stream(x).average().orElse(0.0);
        double meanY = Arrays.stream(y).average().orElse(0.0);
        
        double numerator = 0, denomX = 0, denomY = 0;
        for (int i = 0; i < n; i++) {
            double diffX = x[i] - meanX;
            double diffY = y[i] - meanY;
            numerator += diffX * diffY;
            denomX += diffX * diffX;
            denomY += diffY * diffY;
        }
        
        return numerator / Math.sqrt(denomX * denomY);
    }
    
    // ファイル保存メソッド群
    private static void saveBasicStatistics(List<Framework> frameworks, String[] metrics) throws Exception {
        new File(RESULTS_DIR).mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "basic_statistics.txt"));
        writer.println("=== 基本統計分析結果 ===");
        writer.println("分析対象: State of JS 2024メタフレームワーク");
        writer.println("フレームワーク数: " + frameworks.size());
        writer.println();
        
        for (Framework fw : frameworks) {
            writer.printf("%s: 使用率=%.3f, 継続率=%.3f, 矛盾指数=%.2f\n", 
                fw.name, fw.usage, fw.retention, fw.getContradictionIndex());
        }
        writer.close();
    }
    
    private static void saveKMeansResults(List<Framework> frameworks, List<int[]> clusters) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "kmeans_analysis.txt"));
        writer.println("=== K-meansクラスタリング分析結果 ===");
        writer.println("アルゴリズム: 簡易K-means");
        writer.println("クラスタ数: " + clusters.size());
        writer.println("距離尺度: ユークリッド距離");
        writer.println();
        
        for (int c = 0; c < clusters.size(); c++) {
            writer.printf("クラスタ%d:\n", c);
            for (int idx : clusters.get(c)) {
                Framework fw = frameworks.get(idx);
                writer.printf("  %s (矛盾指数: %.2f)\n", fw.name, fw.getContradictionIndex());
            }
            writer.println();
        }
        writer.close();
    }
    
    private static void saveContradictionAnalysis(List<Framework> frameworks, Framework nextjs, 
                                                double competitorAvg) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "contradiction_index.txt"));
        writer.println("=== 矛盾指数分析結果 ===");
        writer.println("矛盾指数 = (使用率 / 継続率) × 100");
        writer.println();
        writer.printf("Next.js矛盾指数: %.2f\n", nextjs.getContradictionIndex());
        writer.printf("競合平均矛盾指数: %.2f\n", competitorAvg);
        writer.printf("倍率差: %.2f倍\n", nextjs.getContradictionIndex() / competitorAvg);
        writer.println();
        writer.println("全フレームワーク詳細:");
        for (Framework fw : frameworks) {
            writer.printf("%s: %.2f (使用率%.3f/継続率%.3f)\n", 
                fw.name, fw.getContradictionIndex(), fw.usage, fw.retention);
        }
        writer.close();
    }
    
    private static void saveTimeSeriesAnalysis(int[] years, double[] retention, double slope, 
                                             double intercept) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "timeseries_analysis.txt"));
        writer.println("=== 時系列分析結果 ===");
        writer.println("分析対象: Next.js継続率推移 (2018-2024)");
        writer.println();
        
        for (int i = 0; i < years.length; i++) {
            double predicted = slope * years[i] + intercept;
            writer.printf("%d年: 実測値=%.3f, 予測値=%.3f, 誤差=%.3f\n", 
                years[i], retention[i], predicted, Math.abs(retention[i] - predicted));
        }
        
        writer.printf("\n線形回帰式: y = %.6fx + %.3f\n", slope, intercept);
        writer.printf("年間劣化率: %.4f/年\n", slope);
        
        // 将来予測
        for (int year = 2025; year <= 2027; year++) {
            double prediction = slope * year + intercept;
            writer.printf("%d年予測: %.3f\n", year, prediction);
        }
        writer.close();
    }
    
    private static void saveCorrelationAnalysis(List<Framework> frameworks, String[] metrics) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "correlation_analysis.txt"));
        writer.println("=== 相関分析結果 ===");
        writer.println("継続率との相関係数:");
        
        for (int i = 0; i < 6; i++) {
            if (i == 1) continue;
            double correlation = calculateCorrelation(frameworks, 1, i);
            writer.printf("%s: %.3f\n", metrics[i], correlation);
        }
        writer.close();
    }
}