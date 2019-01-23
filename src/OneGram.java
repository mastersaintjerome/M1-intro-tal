import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

final public class OneGram {
	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	private static final String ONE_GRAM_FILE_NAME = "one-gram.txt";
	
	public void createOneGram(String line){
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
	
	public void sortMap() {
		Map<Integer, Integer> treeMap = new TreeMap<>(
                (Comparator<Integer>) (o1, o2) -> o1.compareTo(o2)
        );
		treeMap.putAll( map );
		map = treeMap;
	}
	
	public void printMap() {
		System.out.println("La map contient les éléments suivants: ");
		for( int key : map.keySet() ) {
			System.out.println(key + " " + map.get(key) );
		};
	}
	
	public void onegramToFile(){
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
	
	public void initFromFile(){
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
}

