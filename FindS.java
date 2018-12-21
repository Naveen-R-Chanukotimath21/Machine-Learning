package findSAlgorithm;

/*
 * Implementation of the Find S Algorithm. 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FindS {
	public static void main(String[] args) throws Exception{
		
		//Initializations 
		String[] Attributes = new String[20];
		String[] hypothesis = new String[10];
	
		int y=0;
		
		boolean flag=true;
		String row;
		BufferedReader br = null;
		BufferedWriter bw = null;
		//Initializing null hypothesis as default
		for(int i=0;i<6;i++)
		{
			hypothesis[i]="null";
		}
		//Setting up the file readers
		try
		{
			bw = new BufferedWriter(new FileWriter("FindS_output.txt"));
			br = new BufferedReader(new FileReader("CEA_Train.txt"));
		}catch(Exception e)
		{
			e.printStackTrace();
			return;
		}	
		
		//TRAINING the hypothesis and generalizing it
		while((row=br.readLine())!=null)
		{	
			
			if(!row.equals("\n"))
			{	// Variable to check the correct parameter in hypothesis 
				y=0;
				//Splitting based on tabs and spaces 
				Attributes = row.split("\\s+");
				//We only consider positive examples 
				if(Attributes[Attributes.length-1].equals("Yes"))
				{
					for(int i=0;i<Attributes.length-1;i=i+1)
					{	//If first high row, we store values to the hypothesis
						if(flag)
						{
							hypothesis[y] = Attributes[i];
						}
						else
						{	// If not equal we generalize it by using ?
							if(!hypothesis[y].equals(Attributes[i]))
							{
								hypothesis[y]="?";
							}
						}
						y++;
					}
					//After first high row we start generalizing
					flag=false;
				}
			}
			
				for(int i=0;i<6;i++)
				{
					if(i==0)
					{	
						bw.write(hypothesis[i]);
					}
					else
					{	
						bw.write("\t"+hypothesis[i]);
					}
				}
				bw.write("\n");
			
		}
		
		// Closing buffers
		try{
			bw.close();
			br.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
		
