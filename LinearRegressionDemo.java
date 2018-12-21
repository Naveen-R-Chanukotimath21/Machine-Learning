package LWLRegression;


import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.gui.beans.ModelPerformanceChart;
import weka.gui.beans.ThresholdDataEvent;
import weka.gui.graphvisualizer.GraphVisualizer;
import weka.gui.visualize.PlotData2D;

public class LinearRegressionDemo {
	
	/** file names are defined*/
	public static final String TRAINING_DATA_SET_FILENAME="D:/Users/networklab/workspace/MLLaboratory/lwlr/lrtrain.arff";
	//public static final String TESTING_DATA_SET_FILENAME="D:/Users/networklab/workspace/MLLaboratory/lwlr/lrtest.arff";
	public static final String PREDICTION_DATA_SET_FILENAME="D:/Users/networklab/workspace/MLLaboratory/lwlr/lrtestconfused.arff";

	public static Instances getDataSet(String fileName) throws IOException {
		/**
		 * we can set the file i.e., loader.setFile("filename") to load the data
		 */
		int classIdx = 1;
		/** the arffloader to load the arff file */
		ArffLoader loader = new ArffLoader();
		/** load the traing data */
		
		// we can also set the file like 
		  loader.setFile(new File(fileName));
		 
		Instances dataSet = loader.getDataSet();
		/** set the index based on the data given in the arff files */
		dataSet.setClassIndex(classIdx);
		return dataSet;
	}

	
	public static void  main(String args[]) throws Exception {

		Instances trainingDataSet = getDataSet(TRAINING_DATA_SET_FILENAME);
		Instances testingDataSet = getDataSet(PREDICTION_DATA_SET_FILENAME);
		/** Classifier here is Linear Regression */
		//Classifier classifier = new weka.classifiers.functions.LinearRegression();
		Classifier classifier = new weka.classifiers.lazy.LWL();
		//LoessInterpolator lip = new LoessInterpolator();
		/** */
		classifier.buildClassifier(trainingDataSet);
		
		/**
		 * train the algorithm with the training data and evaluate the
		 * algorithm with testing data
		 */
		Evaluation eval = new Evaluation(trainingDataSet);
		eval.evaluateModel(classifier, testingDataSet);
		/** Print the algorithm summary */
		System.out.println("** Locally Weighted Linear Regression Evaluation with Datasets **");
		System.out.println(eval.toSummaryString());
		
			
		/*Instance predictionDataSet = getDataSet(PREDICTION_DATA_SET_FILENAME).lastInstance();
		double value = classifier.classifyInstance(predictionDataSet);*/
		
		System.out.print(" the expression for the input data as per alogorithm is ");
		System.out.println(classifier.toString());
		
		/** Prediction Output */
		//System.out.println("Prediction\n" + value);
		
		try {
			
		    java.io.Reader r = new java.io.BufferedReader(new java.io.FileReader(
		    		PREDICTION_DATA_SET_FILENAME));
		    Instances inst = new Instances(r);
		    final javax.swing.JFrame jf = new javax.swing.JFrame("LWLRegression");
		    jf.getContentPane().setLayout(new java.awt.BorderLayout());
		    final ModelPerformanceChart as = new ModelPerformanceChart();
		    PlotData2D pd = new PlotData2D(inst);
		    pd.setPlotName(inst.relationName());
		    ThresholdDataEvent roc = new ThresholdDataEvent(as, pd);
		    as.acceptDataSet(roc);

		    jf.getContentPane().add(as, java.awt.BorderLayout.CENTER);
		    jf.setSize(800, 600);
		    jf.setVisible(true);
		     } catch (Exception ex) {
		    ex.printStackTrace();
		    System.err.println(ex.getMessage());
		  }
	}
	
	
}