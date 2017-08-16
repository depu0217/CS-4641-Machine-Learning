package assignment4;

import burlap.behavior.learningrate.SoftTimeInverseDecayLR;
import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.policy.GreedyDeterministicQPolicy;
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.RandomPolicy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.auxiliary.StateReachability;
import burlap.behavior.singleagent.auxiliary.performance.LearningAlgorithmExperimenter;
import burlap.behavior.singleagent.auxiliary.performance.PerformanceMetric;
import burlap.behavior.singleagent.auxiliary.performance.TrialMode;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.ValueFunctionVisualizerGUI;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.LearningAgentFactory;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.valuefunction.ValueFunctionInitialization;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldTerminalFunction;
import burlap.oomdp.auxiliary.stateconditiontest.TFGoalCondition;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.singleagent.common.GoalBasedRF;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class GridWorldQL {
    public static void main(String [] args) throws FileNotFoundException{
    	double[] reward = {-0.1};
		double[] discount = {0.99,0.95,0.9,0.8};
		double[] qInit = {0.1, 1, 10, 100};
		double[] epsilon = {0.05,0.1,0.4,0.7};
		double[] learnrate = {0.1, 0.3, 0.5, 0.8,1};
        Scanner reader = new Scanner(System.in);
        
        OutputStream outs = System.out;
		PrintStream dos = new PrintStream(outs);
		
		String head = "level";
		System.setOut(dos);
		//System.out.println(head);
  		

        System.out.println("What domain is tested?\n1. 11 cells\n2. Four Rooms\n3. Maze");
        GridWorldDomain gwd = new GridWorldDomain(11, 11);
        gwd.setMapToFourRooms();

        //terminate in top right corner
        TerminalFunction tf = new GridWorldTerminalFunction(10, 10);
        int agentX = 0, agentY = 0;
        
        new File("./output/"+head).mkdirs();
		File file = new File("./output/"+head+"/out_GW"); //Your file
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);

        switch (reader.nextInt()) {
            case 1:
                gwd = new GridWorldDomain(new int[][] {
                        {0,0,0},
                        {0,1,0},
                        {0,0,0},
                        {0,0,0}
                });
                tf = new GridWorldTerminalFunction(3,2);
                break;
            case 2:
                gwd = new GridWorldDomain(11, 11);
                gwd.setMapToFourRooms();
                break;
            case 3:
                gwd = new GridWorldDomain(new int[][] {
                	{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {0,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1},
                    {0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1},
                    {0,0,0,1,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,0,0,0,0,1},
                    {0,1,1,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1},
                    {0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,1},
                    {1,1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1},
                    {0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,1,0,0,0,1},
                    {0,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,0,1},
                    {0,1,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,1},
                    {0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1},
                    {0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,1},
                    {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1},
                    {0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,1},
                    {1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,0,1,0,1,0,1},
                    {0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,1},
                    {0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1},
                    {0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,1},
                    {0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,0,1,0,1},
                    {0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,1},
                    {1,1,0,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1},
                    {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
                    {0,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1},
                    {0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1},
                    {0,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,1,1,0,1,1,1},
                    {0,0,0,1,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,1},
                    {1,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1,1,0,1},
                    {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,1},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,0,1,1,1},
                    {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,0,0,1},
                    {1,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1},
                    {0,0,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
                    {0,1,0,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1},
                    {0,1,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,1},
                    {0,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1},
                    {0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,1},
                    {1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1},
                    {0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                });
                tf = new GridWorldTerminalFunction(0, 38);
                agentX = 39;
                agentY = 0;
                break;
        }


        //only go in intended directon 80% of the time
        gwd.setProbSucceedTransitionDynamics(0.8);

        final Domain domain = gwd.generateDomain();

        //get initial state with agent in 0,0
        final State s = GridWorldDomain.getOneAgentNoLocationState(domain);
        GridWorldDomain.setAgent(s, agentX, agentY);
        

        //all transitions return -1
        RewardFunction rf = new GoalBasedRF(new TFGoalCondition(tf), 5., -0.1);

        //create environment
        SimulatedEnvironment env = new SimulatedEnvironment(domain,rf, tf, s);
        
        //hashingFactory
        SimpleHashableStateFactory hashingFactory = new SimpleHashableStateFactory();

        head = "qInit";
		System.setOut(dos);
		System.out.println(head);
		new File("./output/"+head).mkdirs();
		file = new File("./output/"+head+"/out_BD"); //Your file
		fos = new FileOutputStream(file);
		ps = new PrintStream(fos);
		System.setOut(ps);
		LearningAgentFactory qLearningFactory10 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with qInit " +qInit[0];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, qInit[0],0.1,0.1);
			}
        };
        LearningAgentFactory qLearningFactory11 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with qInit " +qInit[1];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, qInit[1],0.1,0.1);
			}
        };
        LearningAgentFactory qLearningFactory12 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with qInit " +qInit[2];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, qInit[2],0.1,0.1);
			}
        };
        LearningAgentFactory qLearningFactory13 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with qInit " +qInit[3];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, qInit[3],0.1,0.1);
			}
        };
        MyLearningAlgorithmExperimenter exp3 = new MyLearningAlgorithmExperimenter(env,
  				5, 2000, qLearningFactory10,qLearningFactory11,qLearningFactory12,qLearningFactory13);
  		exp3.setUpPlottingConfiguration(500, 500, 2, 1000, TrialMode.MOSTRECENTANDAVERAGE,
  				PerformanceMetric.STEPSPEREPISODE, 
  				PerformanceMetric.AVERAGEEPISODEREWARD);
  		//start experiment
  		long startTime = System.currentTimeMillis();
  		System.out.println("Start");
  		exp3.startExperiment();
  		long estimatedTime = System.currentTimeMillis() - startTime;
  		System.out.println("End");
  		System.out.println("Time Elapsed: " + estimatedTime + " ms");

		
        
        
        head = "learnrate";
		System.setOut(dos);
		System.out.println(head);
		new File("./output/"+head).mkdirs();
		file = new File("./output/"+head+"/out_BD"); //Your file
		fos = new FileOutputStream(file);
		ps = new PrintStream(fos);
		System.setOut(ps);

        LearningAgentFactory qLearningFactory1 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with learn rate "+learnrate[0];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, learnrate[0],0.1);
			}
        };
        LearningAgentFactory qLearningFactory2 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with learn rate "+learnrate[1];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, learnrate[1],0.1);
			}
        };
        LearningAgentFactory qLearningFactory3 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with learn rate "+learnrate[2];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, learnrate[2],0.1);
			}
        };
        LearningAgentFactory qLearningFactory4 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with learn rate "+learnrate[3];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, learnrate[3],0.1);
			}
        };
        LearningAgentFactory qLearningFactory5 = new LearningAgentFactory() {
			@Override
			public String getAgentName() {
				return "Q-learning with learn rate "+learnrate[4];
			}
			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, learnrate[4],0.1);
			}
        };
      		
		MyLearningAlgorithmExperimenter exp = new MyLearningAlgorithmExperimenter(env,
  				5, 2000, qLearningFactory1,qLearningFactory2,qLearningFactory3,qLearningFactory4);
  		exp.setUpPlottingConfiguration(500, 500, 2, 1000, TrialMode.MOSTRECENTANDAVERAGE,
  				PerformanceMetric.STEPSPEREPISODE, 
  				PerformanceMetric.AVERAGEEPISODEREWARD);
  		//start experiment
  		startTime = System.currentTimeMillis();
  		System.out.println("Start");
  		exp.startExperiment();
  		estimatedTime = System.currentTimeMillis() - startTime;
  		System.out.println("End");
  		System.out.println("Time Elapsed: " + estimatedTime + " ms");
      	
		head = "epsilon";
		System.setOut(dos);
		System.out.println(head);
		new File("./output/"+head).mkdirs();
		file = new File("./output/"+head+"/out_BD"); //Your file
		fos = new FileOutputStream(file);
		ps = new PrintStream(fos);
		System.setOut(ps);
		
		System.out.println("----------------------"+head+"_"+epsilon+"-----------------------");
		System.out.println("-------------Q Learning-------------");
		LearningAgentFactory qLearningFactory6 = new LearningAgentFactory() {

			@Override
			public String getAgentName() {
				return "Q-learning with epsilon " + epsilon[0];
			}

			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, 0.1,epsilon[0]);
			}
	    };
	    LearningAgentFactory qLearningFactory7 = new LearningAgentFactory() {

			@Override
			public String getAgentName() {
				return "Q-learning with epsilon " + epsilon[1];
			}

			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, 0.1,epsilon[1]);
			}
	    };
	    LearningAgentFactory qLearningFactory8 = new LearningAgentFactory() {

			@Override
			public String getAgentName() {
				return "Q-learning with epsilon " + epsilon[2];
			}

			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, 0.1,epsilon[2]);
			}
	    };
	    LearningAgentFactory qLearningFactory9 = new LearningAgentFactory() {

			@Override
			public String getAgentName() {
				return "Q-learning with epsilon " + epsilon[3];
			}

			@Override
			public LearningAgent generateAgent() {
				return new MyQLearning(domain, 0.99, hashingFactory, 0.3, 0.1,epsilon[3]);
			}
	    };
  		MyLearningAlgorithmExperimenter exp2 = new MyLearningAlgorithmExperimenter(env,
  				5, 2000, qLearningFactory6, qLearningFactory7, qLearningFactory8, qLearningFactory9);
  		exp2.setUpPlottingConfiguration(500, 500, 2, 1000, TrialMode.MOSTRECENTANDAVERAGE,
  				PerformanceMetric.STEPSPEREPISODE, 
  				PerformanceMetric.AVERAGEEPISODEREWARD);
  		//start experiment
  		startTime = System.currentTimeMillis();
  		System.out.println("Start");
  		exp2.startExperiment();
  		estimatedTime = System.currentTimeMillis() - startTime;
  		System.out.println("End");
  		System.out.println("Time Elapsed: " + estimatedTime + " ms");

//        System.out.println("What's the number of episodes?");
//        final int numIter = reader.nextInt();
//
//        QLearning ql = new QLearning(domain, 0.95, new SimpleHashableStateFactory(), 0, 0.1);
//        ql.setLearningRateFunction(new SoftTimeInverseDecayLR(initLR, sFactor));
//        ql.setLearningPolicy(new EpsilonGreedy(ql, 0.1));
//        ql.setQInitFunction(new ValueFunctionInitialization.ConstantValueFunctionInitialization());
//
//        ql.initializeForPlanning(rf, tf, numIter);
//
//        for(int i = 0; i < numIter; i++){
//            ql.runLearningEpisode(env, 100000);
//            env.resetEnvironment();
//        }
//
//        Policy p = ql.planFromState(s);
//        EpisodeAnalysis ea = p.evaluateBehavior(s, rf, tf);
//        System.out.println("Steps taken to exit: " + ea.actionSequence.size());

//        List<State> allStates = StateReachability.getReachableStates(s,
//                (SADomain) domain, new SimpleHashableStateFactory());
//
//        ValueFunctionVisualizerGUI gui = GridWorldDomain.getGridWorldValueFunctionVisualization(
//                allStates, ql, p);
//        gui.initGUI();
    }
}
