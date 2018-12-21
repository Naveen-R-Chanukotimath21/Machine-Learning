package bayesNetwork;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet; 
import weka.core.Instances; 
import weka.core.converters.ConverterUtils.DataSource; 
import weka.gui.graphvisualizer.GraphVisualizer; 
import java.awt.BorderLayout; 
import javax.swing.JFrame; 
 
/**
 * Displays a trained BayesNet graph. 
 * Expects an ARFF filename as first argument. 
 * 
 */ 
public class VisualizeBayesNet { 
 
  
  public static void main(String args[]) throws Exception { 
    // train classifier 
    BayesNet cls = new BayesNet(); 
    Instances data = DataSource.read("bayesnet/diabetes.arff"); 
    data.setClassIndex(data.numAttributes() - 1); 
    cls.buildClassifier(data); 
    
    
   /* Instances test = DataSource.read("bayesnet/heartdiesease.arff");
    test.setClassIndex(data.numAttributes() - 1); */
    Evaluation eval = new Evaluation(data);
	eval.evaluateModel(cls, data);
	
	System.out.println("** Bayes Network Evaluation with Datasets **");
	System.out.println(eval.toSummaryString());
	
	 
    // display graph 
    GraphVisualizer gv = new GraphVisualizer(); 
    gv.readBIF(cls.graph()); 
    JFrame jf = new JFrame("BayesNet graph"); 
    //jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    jf.setSize(800, 600); 
    jf.getContentPane().setLayout(new BorderLayout()); 
    jf.getContentPane().add(gv, BorderLayout.CENTER); 
    jf.setVisible(true); 
    // layout graph 
    gv.layoutGraph(); 
  } 
}