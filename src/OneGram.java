import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

final public class OneGram {
	
	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	private static final String ONE_GRAM_FILE_NAME = "one-gram.txt";
	
	public void createOneGram(String line)
	/*
	 * Prend une ligne en entrée, si la ligne n'est pas vide, Découpe la chaine mot par mot.
	 * Et ajoute le code du mot s'il n'existe pas sinon l'incrémente.
	 */
	{
		if( ! line.isEmpty() ) {
			String[] parsedLine = line.split(" ");
			
			for( String code : parsedLine ) {
				
				int key = Integer.parseInt(code);
				
				if( map.containsKey(key) ) {
					map.put(key, map.get(key) + 1);
				}else {
					map.put(key, 1);
				}
			}
		}
	}
	
	public void sortMap() 
	/*
	 * Crée une map, trié dans l'ordre croissant de la map de base. 
	 */
	{
		Map<Integer, Integer> treeMap = new TreeMap<>(
                (Comparator<Integer>) (o1, o2) -> o1.compareTo(o2)
        );
		treeMap.putAll( map );
		map = treeMap;
	}
	
	public void printMap() 
	/*
	 * Print les éléments de la map. 
	 */
	{
		System.out.println("La map contient les éléments suivants: ");
		for( int key : map.keySet() ) {
			System.out.println(key + " " + map.get(key) );
		};
	}
	
	public void onegramToFile()
	/*
	 * Ecris la liste des 1-gram dans un fichier txt.
	 */
	{
		BufferedWriter bw = null;
		FileWriter fw = null;	
		
		try {
			fw = new FileWriter(ONE_GRAM_FILE_NAME);
			bw = new BufferedWriter(fw);
			for( int key : map.keySet() ) {
				bw.write(key + " " + map.get(key));
				bw.newLine();
				bw.flush();
			}
			System.out.println(ONE_GRAM_FILE_NAME + " Done");
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
	
	public void initFromFile()
	/*
	 * Ecris la liste des 2-gram dans un fichier txt.
	 */
	{
		File text = new File(ONE_GRAM_FILE_NAME);
		Scanner scnr = null;
		try{
			scnr = new Scanner(text);
		}catch(FileNotFoundException fi){
			
		}
		if(scnr != null){
			while(scnr.hasNextLine()){
				String line = scnr.nextLine();
				String[] parts = line.split(" ",2);
				map.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			}
		}else{
			
		}
	}
        
	public int getN()
    /*
     * return : Nombre de mots total de la map. 
     */
	{
		int result = 0;
		for(Map.Entry<Integer, Integer> entry : map.entrySet()){
			result += entry.getValue();
		}
		return result;
	}
	
	public Set<Integer> getMapKeys()
	/*
	 * Return : renvois les clés contenus dans la map (les 1-grams). 
	 */
	{
		return map.keySet();
	}
        
    public int getV()
    /*
     * return : Nombre de mots différents de la map.
     */
    {
    	return map.size();
    }
        
    public int getNumberOf(int key) 
    /*
     * return : Nombre d'occurence de la clé key.
     */
    {
    	//System.out.println(key);
    	
    	if (map.containsKey(key))
    		return this.map.get(key);
    	return 0;
    }

    
}

