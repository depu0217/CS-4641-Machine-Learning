fprintf('Simulated Annealing : \n');
display ( ['mean elapsed time for 1 run: ' num2str(mean(globalResults(3,:))) ]);
display ( ['mean iterations: ' num2str(mean(globalResults(1,:))) ]  );
display ( ['average f-val: ' num2str(mean(globalResults(2,:))) ]  );
display ( ['best f-val: ' num2str(min(globalResults(2,:)))]  );
accuracy_testing = zeros(1,3);
for j = 1:3
    x = xs(j,:);
    accuracy_testing(j) = performnnet(x, net, test_data',test_target);
end
display ( ['performance testing f-val: ' num2str(mean(accuracy_testing))]);
accuracy_training = zeros(1,3);
for j = 1:3
    x = xs(j,:);
    accuracy_training(j) = performnnet(x, net, training_data',training_target);
end
display ( ['performance training f-val: ' num2str(mean(accuracy_training))]);