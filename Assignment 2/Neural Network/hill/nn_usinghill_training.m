function nn_usinghill_training(hiddenlayer, neurons, classes)
%% This function calculates the accuracy of Neural Network on different Training Data Size

% trainfunc - specified training function
% hiddenlayer - the number of hidden layers
% n - the number of neurons in each layer
% the number of classes present in the dataset

load('wilt.mat');
s = round(length(training_label)/5);
for i = 1:5
    globalResults1 = [];
    globalResults2 = [];
    xs1 = [];
    xs2 = [];
    if (i <= 4)
        train_d = training_data(1:i*s,:);
        train_l = training_target(1:i*s,:);
    else
        train_d = training_data;
        train_l = training_target;
    end
    target = zeros(classes,length(train_l));
    for x = 1:classes
        target(x,:) = train_l==(x);
    end
    n = ones(1,hiddenlayer) * neurons;
    [n_attr, ~]  = size(training_data');
    ps_opts1 = psoptimset ( 'CompletePoll', 'off', 'Display', 'off', 'MaxIter', 50); %, 'TimeLimit', 120 );
    
    % There is n_attr attributes in dataset, and there are n neurons so there
    % are total of n_attr*n input weights (uniform weight)
    initial_il_weights = ones(1, n_attr*n)/(n_attr*n);
    % There are n bias values, one for each neuron (random)
    initial_il_bias    = rand(1, n);
    % There is n_class output, so there are total of n_class*n output weights
    % (uniform weight)
    initial_ol_weights = ones(1, classes*n)/(classes*n);
    % There are n_class bias values, one for each output neuron (random)
    initial_ol_bias    = rand(1, classes);
    % starting values
    starting_values = [initial_il_weights, initial_il_bias, ...
        initial_ol_weights, initial_ol_bias];
    
    % Initialize neural network
    net = feedforwardnet(n);
    net = configure(net, training_data', training_label');
    
    
    fun = @(w) mse_test(w, net, training_data', training_label');
    
    %     Train the Network
    tic
    [x1, fval1, flag1, output1] = patternsearch(fun, starting_values, [], [],[],[], -1e5, 1e5, ps_opts1);
    xs1 = [ xs1;x1 ];
    globalResults1(3,i) = toc;
    tic
    ps_opts2 = psoptimset ( 'CompletePoll', 'on', 'Display', 'off', 'MaxIter', 50); %, 'TimeLimit', 120 );
    [x2, fval2, flag2, output2] = patternsearch(fun, starting_values, [], [],[],[], -1e5, 1e5, ps_opts2);
    globalResults2(3,i) = toc;
    xs2 = [ xs2;x2 ];
    globalResults1(1,i) = output1.iterations;
    globalResults1(2,i) = fval1;
    globalResults2(1,i) = output2.iterations;
    globalResults2(2,i) = fval2;
    print2;
end
