function Neural(fold,n,classes)
%% This function use x-fold cross validation to explore the performance of 
% Neural Network with different hyper parameters. 

% fold - the number of fold to perform cross validation
% n - the number of neurons
% the number of classes present in the dataset

load('training_test.mat');

indices = crossvalind('Kfold',training_label,fold);
func = {'trainlm','trainbfg','trainrp','trainscg','traincgb','traincgf','traincgp','trainoss','traingdx'};
rate = zeros(length(func),fold);
hiddenLayerSize = 1:10;

for k = 1:length(func)
    disp(['Training function: ' func{k}]);
    for j = 1:length(hiddenLayerSize)
        disp(['hidden layer size: ' num2str(j)]);
        nnp = zeros(1,10);
        for i = 1:fold
            valid = (indices == i); training = ~valid;
            valid_d = training_data(valid,:);
            valid_l = training_label(valid);
            
            train_d = training_data(training,:);
            train_l = training_label(training);
            
            target = zeros(classes,length(train_l));
            for x = 1:classes
                target(x,:) = train_l==x;
            end
            
            neurons = ones(1,i) * n;
            
            net = fitnet(neurons);
            net.trainFcn = func{k};
            net.trainParam.showWindow = false;
            
            %     Train the Network
            net = train(net,train_d',target);
            
            % Test the Network
            result = net(valid_d');
            nnResult = vec2ind(result);
            nnp(i) = sum(nnResult' == valid_l)/length(nnResult);
        end
        rate(k,j) = mean(nnp);
    end
    
end
save(['neural_result_' num2str(n) '.mat']);
end


