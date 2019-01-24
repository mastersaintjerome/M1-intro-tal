final public class BiGram {
	
	private int previous;
	private int current;
	private int number;
	
	public BiGram(int previous, int current, int number){
		this.previous = previous;
		this.current = current;
		this.number = number;
	}
	
	public void increment()
	/*
	 * Incrémente le nombre d'occurence du bi-gram.
	 */
	{
		number++;
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
    public boolean equals(Object obj)
	/*
	 * return : Renvoie vrai si 2 bigrams sont égaux, faux sinon.
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
		return previous + " " + current + " " + number;
	}
	
	public int getNomber() {
		return this.number;
	}
	
	public int getPrevious() {
		return this.previous;
	}
	
	public int getCurrent() {
		return this.current;
	}
}


