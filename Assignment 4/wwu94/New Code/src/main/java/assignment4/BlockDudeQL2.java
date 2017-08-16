package assignment4;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

//import burlap.behavior.learningrate.SoftTimeInverseDecayLR;
//import burlap.behavior.policy.EpsilonGreedy;
//import burlap.behavior.policy.Policy;
//import burlap.behavior.singleagent.EpisodeAnalysis;
//import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
//import burlap.behavior.singleagent.auxiliary.StateReachability;
//import burlap.behavior.singleagent.auxiliary.valuefunctionvis.ValueFunctionVisualizerGUI;
//import burlap.behavior.singleagent.learning.tdmethods.QLearning;
//import burlap.behavior.singleagent.learning.tdmethods.SarsaLam;
//import burlap.behavior.valuefunction.ValueFunctionInitialization;
//import burlap.domain.singleagent.blockdude.BlockDude;
//import burlap.domain.singleagent.blockdude.BlockDudeLevelConstructor;
//import burlap.domain.singleagent.blockdude.BlockDudeTF;
//import burlap.domain.singleagent.blockdude.BlockDudeVisualizer;
//import burlap.domain.singleagent.gridworld.GridWorldDomain;
//import burlap.domain.singleagent.gridworld.GridWorldTerminalFunction;
//import burlap.domain.singleagent.pomdp.tiger.TigerDomain;
//import burlap.oomdp.auxiliary.stateconditiontest.TFGoalCondition;
//import burlap.oomdp.core.Domain;
//import burlap.oomdp.core.TerminalFunction;
//import burlap.oomdp.core.states.State;
//import burlap.oomdp.singleagent.RewardFunction;
//import burlap.oomdp.singleagent.SADomain;
//import burlap.oomdp.singleagent.common.GoalBasedRF;
//import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
//import burlap.oomdp.statehashing.SimpleHashableStateFactory;
//import burlap.oomdp.visualizer.Visualizer;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtilities;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;

public class BlockDudeQL2 {

	public static void main(String[] args) throws IOException {
		int[] level = {1};
		//double[] reward = {-0.1,1,3,5,10,100};
		double[] reward = {-0.1};
		double[] discount = {0.99,0.95,0.9,0.8};
		double[] qInit = {0.1, 1, 10, 100};
		double[] epsilon = {0.05,0.1,0.4,0.7};
		double[] learnrate = {0.1, 0.5, 1};
		
		OutputStream outs = System.out;
		PrintStream dos = new PrintStream(outs);
		
		String head = "level";
		System.setOut(dos);
		//System.out.println(head);
  		new File("./output/"+head).mkdirs();
		File file = new File("./output/"+head+"/out_BD2"); //Your file
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		
//		for(int i=0;i<level.length;i++){
//			System.out.println("----------------------"+head+"_"+level[i]+"-----------------------");
//			MyBlockDude bd = new MyBlockDude(5,-0.1,0.99,level[i]);
//			System.out.println("-------------Value Iteration-------------");
//			bd.valueIteration("./output/"+head+"/BlockDude_"+level[i]);
//			System.out.println("-------------Policy Iteration-------------");
//			bd.policyIteration("./output/"+head+"/BlockDude_"+level[i]);
//		}
		
//		head = "reward";
//		System.setOut(dos);
//		System.out.println(head);
//		new File("./output/"+head).mkdirs();
//		file = new File("./output/"+head+"/out_BD"); //Your file
//		fos = new FileOutputStream(file);
//		ps = new PrintStream(fos);
//		System.setOut(ps);
//		for(int i=0;i<reward.length;i++){
//			System.out.println("----------------------"+head+"_"+reward[i]+"-----------------------");;
//			MyBlockDude bd = new MyBlockDude(reward[i],-0.1, 0.99,1);
//			System.out.println("-------------Value Iteration-------------");
//			bd.valueIteration("./output/"+head+"/BlockDude_"+reward[i]);
//			System.out.println("-------------Policy Iteration-------------");
//			bd.policyIteration("./output/"+head+"/BlockDude_"+reward[i]);
//		}
//		
//		head = "discount";
//		System.setOut(dos);
//		System.out.println(head);
//		new File("./output/"+head).mkdirs();
//		file = new File("./output/"+head+"/out_BD"); //Your file
//		fos = new FileOutputStream(file);
//		ps = new PrintStream(fos);
//		System.setOut(ps);
//		for(int i=0;i<discount.length;i++){
//			System.out.println("----------------------"+head+"_"+discount[i]+"-----------------------");
//			MyBlockDude bd = new MyBlockDude(5, -0.1, discount[i],1);
//			System.out.println("-------------Value Iteration-------------");
//			bd.valueIteration("./output/"+head+"/BlockDude_"+discount[i]);
//			System.out.println("-------------Policy Iteration-------------");
//			bd.policyIteration("./output/"+head+"/BlockDude_"+discount[i]);
//		}
		
		head = "qInit";
		System.setOut(dos);
		System.out.println(head);
		new File("./output/"+head).mkdirs();
		file = new File("./output/"+head+"/out_BD"); //Your file
		fos = new FileOutputStream(file);
		ps = new PrintStream(fos);
		System.setOut(ps);
		for(int i=0;i<qInit.length;i++){
			System.out.println("----------------------"+head+"_"+qInit[i]+"-----------------------");
			MyBlockDude bd = new MyBlockDude(5, -0.1, 0.99,3);
			System.out.println("-------------Q Learning-------------");
			bd.QLearning("./output/"+head+"/BlockDude_"+qInit[i], qInit[i], 1, 0.1);
		}

		head = "learnrate";
		System.setOut(dos);
		System.out.println(head);
		new File("./output/"+head).mkdirs();
		file = new File("./output/"+head+"/out_BD"); //Your file
		fos = new FileOutputStream(file);
		ps = new PrintStream(fos);
		System.setOut(ps);
		for(int i=0;i<learnrate.length;i++){
			System.out.println("----------------------"+head+"_"+learnrate[i]+"-----------------------");
			MyBlockDude bd = new MyBlockDude(5, -0.1, 0.99,3);
			System.out.println("-------------Q Learning-------------");
			bd.QLearning("./output/"+head+"/BlockDude_"+learnrate[i], learnrate[i], 1, 0.1);
		}
		
		head = "epsilon";
		System.setOut(dos);
		System.out.println(head);
		new File("./output/"+head).mkdirs();
		file = new File("./output/"+head+"/out_BD"); //Your file
		fos = new FileOutputStream(file);
		ps = new PrintStream(fos);
		System.setOut(ps);
		for(int i=0;i<epsilon.length;i++){
			System.out.println("----------------------"+head+"_"+epsilon[i]+"-----------------------");
			MyBlockDude bd = new MyBlockDude(5, -0.1, 0.99,3);
			System.out.println("-------------Q Learning-------------");
			bd.QLearning("./output/"+head+"/BlockDude_"+epsilon[i], 0.3, 1, epsilon[i]);
		}
		System.setOut(dos);
		System.out.println("ALL DONE");
		
	}
}