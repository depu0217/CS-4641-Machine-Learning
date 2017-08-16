fprintf('RHC - first sucessfull poll: \n');
display ( ['mean elapsed time for 1 run: ' num2str(mean(globalResults1(3,:))) ]);
display ( ['mean iterations: ' num2str(mean(globalResults1(1,:))) ]  );
display ( ['average f-val: ' num2str(mean(globalResults1(2,:))) ]  );
display ( ['best f-val: ' num2str(min(globalResults1(2,:)))]  );
accuracy_testing1 = zeros(1,3);

accuracy_testing1 = performnnet(xs1, net, test_data',test_target);

display ( ['performance testing f-val: ' num2str(accuracy_testing1)]);
accuracy_training1 = zeros(1,3);

accuracy_training1 = performnnet(xs1, net, train_d',train_l);

display ( ['performance training f-val: ' num2str(accuracy_training1)]);

fprintf('\n');
fprintf('RHC - complete sucessfull poll: \n');
display ( ['mean elapsed time for 1 run: ' num2str(mean(globalResults2(3,:))) ]);
display ( ['mean iterations: ' num2str(mean(globalResults2(1,:))) ]  );
display ( ['average f-val: ' num2str(mean(globalResults2(2,:))) ]  );
display ( ['best f-val: ' num2str(min(globalResults2(2,:)))]  );

accuracy_testing2 = performnnet(xs2, net, test_data',test_target);
display ( ['performance testing f-val: ' num2str(accuracy_testing2)]);


accuracy_training2 = performnnet(xs2, net, train_d',train_l);
display ( ['performance training f-val: ' num2str(accuracy_training2)]);