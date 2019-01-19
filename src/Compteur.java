import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Compteur {

	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	private Scanner sc = new Scanner(System.in);
	
	public boolean hasNextLine() {
		return sc.hasNextLine();
	}
	
	public void readLine() {
		String line = sc.nextLine();
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
			System.out.println("    " + key + " " + map.get(key) );
		}
	}
	
	public static void main(String[] args) {
		
		Compteur comp = new Compteur();
		
		comp.readLine();
		comp.sortMap();
		comp.printMap();
		
	}
	
}
