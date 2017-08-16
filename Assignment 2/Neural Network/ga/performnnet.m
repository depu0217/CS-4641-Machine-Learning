function accuracy = mse_test(x, net, inputs, targets)
% 'x' contains the weights and biases vector
% in row vector form as passed to it by the
% genetic algorithm. This must be transposed
% when being set as the weights and biases
% vector for the network.

% To set the weights and biases vector to the
% one given as input

net = setwb(net, x');

% To evaluate the ouputs based on the given
% weights and biases vector
y = net(inputs);

nnResult = vec2ind(y);
accuracy = sum(nnResult' == targets)/length(nnResult);
end