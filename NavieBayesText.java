package navieBayesText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class NavieBayesText {
	public static void main(String args[]) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the number of attributes: ");
		int attr = sc.nextInt();
		sc.nextLine();

		String[][] attrValues = new String[attr][];
		float[][][] probTable = new float[attr][][]; //Achievement unlocked: 3D Array!

		for(int i=0; i<attr; i++) {
			System.out.print("Enter the possible values for attribute " + (i+1) + ": ");
			attrValues[i] = sc.nextLine().split(" ");
			probTable[i] = new float[attrValues[i].length][2];
			
		}
		
		//System.out.print("Enter the name of the CSV file: ");
		//String fileName = sc.nextLine();
		String fileName = "NavieBayesText.txt";
		
		int yesCount = 0, noCount = 0;
		
		//BufferedReader br = new BufferedReader(new FileReader(fileName + ".csv"));
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		int objects = 0;
		try{
			while ((line = br.readLine()) != null) {
				String[] currentAttr = line.split(" ");
				
				for(int j=0; j<attr; j++)
					for(int k=0; k<attrValues[j].length; k++)
						
							if(currentAttr[j].equals(attrValues[j][k])) {
								if(currentAttr[attr].equals("Y"))
									probTable[j][k][0]++;
								else
									probTable[j][k][1]++;
								break;
							}
						
				if(currentAttr[attr].equals("Y"))
					yesCount++;
				else
					noCount++;
				
				objects++;
			}
			
			br.close();
		}catch(ArrayIndexOutOfBoundsException aie){aie.printStackTrace();}
		
		
		for(int i=0; i<attr; i++)
			for(int j=0; j<probTable[i].length; j++) {
				probTable[i][j][0] /= yesCount;
				probTable[i][j][1] /= noCount;
			}
			
		System.out.print("\nEnter the attributes of the unknown sample: ");
		String[] unknownAttr = sc.nextLine().split(" ");
		
		double actual = 0, attributesCount = 0, predicted = 0, correctClassification = 0, incorrectClassification = 0;
		float yesProb = 1, noProb = 1;
		
		for(int i=0; i<unknownAttr.length; i++)
			for(int j=0; j<attrValues[i].length; j++)
				if(unknownAttr[i].equals(attrValues[i][j])) {
					yesProb *= probTable[i][j][0];
					noProb *= probTable[i][j][1];
					attributesCount++;
					break;
				}
		
		yesProb *= yesCount*1.0/objects;
		noProb *= noCount*1.0/objects;
		
		actual = attributesCount/unknownAttr.length;
		predicted = yesProb>noProb? 1:0;
		if(predicted==actual) correctClassification++;
		else incorrectClassification++;
		double accuracy = correctClassification/(correctClassification + incorrectClassification + 0.0); 
		
		System.out.println("P(Y) = " + yesProb + "\tP(N) = " + noProb);
		System.out.println("Thus, the class of the unknown sample will be " + (yesProb>noProb? "Y":"N"));
		System.out.println("Accuracy" + accuracy);
		
		sc.close();
	}
}
