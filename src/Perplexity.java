
final public class Perplexity {
	
    final static public double alpha = 0.01;
    final private int N;
    final private int v;
    final private OneGram oneGram = new OneGram();
    final private BiGramList biGramList = new BiGramList();

    public Perplexity() {
        oneGram.initFromFile();
        biGramList.initFromFile();
        N = oneGram.getN();
        v = oneGram.getV();
    }
    
    public OneGram getOneGram() {
        return oneGram;
    }

    public BiGramList getBiGramList() {
        return biGramList;
    }

    public static void main(String[] args) {
        Perplexity perplexity = new Perplexity();
        perplexity.getOneGram().printMap();
        perplexity.getBiGramList().printBiGram();
    }

	public int getN() {
		return N;
	}

	public int getV() {
		return v;
	}
}
