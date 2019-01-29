import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

final public class BiGramList {
	
	private static final String BI_GRAM_FILE_NAME = "bi-gram.txt";
	private List<BiGram> biGrams = new ArrayList<BiGram>(); 
	
	public void createBiGram(String line)
	/*
	 * Crée un bi-gram et l'ajoute à la liste.
	 */
	{
		if( ! line.isEmpty() ) {
			
			String[] parsedLine = line.split(" ");
			int parsedLineLength = parsedLine.length;
			int i = 0;
			
			while(i < parsedLineLength){
				
				String current = parsedLine[i];
				//System.out.println(current);
				BiGram biGram;
				
				if(i == 0){
					biGram = new BiGram(0,Integer.parseInt(current),1);
				}else{
					biGram = new BiGram(Integer.parseInt(parsedLine[i-1]),Integer.parseInt(current),1);
				}
				
				int index = biGrams.indexOf(biGram);
				
				if(index != -1){
					
					biGrams.get(index).increment();
				}else{
					
					biGrams.add(biGram);
				}
				
				i++;
			}
		}
	}
	
	public List<BiGram> getBiGrams()
	/*
	 * return : renvoie la liste des bi-grams.
	 */
	{
		return getBiGrams();
	}
	
	public void bigramToFile()
	/*
	 * Ecris la liste des bi-grams dans un fichier.
	 */
	{
		BufferedWriter bw = null;
		FileWriter fw = null;	
		
		try {
			fw = new FileWriter(BI_GRAM_FILE_NAME);
			bw = new BufferedWriter(fw);
			
			for (BiGram biGram: biGrams) {
				bw.write(biGram.toString());
				bw.newLine();
				bw.flush();
			}
			
			System.out.println(BI_GRAM_FILE_NAME + " Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				
				if (fw != null)
					fw.close();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void printBiGram()
	/*
	 * Affiche la liste des bi-grams.
	 */
	{
		for (BiGram biGram: biGrams) {
			System.out.println(biGram.toString());
		}
	}
	
	public void initFromFile()
	/*
	 * Lis un fichier et crée une liste de bi-gram.
	 */
	{
		File text = new File(BI_GRAM_FILE_NAME);
		Scanner scnr = null;
		
		try{
			scnr = new Scanner(text);
			
		}catch(FileNotFoundException fi){
			fi.printStackTrace();
		}
            while(scnr.hasNextLine()){
                String line = scnr.nextLine();
                String[] parts = line.split(" ",3);
                biGrams.add(new BiGram(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),Integer.parseInt(parts[2])));
            }      
	}
	
	public void getMapKeys() {
		
	}
	
	public int getNumberOf(int prev, int curr) {
		
		BiGram biGram = new BiGram(prev, curr, 1);
		
		int index = biGrams.indexOf(biGram);
		
		if(index != -1){
			return biGrams.get(index).getNomber();
		}
		
		return -1;
	}
	
    public int getN()
    /*
     * return : Nombre de mots total de la map. 
     */
    {
       int result = 0;
       
       for(BiGram b : biGrams) {
    	   result += b.getNomber();
       }
       
       return result;
    }
    
    public int getV()
    /*
     * return : Nombre de mots différents de la map.
     */
    {
       return biGrams.size();
    }
}
