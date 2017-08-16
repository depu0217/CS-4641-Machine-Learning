package assignment4;

import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.auxiliary.StateReachability;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.ValueFunctionVisualizerGUI;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.blockdude.BlockDude;
import burlap.domain.singleagent.blockdude.BlockDudeLevelConstructor;
import burlap.domain.singleagent.blockdude.BlockDudeTF;
import burlap.domain.singleagent.blockdude.BlockDudeVisualizer;
import burlap.domain.singleagent.frostbite.FrostbiteDomain;
import burlap.domain.singleagent.frostbite.FrostbiteRF;
import burlap.domain.singleagent.frostbite.FrostbiteTF;
import burlap.domain.singleagent.frostbite.FrostbiteVisualizer;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldTerminalFunction;
import burlap.domain.singleagent.lunarlander.LLVisualizer;
import burlap.domain.singleagent.lunarlander.LunarLanderDomain;
import burlap.domain.singleagent.lunarlander.LunarLanderRF;
import burlap.domain.singleagent.lunarlander.LunarLanderTF;
import burlap.oomdp.auxiliary.stateconditiontest.TFGoalCondition;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.singleagent.common.GoalBasedRF;
import burlap.oomdp.singleagent.common.VisualActionObserver;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.visualizer.Visualizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BlockDudeVI {
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

        //setup vi with 0.99 discount factor, a value
        //function initialization that initializes all states to value 0, and which will
        //run for at most 10000 iterations over the state space
        ValueIteration vi = new ValueIteration(domain, rf, tf, 0.99, new SimpleHashableStateFactory(),
               0.01, 10000);

        //run planning from our initial state
        Policy p = vi.planFromState(s);

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
