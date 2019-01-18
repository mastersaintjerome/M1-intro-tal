import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

	private int codeMot;
	private String line;
	private Scanner sc;
	private Scanner entry = new Scanner(System.in);
	
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
