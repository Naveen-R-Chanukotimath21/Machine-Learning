package mlpBpnn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;


public class MLPBackpropagationNN {
	public static void main(String args[]){simpleWekaTrain();}
	
	public static void simpleWekaTrain()
	{
	try{
	//Reading training arff or csv file
	FileReader trainreader = new FileReader("bpnn/xor.arff");
	Instances train = new Instances(trainreader);
	train.setClassIndex(train.numAttributes()-1);
	//Instance of NN
	MultilayerPerceptron mlp = new MultilayerPerceptron();
	//Setting Parameters
	mlp.setLearningRate(0.1);
	mlp.setMomentum(0.2);
	mlp.setTrainingTime(2000);
	mlp.setHiddenLayers("5");
	mlp.buildClassifier(train);
			
	System.out.println(mlp.globalInfo());
	
	//mlp.setOptions(Utils.splitOptions("-L 0.1 -M 0.2 -N 2000 -V 0 -S 0 -E 20 -H 3"));
	
	
	Evaluation eval = new Evaluation(train);
    eval.evaluateModel(mlp, train);
   
    
    eval.crossValidateModel(mlp, train, 3, new Random(1));
    
    System.out.println("\n"+"Mean root squared Error: "+eval.errorRate()); //Printing Training Mean root squared Error
    System.out.println(eval.toSummaryString()); //Summary of Training
        
    Instances datapredict = new Instances(
    		new BufferedReader(
    		new FileReader("bpnn/xor.arff")));
    		datapredict.setClassIndex(datapredict.numAttributes()- 1);
    		Instances predicteddata = new Instances(datapredict);
    		//Predict Part
    		for (int i = 0; i < datapredict.numInstances(); i++) {
    		double clsLabel = mlp.classifyInstance(datapredict.instance(i));
    		System.out.println(clsLabel);
    		predicteddata.instance(i).setClassValue(clsLabel);
    		}
    		
    		System.out.println("\n"+predicteddata.toString()+"\n"+mlp.toString());
    		
    		
    }
	catch(Exception ex){
	ex.printStackTrace();
	}
	
	}
}
