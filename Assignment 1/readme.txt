Submission detail: 
	Assignment 1 --> Car Evaluation (dataset and functions)
				--> cartest.arff: testing data
				--> cartrain.arff: training data (100%)
				--> cartrain.arff-xx%: partial training data
				--> training_test.mat: training and testing input for MATLAB
		--> Wilt (dataset and functions)
				--> wilttest.arff: testing data
				--> wilttrain.arff: training data (100%)
				--> wilttrain.arff-xx%: partial training data
				--> training_test.mat: training and testing input for MATLAB

1. Experiments of Decision tree, Boosting, kNN and SVM were performed using Weka 3.8 [3], which is a data mining software in Java (http://www.cs.waikato.ac.nz/ml/weka/). Experiments of Neural Network were performed using MATLAB Neural Nets toolbox (https://www.mathworks.com/products/neural-network.html). 

	a.	For Decision tree, I used J48 tree implementation from Weka GUI. For pruned trees, the confidence factor is 0.25.
	b.	For Boosting, I used AdaboostM1 implementation from Weka GUI. The base classifier is J48 trees (pruned and unpruned). For pruned trees, the confidence factor is 0.1.
	c.	For kNN, I used IBK implementation from Weka GUI. The k values explored are 1,3,5,7,...25,30,40,50,..100. 
	d.	For SVM, I used SMO implementation from Weka GUI. Different kernel functions explored are Poly Kernel, RBF Kernel, Puk. 
	e.	For the details about hyperparameters and experiments, please refer to the report. 
	f.	For the Neural Network, I used the Neural Network implementation from MATLAB (https://www.mathworks.com/help/nnet/ref/fitnet.html). 
	Neural.m included in each dataset folder is a MATLAB function that use cross validation to examine the performance of NN with different hyperparameters and saves the result to a .mat file. 
	perforOnTrainNN.m is a function that calculates the accuracy of Neural Network on different Training Data Size with specific hyperparameter values. This function saves the result to a .mat file. 


2. I used two datasets from UCI database in this assignment. The first dataset the Wilt dataset [1], which can be found at https://archive.ics.uci.edu/ml/datasets/Wilt. The second dataset is the Car Evaluation dataset [2], which can be found at https://archive.ics.uci.edu/ml/datasets/Car+Evaluation. The two datasets were split into several .arff files to be input to Weka GUI. The equivalent sub-datasets are stored in training_test.mat to be input to MATLAB. See car.names in "Car Evaluation" folder for detailed data description. See wilt.names in "Wilt" folder for detailed data description. 


Reference
[1]	Johnson, B., Tateishi, R., Hoan, N., 2013. A hybrid pansharpening approach and multiscale object-based image analysis for mapping diseased pine and oak trees. International Journal of Remote Sensing, 34 (20), 6969-6982.
[2]	M. Bohanec and V. Rajkovic: Knowledge acquisition and explanation for multi-attribute decision making. In 8th Intl Workshop on Expert Systems and their Applications, Avignon, France. pages 59-78, 1988. 
[3] Mark Hall, Eibe Frank, Geoffrey Holmes, Bernhard Pfahringer, Peter Reutemann, and Ian H. Witten (2009). The WEKA Data Mining Software: An Update. SIGKDD Explorations, Volume 11, Issue 1.