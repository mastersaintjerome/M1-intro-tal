import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

final public class Counter {
	
	private OneGram oneGram = new OneGram();
	private BiGramList biGrams = new BiGramList();
	private Scanner sc = new Scanner(System.in);
	//private String line;
	
	public Counter(){
		
	}
	
	public void oneGram(String line)
	/*
	 * Créer et ajoute un 1-gram dans la liste, La trie ensuite.
	 */
	{
		oneGram.createOneGram(line);
	}
	
	public void sortOneGram() {
		oneGram.sortMap();
	}
	
	public OneGram getOneGram()
	/*
	 * return: Renvoie la liste des 1-gram lus.
	 */
	{
		return oneGram;
	}
	
	public BiGramList getBiGrams()
	/*
	 * Retourne la liste des bigrams.
	 */
	{
		return biGrams;
	}
	
	public void biGram(String line)
	/*
	 * Crée et ajoute un biGram dans la liste.
	 */
	{
		biGrams.createBiGram(line);
	}
	
	public void toFile()
	/*
	 * Ecris le contenu des 1-gram et des 2-gram lus dans 2 fichier différents.
	 */
	{
		oneGram.onegramToFile();
		biGrams.bigramToFile();
	}
	
	public static void main(String[] args) {
		
		Counter comp = new Counter();	
		
		if(args.length >= 1) {
			//System.out.println(args[0]);
			//comp = new Counter(args[0]);
			File text = new File(args[0]);
			Scanner scnr = null;
			try{
				scnr = new Scanner(text);
			}catch(FileNotFoundException fi){
				
			}
			if(scnr != null){
				while(scnr.hasNextLine()){
					String line = scnr.nextLine();
					comp.oneGram(line);
					comp.biGram(line);
				}
			}else{
				
			}
			//comp.getOneGram().printMap();
			//comp.getBiGrams().printBiGram();
			comp.sortOneGram();
			comp.toFile();
		}else {
			//comp = new Counter();
			//comp.readLine();
		}
	}
}

