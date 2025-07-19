import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.clusterers.SimpleKMeans;
import weka.clusterers.HierarchicalClusterer;
import weka.attributeSelection.PrincipalComponents;
import weka.attributeSelection.Ranker;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;
import java.io.*;
import java.util.*;

/**
 * Next.js教師なし学習分析プログラム
 * State of JS 2024データを用いてNext.jsの矛盾現象を解明
 */
public class NextjsUnsupervisedAnalysis {
    
    private static final String OUTPUT_DIR = "output/";
    private static final String RESULTS_DIR = "results/";
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Next.js教師なし学習分析開始 ===");
        
        // データの準備
        Instances data = prepareData();
        System.out.println("データ準備完了: " + data.numInstances() + "インスタンス, " + data.numAttributes() + "属性");
        
        // K-meansクラスタリング
        performKMeansClustering(data);
        
        // 階層クラスタリング
        performHierarchicalClustering(data);
        
        // 主成分分析
        performPCA(data);
        
        // 矛盾指数の算出
        calculateContradictionIndex();
        
        // 時系列分析
        performTimeSeriesAnalysis();
        
        System.out.println("=== 分析完了 ===");
    }
    
    /**
     * データの準備と前処理
     */
    private static Instances prepareData() throws Exception {
        // State of JS 2024データから特徴量を抽出
        Instances data = createMetaFrameworkDataset();
        
        // 標準化
        Standardize standardize = new Standardize();
        standardize.setInputFormat(data);
        data = Filter.useFilter(data, standardize);
        
        return data;
    }
    
    /**
     * メタフレームワークデータセットの作成
     */
    private static Instances createMetaFrameworkDataset() throws Exception {
        // 属性定義
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("usage_rate"));          // 使用率
        attributes.add(new Attribute("retention_rate"));      // 継続使用率
        attributes.add(new Attribute("positivity_rate"));     // ポジティブ評価率
        attributes.add(new Attribute("interest_rate"));       // 関心度
        attributes.add(new Attribute("awareness_rate"));      // 認知度
        attributes.add(new Attribute("appreciation_rate"));   // 評価度
        
        // フレームワーク名属性（カテゴリ）
        ArrayList<String> frameworkNames = new ArrayList<>();
        frameworkNames.add("nextjs");
        frameworkNames.add("astro");
        frameworkNames.add("sveltekit");
        frameworkNames.add("nuxt");
        frameworkNames.add("remix");
        frameworkNames.add("gatsby");
        attributes.add(new Attribute("framework", frameworkNames));
        
        // データセット作成
        Instances dataset = new Instances("MetaFrameworks2024", attributes, 0);
        
        // State of JS 2024の実データを投入
        // Next.js 2024
        Instance nextjs = new DenseInstance(7);
        nextjs.setValue(attributes.get(0), 0.54);    // usage
        nextjs.setValue(attributes.get(1), 0.68);    // retention
        nextjs.setValue(attributes.get(2), 0.32);    // positivity
        nextjs.setValue(attributes.get(3), 0.43);    // interest
        nextjs.setValue(attributes.get(4), 0.99);    // awareness
        nextjs.setValue(attributes.get(5), 0.25);    // appreciation
        nextjs.setValue(attributes.get(6), "nextjs");
        dataset.add(nextjs);
        
        // Astro 2024
        Instance astro = new DenseInstance(7);
        astro.setValue(attributes.get(0), 0.23);     // usage
        astro.setValue(attributes.get(1), 0.94);     // retention
        astro.setValue(attributes.get(2), 0.34);     // positivity
        astro.setValue(attributes.get(3), 0.67);     // interest
        astro.setValue(attributes.get(4), 0.82);     // awareness
        astro.setValue(attributes.get(5), 0.27);     // appreciation
        astro.setValue(attributes.get(6), "astro");
        dataset.add(astro);
        
        // SvelteKit 2024
        Instance sveltekit = new DenseInstance(7);
        sveltekit.setValue(attributes.get(0), 0.16);  // usage
        sveltekit.setValue(attributes.get(1), 0.90);  // retention
        sveltekit.setValue(attributes.get(2), 0.27);  // positivity
        sveltekit.setValue(attributes.get(3), 0.55);  // interest
        sveltekit.setValue(attributes.get(4), 0.82);  // awareness
        sveltekit.setValue(attributes.get(5), 0.35);  // appreciation
        sveltekit.setValue(attributes.get(6), "sveltekit");
        dataset.add(sveltekit);
        
        // Nuxt 2024
        Instance nuxt = new DenseInstance(7);
        nuxt.setValue(attributes.get(0), 0.22);      // usage
        nuxt.setValue(attributes.get(1), 0.81);      // retention
        nuxt.setValue(attributes.get(2), 0.24);      // positivity
        nuxt.setValue(attributes.get(3), 0.39);      // interest
        nuxt.setValue(attributes.get(4), 0.89);      // awareness
        nuxt.setValue(attributes.get(5), 0.42);      // appreciation
        nuxt.setValue(attributes.get(6), "nuxt");
        dataset.add(nuxt);
        
        // Remix 2024
        Instance remix = new DenseInstance(7);
        remix.setValue(attributes.get(0), 0.11);     // usage
        remix.setValue(attributes.get(1), 0.80);     // retention
        remix.setValue(attributes.get(2), 0.21);     // positivity
        remix.setValue(attributes.get(3), 0.48);     // interest
        remix.setValue(attributes.get(4), 0.79);     // awareness
        remix.setValue(attributes.get(5), 0.31);     // appreciation
        remix.setValue(attributes.get(6), "remix");
        dataset.add(remix);
        
        // Gatsby 2024（参考として）
        Instance gatsby = new DenseInstance(7);
        gatsby.setValue(attributes.get(0), 0.19);    // usage
        gatsby.setValue(attributes.get(1), 0.27);    // retention
        gatsby.setValue(attributes.get(2), 0.07);    // positivity
        gatsby.setValue(attributes.get(3), 0.12);    // interest
        gatsby.setValue(attributes.get(4), 0.83);    // awareness
        gatsby.setValue(attributes.get(5), 0.14);    // appreciation
        gatsby.setValue(attributes.get(6), "gatsby");
        dataset.add(gatsby);
        
        return dataset;
    }
    
    /**
     * K-meansクラスタリング実行
     */
    private static void performKMeansClustering(Instances data) throws Exception {
        System.out.println("\n=== K-meansクラスタリング ===");
        
        // フレームワーク名以外の属性でクラスタリング
        Instances clusteringData = new Instances(data);
        clusteringData.deleteAttribute(clusteringData.numAttributes() - 1); // framework属性を削除
        
        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(3);  // エルボー法で決定した最適クラスタ数
        kmeans.setDistanceFunction(new weka.core.EuclideanDistance());
        kmeans.buildClusterer(clusteringData);
        
        System.out.println("クラスタ中心:");
        System.out.println(kmeans.getClusterCentroids());
        
        // 各フレームワークのクラスタ割り当て
        System.out.println("\nフレームワークのクラスタ割り当て:");
        for (int i = 0; i < data.numInstances(); i++) {
            Instance instance = clusteringData.instance(i);
            int cluster = kmeans.clusterInstance(instance);
            String framework = data.instance(i).stringValue(data.numAttributes() - 1);
            System.out.println(framework + " → クラスタ " + cluster);
        }
        
        // 結果をファイルに保存
        saveKMeansResults(kmeans, data);
        
        // 矛盾指数の計算
        calculateFrameworkContradictionIndex(data, kmeans, clusteringData);
    }
    
    /**
     * 階層クラスタリング実行
     */
    private static void performHierarchicalClustering(Instances data) throws Exception {
        System.out.println("\n=== 階層クラスタリング ===");
        
        // フレームワーク名以外の属性でクラスタリング
        Instances clusteringData = new Instances(data);
        clusteringData.deleteAttribute(clusteringData.numAttributes() - 1);
        
        HierarchicalClusterer hierarchical = new HierarchicalClusterer();
        hierarchical.setLinkType(new weka.core.SelectedTag(HierarchicalClusterer.TAGS_LINK_TYPE[2])); // Ward法
        hierarchical.setDistanceFunction(new weka.core.EuclideanDistance());
        hierarchical.buildClusterer(clusteringData);
        
        System.out.println("階層クラスタリング結果:");
        System.out.println(hierarchical.graph());
        
        // デンドログラム情報をファイルに保存
        saveHierarchicalResults(hierarchical, data);
    }
    
    /**
     * 主成分分析実行
     */
    private static void performPCA(Instances data) throws Exception {
        System.out.println("\n=== 主成分分析 ===");
        
        // フレームワーク名以外の属性でPCA
        Instances pcaData = new Instances(data);
        pcaData.deleteAttribute(pcaData.numAttributes() - 1);
        
        PrincipalComponents pca = new PrincipalComponents();
        pca.buildEvaluator(pcaData);
        
        System.out.println("主成分分析結果:");
        System.out.println(pca.toString());
        
        // 変換されたデータ
        Instances transformedData = pca.transformedData(pcaData);
        System.out.println("\n変換後のデータ:");
        for (int i = 0; i < transformedData.numInstances(); i++) {
            String framework = data.instance(i).stringValue(data.numAttributes() - 1);
            System.out.println(framework + ": " + transformedData.instance(i));
        }
        
        savePCAResults(pca, transformedData, data);
    }
    
    /**
     * 矛盾指数の算出
     */
    private static void calculateContradictionIndex() throws Exception {
        System.out.println("\n=== 矛盾指数の算出 ===");
        
        // Next.jsの矛盾指数 = (Usage Rate / Retention Rate) * 100
        double nextjsUsage = 0.54;
        double nextjsRetention = 0.68;
        double nextjsContradiction = (nextjsUsage / nextjsRetention) * 100;
        
        // 競合の矛盾指数
        double astroContradiction = (0.23 / 0.94) * 100;
        double sveltekitContradiction = (0.16 / 0.90) * 100;
        
        System.out.println("矛盾指数（高いほど矛盾が大きい）:");
        System.out.printf("Next.js: %.2f\n", nextjsContradiction);
        System.out.printf("Astro: %.2f\n", astroContradiction);
        System.out.printf("SvelteKit: %.2f\n", sveltekitContradiction);
        System.out.printf("Next.js vs 競合平均の差: %.2f倍\n", 
            nextjsContradiction / ((astroContradiction + sveltekitContradiction) / 2));
        
        // 結果をファイルに保存
        saveContradictionResults(nextjsContradiction, astroContradiction, sveltekitContradiction);
    }
    
    /**
     * 時系列分析実行
     */
    private static void performTimeSeriesAnalysis() throws Exception {
        System.out.println("\n=== 時系列分析 ===");
        
        // Next.jsの時系列データ（2018-2024）
        double[] nextjsRetention = {0.88, 0.90, 0.92, 0.91, 0.89, 0.76, 0.68};
        int[] years = {2018, 2019, 2020, 2021, 2022, 2023, 2024};
        
        // 線形回帰による劣化トレンド分析
        double slope = calculateLinearRegression(years, nextjsRetention);
        
        System.out.println("Next.js満足度の時系列変化:");
        for (int i = 0; i < years.length; i++) {
            System.out.printf("%d年: %.2f\n", years[i], nextjsRetention[i]);
        }
        
        System.out.printf("年間劣化率: %.3f/年\n", slope);
        System.out.printf("6年間での総劣化: %.2fポイント\n", 
            nextjsRetention[nextjsRetention.length-1] - nextjsRetention[0]);
        
        saveTimeSeriesResults(years, nextjsRetention, slope);
    }
    
    /**
     * 線形回帰による傾き計算
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
     * フレームワーク矛盾指数計算
     */
    private static void calculateFrameworkContradictionIndex(Instances data, SimpleKMeans kmeans, 
                                                           Instances clusteringData) throws Exception {
        System.out.println("\nフレームワーク別矛盾分析:");
        
        for (int i = 0; i < data.numInstances(); i++) {
            String framework = data.instance(i).stringValue(data.numAttributes() - 1);
            double usage = data.instance(i).value(0);
            double retention = data.instance(i).value(1);
            int cluster = kmeans.clusterInstance(clusteringData.instance(i));
            
            double contradiction = (usage / retention) * 100;
            
            System.out.printf("%s: 使用率%.2f, 満足度%.2f, 矛盾指数%.2f, クラスタ%d\n", 
                framework, usage, retention, contradiction, cluster);
        }
    }
    
    // ファイル保存メソッド群
    private static void saveKMeansResults(SimpleKMeans kmeans, Instances data) throws Exception {
        new File(RESULTS_DIR).mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "kmeans_results.txt"));
        writer.println("=== K-meansクラスタリング結果 ===");
        writer.println("最適クラスタ数: 3");
        writer.println("アルゴリズム: K-means");
        writer.println("距離尺度: ユークリッド距離");
        writer.println("\nクラスタ中心:");
        writer.println(kmeans.getClusterCentroids());
        writer.close();
    }
    
    private static void saveHierarchicalResults(HierarchicalClusterer hierarchical, Instances data) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "hierarchical_results.txt"));
        writer.println("=== 階層クラスタリング結果 ===");
        writer.println("連結方法: Ward法");
        writer.println("距離尺度: ユークリッド距離");
        writer.println("\nデンドログラム情報:");
        writer.println(hierarchical.graph());
        writer.close();
    }
    
    private static void savePCAResults(PrincipalComponents pca, Instances transformedData, Instances originalData) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "pca_results.txt"));
        writer.println("=== 主成分分析結果 ===");
        writer.println(pca.toString());
        writer.println("\n変換後データ:");
        for (int i = 0; i < transformedData.numInstances(); i++) {
            String framework = originalData.instance(i).stringValue(originalData.numAttributes() - 1);
            writer.println(framework + ": " + transformedData.instance(i));
        }
        writer.close();
    }
    
    private static void saveContradictionResults(double nextjs, double astro, double sveltekit) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "contradiction_analysis.txt"));
        writer.println("=== 矛盾指数分析結果 ===");
        writer.printf("Next.js矛盾指数: %.2f\n", nextjs);
        writer.printf("Astro矛盾指数: %.2f\n", astro);
        writer.printf("SvelteKit矛盾指数: %.2f\n", sveltekit);
        writer.printf("Next.js vs 競合平均の差: %.2f倍\n", 
            nextjs / ((astro + sveltekit) / 2));
        writer.close();
    }
    
    private static void saveTimeSeriesResults(int[] years, double[] retention, double slope) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "timeseries_analysis.txt"));
        writer.println("=== 時系列分析結果 ===");
        writer.println("Next.js満足度推移（2018-2024）:");
        for (int i = 0; i < years.length; i++) {
            writer.printf("%d年: %.3f\n", years[i], retention[i]);
        }
        writer.printf("年間劣化率: %.4f/年\n", slope);
        writer.close();
    }
}