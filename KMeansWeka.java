package kMeansandEM;


import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class KMeansWeka {
	Instances cpu = null;
	SimpleKMeans kmeans;

	public void loadArff(String arffInput){
		DataSource source = null;
		try {
			source = new DataSource(arffInput);
			cpu = source.getDataSet();
			cpu.setClassIndex(cpu.numAttributes() - 1);
		} catch (Exception e1) {
		}
	}

	public void generateClassToCluster(){
		Remove filter = new Remove();
		filter.setAttributeIndices("" + (cpu.classIndex() + 1));
		try {
			filter.setInputFormat(cpu);
			Instances dataClusterer = Filter.useFilter(cpu, filter);
			kmeans = new SimpleKMeans();
			kmeans.buildClusterer(dataClusterer);
			ClusterEvaluation eval = new ClusterEvaluation();
			eval.setClusterer(kmeans);
			eval.evaluateClusterer(cpu);

			System.out.println(eval.clusterResultsToString());
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) throws Exception{
		KMeansWeka test = new KMeansWeka();
		test.loadArff("KMeansandEM/weather.nominal.arff");
		//test.clusterData();
		test.generateClassToCluster();
	}
}
