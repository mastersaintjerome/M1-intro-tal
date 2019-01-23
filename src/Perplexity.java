
final public class Perplexity{
	private final double a = 0.01;
	private OneGram oneGram = new OneGram();
	private BiGramList biGramList = new BiGramList();
	
	public Perplexity(){
		oneGram.initFromFile();
		biGramList.initFromFile();
	}
	
	public OneGram getOneGram(){
		return oneGram;
	}
	
	public BiGramList getBiGramList(){
		return biGramList;
	}
	
	public static void main(String[] args) {
		Perplexity perplexity = new Perplexity();
		perplexity.getOneGram().printMap();
		perplexity.getBiGramList().printBiGram();
	}
}
