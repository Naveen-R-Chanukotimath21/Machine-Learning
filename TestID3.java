package id3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.Id3;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


public class TestID3 {
    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

         try {
             inputReader = new BufferedReader(new FileReader(filename));
          } catch (FileNotFoundException ex) {
             System.err.println("File not found: " + filename);
         }

         return inputReader;
     }

    public static void main(String[] args) throws Exception {

        //Get File
           
      ArffLoader loader= new ArffLoader();
      loader.setSource(new File("playtennis/tennis11.arff"));
      Instances data= loader.getDataSet();
       
      //Setting class attribute 
      data.setClassIndex(data.numAttributes() - 1);

      //Make tree
      Id3 tree = new Id3();
      tree.buildClassifier(data);

      //Print tree
      System.out.println(tree);

      //Predictions with test and training set of data

      BufferedReader datafile = readDataFile("playtennis/tennis11.arff");
      BufferedReader testfile = readDataFile("playtennis/tennis12.arff");

      Instances train = new Instances(datafile);
      train.setClassIndex(data.numAttributes() - 1);  // from somewhere
     
      Instances test = new Instances(testfile);
      test.setClassIndex(data.numAttributes() - 1);    // from somewhere
      
      // train classifier
      
      Classifier cls = new Id3();
      cls.buildClassifier(train);
      
 
      // evaluate classifier and print some statistics
      Evaluation eval = new Evaluation(train);
      
	  eval.evaluateModel(cls, test);
      System.out.println(eval.toSummaryString("\nResults\n======\n", true));
      
  
      
       for(int i=0;i<train.numInstances();i++)
      {
            double value=cls.classifyInstance(test.instance(i));
            String prediction=test.classAttribute().value((int)value); 
     System.out.println(test.instance(i)+"............Prediction.......... "+prediction); 
      }
      
   }
}