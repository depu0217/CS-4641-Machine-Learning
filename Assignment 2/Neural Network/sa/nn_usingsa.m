clear all
clc

max_runs = 3;
xs = [];

load wilt.mat
globalResults = zeros(3,max_runs);

% Number of neurons
n = 10;

% Number of attributes and number of classifications
[n_attr, ~]  = size(training_data');
[n_class, ~] = size(training_label');

% Initialize neural network
net = feedforwardnet(n);

% Configure the neural network for this dataset
net = configure(net, training_data', training_label'); %view(net);

fun = @(w) mse_test(w, net, training_data', training_label');

% Unbounded
lb = -Inf;
ub = Inf;
Temp = [0.01 0.1 0.5];

% Add 'Display' option to display result of iterations
for t = 1:length(Temp)
    xs = [];
    globalResults = zeros(3,max_runs);
    for run = 1:max_runs
        % There is n_attr attributes in dataset, and there are n neurons so there
        % are total of n_attr*n input weights (uniform weight)
        initial_il_weights = ones(1, n_attr*n)/(n_attr*n);
        % There are n bias values, one for each neuron (random)
        initial_il_bias    = rand(1, n);
        % There is n_class output, so there are total of n_class*n output weights
        % (uniform weight)
        initial_ol_weights = ones(1, n_class*n)/(n_class*n);
        % There are n_class bias values, one for each output neuron (random)
        initial_ol_bias    = rand(1, n_class);
        % starting values
        starting_values = [initial_il_weights, initial_il_bias, ...
            initial_ol_weights, initial_ol_bias];
        tic
        options = anneal();
        options.InitTemp = Temp(t);
        options.Verbosity =0;
        options.Generator =  @(x) (x+(randperm(length(x))==length(x))*randn);
        
        [x, fval, output] = anneal(fun, starting_values, options);
        globalResults(3,run) = toc;
        xs = [ xs;x ];
        globalResults(1,run) = output;
        globalResults(2,run) = fval;
    end
    save(['result_' num2str(Temp(t)) '.mat']);
    fprintf('Simulated Annealing with initial Temp %d: \n\n', Temp(t));
    display ( ['mean elapsed time for 1 run: ' num2str(mean(globalResults(3,:))) ]);
    display ( ['mean f-evals: ' num2str(mean(globalResults(1,:))) ]  );
    display ( ['average f-val: ' num2str(mean(globalResults(2,:))) ]  );
    display ( ['best f-val: ' num2str(min(globalResults(2,:)))]  );
    [~, bestindex] = min(globalResults(2,:));
    display ( ['performance f-val: ' num2str( performnnet(xs(bestindex,:), net, test_data',test_target)) ]);   
    
end
