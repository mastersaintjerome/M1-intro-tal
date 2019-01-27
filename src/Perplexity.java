
final public class Perplexity {
	
    final static public double alpha = 0.01;
    final private OneGram oneGram = new OneGram();
    final private BiGramList biGramList = new BiGramList();

    public Perplexity() {
        oneGram.initFromFile();
        biGramList.initFromFile();
        
    }
    
    public OneGram getOneGram() {
        return oneGram;
    }

    public int getOneGramN() {
    	return oneGram.getN();
    }
    
    public int getOneGramV() {
    	return oneGram.getV();
    }
    
    public BiGramList getBiGramList() {
        return biGramList;
    }

    public int getBiGramN() {
    	return biGramList.getN();
    }
    
    public int getBiGramV() {
    	return biGramList.getV();
    }
    
    public int C(int key) {
    	return oneGram.getNumberOf(key);
    }
    
    public int C(int key1, int key2) {
    	return biGramList.getNumberOf(key1, key2);
    }
    
    public float P(int Wi) {
    	return (float) ( ( C(Wi) + alpha ) / ( oneGram.getN() + ( oneGram.getV() * alpha ) ) );
    }

    public float P(int Wi, int Wj) {
    	return (float) ( ( C(Wi, Wj) + alpha ) / ( oneGram.getNumberOf(Wi) + ( biGramList.getV() * alpha ) ) );
    }
    
    public float logP(int[] T) {
    	int k = T.length;
    	
    	float result = (float) Math.log( P(0, T[0]) );
    	
    	for(int i = 1; i < k; i++) {
    		result += Math.log(P(T[i], T[i-1]));
    	}
    	
    	return result;
    }
    
    public float LP(String T) {
    	String[] parsedT = T.split(" ");
    	int k = parsedT.length;
    	int[] convertedT = new int[parsedT.length];
    	
    	for(int i = 0; i < k; i++) {
    		convertedT[i] = Integer.parseInt(parsedT[i]);
    	}
    	
    	
    	return (float) ((float) - (1/k) * logP(convertedT));
    }
    
    public float PP(String T) {
    	return (float) Math.pow(2, LP(T) );
    }
    
    public static void main(String[] args) {
        Perplexity perplexity = new Perplexity();
        perplexity.getOneGram().printMap();
        perplexity.getBiGramList().printBiGram();
    }

}
