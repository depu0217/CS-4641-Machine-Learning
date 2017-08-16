package assignment4;

import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.blockdude.BlockDude;
import burlap.domain.singleagent.blockdude.BlockDudeLevelConstructor;
import burlap.domain.singleagent.blockdude.BlockDudeTF;
import burlap.domain.singleagent.blockdude.BlockDudeVisualizer;
import burlap.oomdp.auxiliary.stateconditiontest.TFGoalCondition;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.common.GoalBasedRF;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.visualizer.Visualizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BlockDudePI {
    public static void main(String [] args){
        Scanner reader = new Scanner(System.in);
        System.out.println("Which level do you want to select?\n1. easy\n2. hard");

        BlockDude bd = new BlockDude();

        Domain domain = bd.generateDomain();

        TerminalFunction tf = new BlockDudeTF();
        RewardFunction rf =  new GoalBasedRF(new TFGoalCondition(tf), 5., -0.1);;

        //get initial state with agent in 0
        State s = BlockDudeLevelConstructor.getLevel1(domain);

        if( reader.nextInt() == 2 )
        {
            s = BlockDudeLevelConstructor.getLevel3(domain);
        }

        final long startTime = System.currentTimeMillis();

        PolicyIteration pi = new PolicyIteration(domain, rf, tf, 0.99, new SimpleHashableStateFactory(),
               0.01, 500, 10000);

        //run planning from our initial state
        Policy p = pi.planFromState(s);

        final long endTime = System.currentTimeMillis();

        EpisodeAnalysis ea = p.evaluateBehavior(s, rf, tf);

        double sum = 0;
        for(Double r : ea.rewardSequence) {
            sum += r;
        }

        System.out.println("Runtime: " + (endTime - startTime) + " milliseconds");
        System.out.println("Steps taken to exit: " + ea.actionSequence.size());
        System.out.println("Total Reward: " + sum);

        List<EpisodeAnalysis> lea = new LinkedList<EpisodeAnalysis>();
        lea.add(ea);

        Visualizer v = BlockDudeVisualizer.getVisualizer(30 ,30);
        new EpisodeSequenceVisualizer(v, domain, lea);


    }

}
