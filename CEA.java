package candidateElimination;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CEA {
	
	File file = null;
    FileReader fr = null;
    BufferedReader br = null;
	
	public String[] AttributeList = {"Sky","AirTemp","Humidity","Wind","Water","ForeCast"};
        
    public ArrayList<String> HG = new ArrayList<String>();
	public ArrayList<String> HS = new ArrayList<String>();
		    
	public String[] G = new String[AttributeList.length];
	public String[] S  = new String[AttributeList.length];
    
	public String[] positiveAttributeValues = new String[AttributeList.length];
    public String[] negativeAttributeValues = new String[AttributeList.length];
    
    public String[] result = new String[AttributeList.length];;
	
	public static void main(String args[]) {
		
		Initialize initialize = new Initialize();
	}
	
}

class Initialize{
	
	CEA cea = new CEA();
	static int count = 0;
	
	Initialize(){
		
		for(int i=0; i<cea.AttributeList.length; i++){
			
			cea.G[i] = "?";
			cea.S[i] = "0";
			
		}
		
		/*
		for(int i=0; i<cea.G.size(); i++){
			
			System.out.print(" "+ cea.G.get(i));
		}	
		*/
		try{
	           
	           cea.file = new File("CEA.txt");
	           
	           cea.fr = new FileReader(cea.file);
	           
	           cea.br = new BufferedReader(cea.fr);
	           
	       }catch(FileNotFoundException e){e.printStackTrace();}
		
		String row;
		
		
		try {
			while((row = cea.br.readLine())!=null){
				
				if(!row.equals("\n")){
					
					cea.result = row.split("\\s");
					
					if(cea.result[cea.result.length-1].equals("Yes")){
						
						for(int i=0; i<cea.result.length-1; i++){
							
							cea.positiveAttributeValues[i] = cea.result[i];
											
						}
						
						positiveEncounter();
						cea.HS.add("\n");
						
					}
					
					else{
						
						if(cea.result[cea.result.length-1].equals("No")){
							
							for(int i=0; i<cea.result.length-1; i++){
								
								cea.negativeAttributeValues[i] = cea.result[i];
							}
							
							negativeEncounter();
							
						}
						
					}
															
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display();
	}
	
	void positiveEncounter(){
		
		for(int i=0; i<cea.positiveAttributeValues.length;i++){
			
			if(cea.G[i].equals("?")&&cea.S[i].equals("0")){
					cea.G[i] = "?";
					cea.HG.add("?");
				
			}
			
			else if(cea.G[i].equals("?")&&(!cea.G[i].equals(cea.positiveAttributeValues[i]))){
				cea.G[i]="?";
				for(int j=0;j<cea.positiveAttributeValues.length;j++){
					
						cea.HG.add("?");
				}
					
			}
			else if(cea.G[i].equals(cea.positiveAttributeValues[i])){
				cea.G[i]=cea.G[i];
				for(int j=0;j<cea.positiveAttributeValues.length;j++){
					if(i==j)
						cea.HG.add(cea.G[j]);
					else
						cea.HG.add("?");
				}
					
			}
			else{}
			
			if(cea.S[i]=="0"){
				cea.S[i] = cea.positiveAttributeValues[i];
				cea.HS.add(cea.positiveAttributeValues[i]);
			}
			
			else if(!cea.positiveAttributeValues[i].equals(cea.S[i])){
				cea.S[i] = "?";
				cea.HS.add("?");
			}
			
			else{
				cea.S[i] = cea.positiveAttributeValues[i];
				cea.HS.add(cea.positiveAttributeValues[i]);
			}
			
		}
		
	}
	void negativeEncounter(){
		
		for(int j=0; j<cea.negativeAttributeValues.length;j++){			
							
			if(cea.S[j].equals("?")&&(!cea.negativeAttributeValues[j].equals(cea.S[j]))){
					cea.S[j] = cea.S[j];
					
			}
				
			else if(cea.S[j].equals(cea.negativeAttributeValues[j])){
					cea.S[j] = cea.S[j];
					
					
			}
			else{}
				
			if(!cea.negativeAttributeValues[j].equals(cea.S[j])){
					cea.G[j] = cea.S[j];
					for(int k=0; k<cea.negativeAttributeValues.length;k++){
						if(k==j)
							cea.HG.add(cea.S[j]);
						else
							cea.HG.add("?");
					}									
							
					
			}
			else if(cea.negativeAttributeValues[j].equals(cea.S[j])&&(cea.S[j].equals("?"))){
					cea.G[j] = "?";
					for(int k=0; k<cea.negativeAttributeValues.length;k++){
						
							cea.HG.add("?");
						
					}
					
					
			}
			else{}
				
				
			}	
					
	}
			
	void display(){
		
		System.out.println("The Most Specific Hypothesis is");
		for(String aHS: cea.HS){
			System.out.print("\t"+aHS);
		}System.out.println();
		
		
		int pcount=0;
		System.out.println("The Most General Hypothesis is");
		for(String aHG: cea.HG){
			pcount++;
			System.out.print("\t"+aHG);
			if(pcount==cea.AttributeList.length)
			{
				System.out.println();
				pcount=0;
			}
		}
	}
	
}