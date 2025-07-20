# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **Computer Simulation final project** for Tokyo City University focusing on **unsupervised learning analysis of Next.js developer satisfaction**. The project analyzes the paradox between Next.js's high usage and low user satisfaction using machine learning techniques with **State of JS 2024** real-world survey data.

**Key Details:**
- **Course**: Computer Simulation (コンピュータシミュレーション)
- **Assignment Type**: Group project (3 members) 
- **Due Date**: August 1, 2025, 23:59
- **Primary Language**: Java (required by course)
- **ML Library**: Weka 3.8.6 (official library)
- **Analysis Focus**: Unsupervised learning (K-means, hierarchical clustering, PCA)
- **Major Discovery**: Next.js forms isolated cluster with 3.82x contradiction index vs competitors

## Development Commands

### Core Analysis Execution
```bash
# Compile Weka analysis program
javac -cp "weka/weka-3-8-6/weka.jar:." src/NextjsWekaAnalysis.java -d .

# Run complete analysis (generates all results and CSV files)
java -cp "weka/weka-3-8-6/weka.jar:." NextjsWekaAnalysis
```

### Visualization
```bash
# Create graphs using provided CSV data and guide
# See GRAPH_CREATION_GUIDE.md for detailed instructions
```

## Project Structure

```bash
cs-final/
├── src/
│   └── NextjsWekaAnalysis.java         # Main Weka analysis program
├── weka/
│   └── weka-3-8-6/
│       └── weka.jar                    # Weka 3.8.6 official library
├── csv_data/                           # Generated visualization datasets
│   ├── framework_comparison.csv        # Framework contradiction analysis
│   ├── kmeans_clusters.csv            # K-means clustering results
│   ├── nextjs_timeseries.csv          # Time series degradation data
│   ├── correlation_matrix.csv         # Retention correlation analysis
│   └── pca_results.csv                # Principal component analysis
├── results/                           # Detailed analysis outputs
│   ├── weka_kmeans_results.txt        # K-means detailed results
│   ├── weka_hierarchical_results.txt  # Hierarchical clustering
│   └── weka_contradiction_analysis.txt # Contradiction index analysis
├── output/
│   └── meta_frameworks_analysis.arff  # Weka-format dataset
└── surveys/                           # Source data (State of JS 2024)
    └── state-of-js-2024/              # Raw survey JSON files
```

## Technical Architecture

### Analysis Program Structure
The main analysis is implemented in `src/NextjsWekaAnalysis.java` which:

1. **Data Preparation**: Loads State of JS 2024 framework data (6 frameworks)
2. **Standardization**: Applies Z-score normalization using Weka's Standardize filter
3. **K-means Analysis**: Uses elbow method to determine optimal K=4 clusters
4. **Hierarchical Clustering**: Ward linkage method for framework relationship analysis
5. **Contradiction Index**: Custom metric = (usage_rate/retention_rate) × 100
6. **Output Generation**: Creates 5 CSV files for visualization + detailed result files

### Key Findings Architecture
- **Framework Segmentation**: 3 distinct patterns identified
  - Ideal Type: Astro, SvelteKit, Remix (low contradiction ~18.67)
  - Contradiction Type: Next.js, Nuxt (high contradiction ~53.29) 
  - Decline Type: Gatsby (extreme contradiction 70.37)
- **Next.js Isolation**: Forms separate cluster showing unique contradiction pattern
- **Time Series Degradation**: 6-year retention decline (88% → 68%)

### Data Flow
```
State of JS 2024 JSON → Java Program → Weka Instances → 
Clustering Analysis → CSV Outputs → Visualization → Report Integration
```

## Submission Requirements

### Mandatory Deliverables (ZIP format)
1. **Analysis Report (PDF)**:
   - Title page with student IDs and names
   - Methodology explanation in own words
   - Data source documentation
   - Results interpretation and insights (35% of grade)
   
2. **Java Programs**: All analysis code, even if other languages used
3. **Dataset**: Both original CSV and processed ARFF files

### Evaluation Criteria (100 points)
- Data novelty: 5 points
- Accuracy: 20 points  
- Methodology innovation: 15 points
- **Results interpretation: 35 points** (most important)
- Problem formulation: 25 points

## Working with this Project

### Core Analysis Workflow
1. **Main Program**: `NextjsWekaAnalysis.java` contains complete analysis pipeline
2. **Weka Dependency**: Always include `weka/weka-3-8-6/weka.jar` in classpath
3. **Data Generation**: Running analysis automatically creates all CSV files in `csv_data/`
4. **Visualization**: Use `GRAPH_CREATION_GUIDE.md` with generated CSVs
5. **Report Updates**: Results integrate into `reports/report2.md`

### Key Implementation Details
- **Fixed Seed**: K-means uses seed=42 for reproducibility
- **Standardization Required**: All features Z-score normalized before clustering
- **ARFF Format**: Weka-compatible dataset saved to `output/meta_frameworks_analysis.arff`
- **Error Handling**: PCA disabled due to matrix library conflicts

### Critical Analysis Components
- **Contradiction Index Calculation**: Core innovation quantifying framework paradox
- **Elbow Method Implementation**: Determines optimal cluster count scientifically
- **Time Series Regression**: Linear trend analysis for satisfaction degradation
- **CSV Export Functions**: Generate visualization-ready datasets

### Course Requirements Compliance
- Java-only implementation (no Python/R in submission)
- Weka official library usage (not custom implementations)
- Original dataset (State of JS 2024, not standard ML datasets)
- Reproducible analysis with documented methodology
- Always respond in 日本語
