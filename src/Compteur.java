import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

final public class Compteur {
	private OneGram oneGram = new OneGram();
	private BiGramList biGrams = new BiGramList();
	private Scanner sc = new Scanner(System.in);
	private String line;
	
	public Compteur(){
		
	}
	
	public Compteur(String line){
		this.line = line;
	}
	
	public void oneGram(){
		oneGram.createOneGram(line);
		oneGram.sortMap();
	}
	
	public BiGramList getBiGrams(){
		return biGrams;
	}
	
	public OneGram getOneGram(){
		return oneGram;
	}
	
	public void biGram(){
		biGrams.createBiGram(line);
	}
	
	public boolean hasNextLine() {
		return sc.hasNextLine();
	}
	
	public void readLine() {
		line = sc.nextLine();
	}
	
	public void toFile(){
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

