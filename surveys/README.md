# State of JS & React 2024 Survey Analysis

## Data Sources
The data for this analysis comes from the following surveys, which provide insights into the usage and satisfaction of various JavaScript frameworks and libraries.

### State of JS 2024
The data comes from the [State of JS 2024 survey](https://stateofjs.com/), which provides insights into the usage and satisfaction of various JavaScript frameworks and libraries.

* [Meta-Frameworks Ratios Over Time](./state-of-js-2024/meta_frameworks_ratios.json)

* [Used at Work](./state-of-js-2024/meta_frameworks_work.json)
Which of these tools do you use in a professional context?(Multiple)

* [Meta-framework satisfaction](./state-of-js-2024/meta_frameworks_happiness.json)
How satisfied are you with the current state of the meta framework?

* [メタフレームワークのつらいところ](./state-of-js-2024/meta_frameworks_pain_points.json)
メタフレームワークを使うときに、つらいと感じるところは何ですか？(Freeform, Multiple)

### State of React 2024
The data comes from the [State of React 2024 survey](https://stateofreact.com/), which provides insights into React ecosystem usage and satisfaction.

* [All Features](./state-of-react-2024/all_features.json) (4,429 lines)
  Comprehensive data about React features including awareness, usage, satisfaction, and retention rates.

* [All Tools Experience](./state-of-react-2024/all_tools_experience.json) (4,706 lines)
  Experience levels with various React ecosystem tools and libraries.

* [Hooks Pain Points](./state-of-react-2024/hooks_pain_points.json) (271 lines)
  What are the pain points when using React Hooks? (Freeform, Multiple)

* [Main APIs Pain Points](./state-of-react-2024/main_apis_pain_points.json) (248 lines)
  What are the pain points when using React's main APIs? (Freeform, Multiple)

* [New APIs Pain Points](./state-of-react-2024/new_apis_pain_points.json) (239 lines)
  What are the pain points when using React's new APIs? (Freeform, Multiple)

* [Component Libraries Pain Points](./state-of-react-2024/omponent_libraries_pain_points.json) (157 lines)
  What are the pain points when using React component libraries? (Freeform, Multiple)

## Data Structure
All data files are in JSON format and contain structured survey responses with metadata about:
- Feature/tool awareness and usage rates
- Satisfaction and retention metrics
- Pain points and challenges
- Demographic and experience information

## Usage
These datasets are used for unsupervised learning analysis to understand:
- Next.js developer satisfaction patterns
- Framework usage correlations
- Pain point clustering
- Feature adoption trends
