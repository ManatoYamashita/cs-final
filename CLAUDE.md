# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **Computer Simulation final project** for Tokyo City University focusing on **unsupervised learning analysis of Next.js developer satisfaction**. The project analyzes the paradox between Next.js's high usage and low user satisfaction using machine learning techniques.

**Key Details:**
- **Course**: Computer Simulation (コンピュータシミュレーション)
- **Assignment Type**: Group project (3 members)
- **Due Date**: August 1, 2025, 23:59
- **Primary Language**: Java (required by course)
- **ML Library**: Weka (recommended)
- **Analysis Focus**: Unsupervised learning (K-means, hierarchical clustering, PCA)

## Development Commands


## Project Structure

```bash
cs-final/
├── CLAUDE.md                      # This file (Claude Code instructions)
├── README.md                      # Project description (Japanese)
├── task.md                        # Assignment requirements (Japanese)
└── reports/                       # Analysis reports
    ├── REPORTS.md                 # Report template
    └── report2.md                 # Next.js unsupervised learning analysis report
```

## Technical Architecture

### Data Analysis Pipeline
1. **Data Collection**: State of JS 2024 survey data
2. **Preprocessing**: CSV → ARFF conversion, missing value imputation
3. **Analysis Methods**:
   - K-means clustering (user segmentation)
   - Hierarchical clustering (framework competition analysis)
   - PCA (feature utilization patterns)
4. **Output**: Java programs + analysis report (PDF)

### Data Requirements
- **Format**: CSV input → ARFF for Weka
- **Target Variable**: Must be rightmost column
- **Missing Values**: Must be imputed (mean for numeric, mode for categorical)
- **Header Row**: Required with feature names

### Key Analysis Areas
1. **Developer Segmentation**: Satisfaction patterns by user characteristics
2. **Framework Competition**: Next.js vs Astro/SvelteKit/Nuxt.js usage patterns  
3. **Feature Usage**: App Router, Server Components, API Routes correlation with satisfaction

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

### When adding code:
- **Always use Java** for core analysis (course requirement)
- Include Weka library dependencies
- Follow data preprocessing pipeline (CSV → missing value handling → ARFF)
- Place target variable in rightmost column

### When writing reports:
- Use provided template in `reports/REPORTS.md`
- Include detailed methodology explanations
- Focus heavily on results interpretation (highest scoring section)
- Document all data sources and preprocessing steps

### Important Constraints:
- Cannot use standard datasets (iris, housing, breast-cancer, etc.)
- Must include reproducible analysis conditions
- Java code required even if prototyping in other languages
- All visualizations must be reproducible in Excel if using other tools
- Always respose in 日本語
