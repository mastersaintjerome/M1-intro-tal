import java.util.Scanner;

final public class Compteur {
	
	private OneGram oneGram = new OneGram();
	private BiGramList biGrams = new BiGramList();
	private Scanner sc = new Scanner(System.in);
	private String line;
	
	public Compteur(){
		
	}
	
	public Compteur(String line)
	/*
	 * Crée un compteur avec une ligne a lire 
	 */
	{
		this.line = line;
	}
	
	public void oneGram()
	/*
	 * Créer et ajoute un 1-gram dans la liste, La trie ensuite.
	 */
	{
		oneGram.createOneGram(line);
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
	
	public void biGram()
	/*
	 * Crée et ajoute un biGram dans la liste.
	 */
	{
		biGrams.createBiGram(line);
	}
	
	public boolean hasNextLine() {
		return sc.hasNextLine();
	}
	
	public void readLine() 
	/*
	 * Lis une ligne depuis l'entrée standard.
	 */
	{
		line = sc.nextLine();
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
		
		Compteur comp;	
		
		if(args.length >= 1) {
			System.out.println(args[0]);
			comp = new Compteur(args[0]);
		}else {
			comp = new Compteur();
			comp.readLine();
		}
		
		comp.oneGram();
		comp.biGram();
		comp.getOneGram().printMap();
		comp.getBiGrams().printBiGram();
		comp.toFile();
	}
}

