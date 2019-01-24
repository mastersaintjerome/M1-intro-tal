import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

final public class Reader {

	private int codeMot;
	private String line;
	private Scanner sc;
	private Scanner entry = new Scanner(System.in);
	
	public Reader() 
	/*
	 * Lis l'entrée standard si un fichier n'est pas spécifier.
	 */
	{
		sc = new Scanner(System.in);
	}
	
	public Reader(String filename) 
	/*
	 * Lis un fichier entré en paramettre.
	 */
	{
		
		File file = new  File(filename);
		
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	}

	public void readLine() 
	/*
	 * Prépare la prochaine ligne à traiter si elle existe, en séparant le code et la chaine. 
	 */
	{
		
		if(sc.hasNextLine()) {
			
			codeMot = sc.nextInt();
			line = sc.nextLine();
			line = line.substring(1);
			line = line.replaceAll(" ", "_");
			
		}else {
			codeMot = -1;
			line = "";
		}
		
	}
	
	public boolean hasNextLine() {
		return sc.hasNextLine();
	}
	
	public String readEntry() {
		return entry.nextLine();
	}
	
	public int getCodeMot() {
		return codeMot;
	}
	
	public String getWord() {
		return line;
	}

}
