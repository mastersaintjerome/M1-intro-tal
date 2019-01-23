import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

final public class BiGram {
	private int previous;
	private int current;
	private int number;
	
	public BiGram(int previous, int current, int number){
		this.previous = previous;
		this.current = current;
		this.number = number;
	}
	
	public void increment(){
		number++;
	}
	
	public boolean compareTo(BiGram other){
		if(this.previous == other.current){
			if(this.current == other.current){
				return true;
			}
		}
		return false;
	}
	
	@Override
    public boolean equals(Object obj) {
        if(obj instanceof BiGram){
            BiGram other = (BiGram) obj;
			if(this.previous == other.previous){
				if(this.current == other.current){
					return true;
				}
			}
        }
        return false;
    }
	
	public String toString(){
		return previous + " " + current + " " + number;
	}
}


