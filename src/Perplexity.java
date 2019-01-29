import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

final public class Perplexity {
	
    final static public double alpha = 0.01;
    private Map<Integer, Float> probOneGram = new HashMap<Integer, Float>();
    private Map<BiGram, Float> probBiGram = new HashMap<BiGram, Float>();

    final private OneGram oneGram = new OneGram();
    final private BiGramList biGramList = new BiGramList();

    public Perplexity() {
        oneGram.initFromFile();
        biGramList.initFromFile();
        
    }
    
    public OneGram getOneGram(){
        return oneGram;
    }

    public int getOneGramN()
    /*
     * Return : Renvois le nombre des occurences des mots du 1-gram lu.
     */
    {
    	return oneGram.getN();
    }
    
    public int getOneGramV() 
    /*
     * Return : Renvois le nombre de mots différents du 1-gram lu. 
     */
    {
    	return oneGram.getV();
    }
    
    public BiGramList getBiGramList() 
    /*
     * Return : Renvois la liste des bi-grams lus.
     */
    {
        return biGramList;
    }

    public int getBiGramN()
    /*
     * Return : Renvois le nombre de bi-grams lus.
     */
    {
    	return biGramList.getN();
    }
    
    public int getBiGramV() 
    /*
     * Return : Renvois le nombres de bi-grams différents lus.
     */
    {
    	return biGramList.getV();
    }
    
    public int C(int key) 
    /*
     * Return : Renvois le nombre d'occurences du mot key.
     */
    {
    	return oneGram.getNumberOf(key);
    }
    
    public int C(int key1, int key2) 
    /*
     * Return : Renvois le nombre d'occurences du bugram composé des 2 clés.
     */
    {
    	return biGramList.getNumberOf(key1, key2);
    }
    
    public float P(int Wi)
    /*
     * Return : Calcul et renvois la probabilitée d'un 1-gram donné.
     */
    {
    	return (float) ( ( C(Wi) + alpha ) / ( oneGram.getN() + ( oneGram.getV() * alpha ) ) );
    }

    public float P(int Wi, int Wj) 
    /*
     * Return : Calcul et renvois la probabilitée d'un bigram donné.
     */
    {
    	return (float) ( ( C(Wi, Wj) + alpha ) / ( oneGram.getNumberOf(Wi) + ( biGramList.getV() * alpha ) ) );
    }
    
    public float logP(int[] T) 
    /*
     * Return : Calcul et renvoie la somme des logarithme de toutes les probabilitées des mots en entrée.
     */
    {
    	int k = T.length;
    	
    	float result = (float) Math.log( P(0, T[0]) );
    	
    	for(int i = 1; i < k; i++) {
    		result += Math.log(P(T[i], T[i-1]));
    	}
    	
    	return result;
    }
    
    public float LP(String T)
    /*
     * Return : Calcul et renvoie le logprob moyen de la chaîne de mots en entrée.
     */
    {
    	String[] parsedT = T.split(" ");
    	int k = parsedT.length;
    	int[] convertedT = new int[parsedT.length];
    	
    	for(int i = 0; i < k; i++) {
    		convertedT[i] = Integer.parseInt(parsedT[i]);
    	}
    	
    	
    	return (float) ((float) - (1/k) * logP(convertedT));
    }
    
    public float PP(String T) 
    /*
     * Return : Calcul et renvoie la perplexitée de la liste de mots en entrée.
     */
    {
    	return (float) Math.pow(2, LP(T));
    }
    
    public void calculOneGramProbability() 
    /*
     * Calcul la probabilitée de chaque 1-gram et la stoque dans une Map.
     */
    {
    	Set<Integer> keys = getOneGram().getMapKeys();
    	
    	for(int key: keys) {
    		probOneGram.put(key, P(key));
    	}
    	
    }
    
    public void calculBiGramProbability() 
    /*
     * Calcul la probabilitée de chaque 1-gram et la stoque dans une Map.
     */
    {
    	/*
    	
    	keys;
    	getBiGramList().getMapKeys();
    	
    	for(int key: keys) {
    		//probBiGram.put(key, P(key));
    	}
    	
    	*/
    }
    
    public static void main(String[] args) {
    	
        Perplexity perplexity = new Perplexity();
        
        perplexity.getOneGram().printMap();
        perplexity.getBiGramList().printBiGram();
        
        perplexity.calculOneGramProbability();
        perplexity.calculBiGramProbability();
        
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        
        
        
        
    }

}
