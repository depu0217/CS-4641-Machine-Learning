fprintf('RHC - first sucessfull poll: \n');
display ( ['mean elapsed time for 1 run: ' num2str(mean(globalResults1(3,:))) ]);
display ( ['mean iterations: ' num2str(mean(globalResults1(1,:))) ]  );
display ( ['average f-val: ' num2str(mean(globalResults1(2,:))) ]  );
display ( ['best f-val: ' num2str(min(globalResults1(2,:)))]  );
accuracy_testing1 = zeros(1,3);
for j = 1:2
    x = xs1(j,:);
    accuracy_testing1(j) = performnnet(x, net, test_data',test_target);
end
display ( ['performance testing f-val: ' num2str(mean(accuracy_testing1))]);
accuracy_training1 = zeros(1,3);
for j = 1:2
    x = xs1(j,:);
    accuracy_training1(j) = performnnet(x, net, training_data',training_target);
end
display ( ['performance training f-val: ' num2str(mean(accuracy_training1))]);

fprintf('\n');
fprintf('RHC - complete sucessfull poll: \n');
display ( ['mean elapsed time for 1 run: ' num2str(mean(globalResults2(3,:))) ]);
display ( ['mean iterations: ' num2str(mean(globalResults2(1,:))) ]  );
display ( ['average f-val: ' num2str(mean(globalResults2(2,:))) ]  );
display ( ['best f-val: ' num2str(min(globalResults2(2,:)))]  );
accuracy_testing2 = zeros(1,3);
for j = 1:2
    x = xs2(j,:);
    accuracy_testing2(j) = performnnet(x, net, test_data',test_target);
end
display ( ['performance testing f-val: ' num2str(mean(accuracy_testing2))]);

accuracy_training2 = zeros(1,3);
for j = 1:2
    x = xs2(j,:);
    accuracy_training2(j) = performnnet(x, net, training_data',training_target);
end
display ( ['performance training f-val: ' num2str(mean(accuracy_training2))]);