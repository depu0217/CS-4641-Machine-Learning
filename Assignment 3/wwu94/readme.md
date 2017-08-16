## **Author**

Name: Wenjun Wu

GT username: wwu94

## Project Info

Implementation Platform: Weka 3.8.1 with Student Filters extension (https://github.com/cgearhart/students-filters.)

Wilt Dataset: 

`~/wwu94/Silt/wilt.arff`  

Car Evaluation Dataset:

`~/wwu94/Car/car.arff`

## Clustering Analysis

#### K-means Clustering (KM)

​	Weka —> Clustering —> SimpleKMeans

​	MaxIteration = 10000

#### Expectation Maximization Clustering

​	Weka —> EM

​	MaxIteration = 10000

`	

## Dimensionality Reduction

#### ICA

​	`Weka --> Preprocess-->Unsupervised --> attribute--> IndependenComponents`

​	Kurtosis were calculated using `python kurtosis_calculator *.arff`

#### PCA

​	`Weka --> Preprocess-->Unsupervised --> attribute--> PrincipalComponents`

​	Eigenvalues were generated using SelectAttribute —> PrincipalComponents

#### RP

`		Weka --> Preprocess-->Unsupervised --> attribute--> RandomProjection`	

#### Information Gain Feature Selection

`	Weka --> Preprocess-->supervised --> attribute--> FeatureSelection-->Information Gain (rankers)`	

## Neural Network Learning

1. Load the dataset and then preprocess the raw dataset or load the preprocess dataset in `~/wwu94/Wilt/preprocess`  
2. Go to 'Classify' interface. Choose the MultilayerPerceptron. Use Percentage split with 70% training set.Run the neural network algorithm with parameters described in the report. 

## Clustering as feature reduction + NN learning

1. Load the preprocess dataset in `~/wwu94/Wilt/preprocess`  
2. Use AddCluster Filter and select EM or SimpleKMeans. Change parameters as described in the report.
3. Click 'Apply' and delete all attributes except 'Class' and 'Cluster'.
4. Go to 'Classify' interface. Choose the MultilayerPerceptron. Use Percentage split with 70% training set. Run the neural network algorithm with parameters described in the report. 