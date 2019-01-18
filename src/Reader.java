import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

	private char calu;
	private int codeMot;
	private boolean finLigne = true;
	private String line;
	private Scanner sc;
	private int cursor = 0;
	
	public Reader() {
		sc = new Scanner(System.in);
	}
	
	public Reader(String filename) {
		File file = new  File(filename);
		
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	}

	public void readLine() {
		
		if(sc.hasNextLine()) {
			codeMot = sc.nextInt();
			line = sc.nextLine();
			line = line.substring(1);
			line = line.replaceAll(" ", "_");
		}else {
			codeMot = -1;
			line = "";
		}
		finLigne = false;
		cursor = 0;
	}

	public boolean readChar() {

		if (finLigne)
			readLine();

		if( ! line.isEmpty()) {
			calu = line.charAt(cursor);
			
			if(cursor == line.length() -1 ) {
				finLigne = true;
			}
			cursor++;
		}else {
			return false;
		}
		return true;
	}
	
	public String readEntry() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	public int getCodeMot() {
		return codeMot;
	}
	
	public char getCalu() {
		return calu;
	}
	
	public boolean isEndOfLine(){
		return finLigne;
	}
}
