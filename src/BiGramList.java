import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

final public class BiGramList {
	
	private static final String BI_GRAM_FILE_NAME = "bi-gram.txt";
	private Map<BiGram, Integer> biGramsList = new HashMap<BiGram, Integer>();
	
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
				
				int current = Integer.parseInt(parsedLine[i]);
				//System.out.println(current);
				BiGram biGram;
				
				if(i == 0){
					biGram = new BiGram(0, current);
				}else{
					biGram = new BiGram(Integer.parseInt(parsedLine[i-1]), current);
				}
			
				if( biGramsList.containsKey(biGram) ){
					incrementNumberOf(biGram);
				}else{
					biGramsList.put(biGram, 1);
				}
				
				i++;
			}
		}
	}
	
	public void incrementNumberOf(BiGram biGram)
	/*
	 * Incrémente le nombre d'occurence du bi-gram.
	 */
	{	
		biGramsList.put(biGram, biGramsList.get(biGram) + 1);
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
			
			for (BiGram biGram: biGramsList.keySet()) {
				bw.write(biGram.toString() + " " + biGramsList.get(biGram));
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
		for (BiGram biGram: biGramsList.keySet()) {
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
                String[] parts = line.split(" ", 3);
                biGramsList.put(new BiGram(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])), Integer.parseInt(parts[2]));
            }      
	}
	
	public Set<BiGram> getMapKeys() 
	/*
	 * Return : renvoie l'ensemble des Keys de la map contenant les 2-grams.
	 */
	{
		return biGramsList.keySet();
	}
	
	public int getNumberOf(int prev, int curr) 
	/*
	 * Return : renvoie le nombre d'occurence du 2-Gram.
	 */
	{
		BiGram bigram = new BiGram(prev, curr);
		
		if(biGramsList.containsKey(bigram))
			return biGramsList.get(bigram);
		
		else return 0;
	}
	
    public int getN()
    /*
     * return : Nombre de mots total de la map. 
     */
    {
       int result = 0;
       
       for(BiGram key : biGramsList.keySet()) {
    	   result += biGramsList.get(key);
       }
       
       return result;
    }
    
    public int getV()
    /*
     * return : Nombre de mots différents de la map.
     */
    {
       return biGramsList.keySet().size();
    }
}
