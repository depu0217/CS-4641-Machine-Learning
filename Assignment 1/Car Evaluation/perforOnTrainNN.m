function perforOnTrainNN(trainfunc, hiddenlayer, neurons, classes)
%% This function calculates the accuracy of Neural Network on different Training Data Size 

% trainfunc - specified training function
% hiddenlayer - the number of hidden layers
% n - the number of neurons in each layer
% the number of classes present in the dataset
load('training_test.mat');
s = round(length(training_label)/5);
TestRate = zeros(1,5);
TrainRate = zeros(1,5);
for i = 1:5
    if (i <= 4)
        train_d = training_data(1:i*s,:);
        train_l = training_label(1:i*s,:);
    else
        train_d = training_data;
        train_l = training_label;
    end
    target = zeros(classes,length(train_l));
    for x = 1:classes
        target(x,:) = train_l==(x);
    end
    n = ones(1,hiddenlayer) * neurons;
    
    net = fitnet(n);
    net.trainFcn = trainfunc;
    net.trainParam.showWindow = false;
    %     Train the Network
    tic
    net = train(net,train_d',target);
    toc
    
    % Test the Network
    result = net(test_data');
    nnResult = vec2ind(result);
    TestRate(i) = sum(nnResult' == test_label)/length(nnResult);

    
    
    result = net(train_d');
    nnResult = vec2ind(result);
    TrainRate(i) = sum(nnResult' == train_l)/length(nnResult);  
  
                        
    save('learning-curve.mat');
end
