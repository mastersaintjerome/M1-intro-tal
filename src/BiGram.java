final public class BiGram {
	
	private int previous;
	private int current;
	
	public BiGram(int previous, int current){
		this.previous = previous;
		this.current = current;
	}
	
	public BiGram compareTo(BiGram other)
	/*
	 * Compare 2 bi-grams entre eux et renvoie le plus petit en comparant le premier mot d'abord.
	 */
	{
		if(this.previous <= other.current){
			if(this.current <= other.current){
				return this;
			}
		}
		return other;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + current;
		result = prime * result + previous;
		return result;
	}
	
	@Override
    public boolean equals(Object obj)
	/*
	 * return : Renvoie vrai si les 2 bigrams sont égaux, faux sinon.
	 */
	{
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
		return previous + " " + current;
	}
	
	public int getPrevious() {
		return this.previous;
	}
	
	public int getCurrent() {
		return this.current;
	}
}


